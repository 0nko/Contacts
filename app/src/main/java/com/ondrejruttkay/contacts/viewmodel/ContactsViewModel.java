package com.ondrejruttkay.contacts.viewmodel;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.ondrejruttkay.contacts.ContactsApplication;
import com.ondrejruttkay.contacts.event.AddContactEvent;
import com.ondrejruttkay.contacts.event.ContactAddedEvent;
import com.ondrejruttkay.contacts.event.ContactSelectedEvent;
import com.ondrejruttkay.contacts.event.ContactsReceivedEvent;
import com.ondrejruttkay.contacts.event.ContactsRequestErrorEvent;
import com.ondrejruttkay.contacts.model.Contact;
import com.ondrejruttkay.contacts.utility.NetworkManager;
import com.ondrejruttkay.contacts.view.IContactsView;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import eu.inloop.viewmodel.AbstractViewModel;

/**
 * Created by onko on 27/01/2016.
 */
public class ContactsViewModel extends AbstractViewModel<IContactsView> {

    private static final String PROGRESS_KEY = "PROGRESS_KEY";

    private boolean isLoading;
    private List<Contact> contacts;

    public ContactsViewModel() {
        contacts = new ArrayList<>();
        ContactsApplication.getEventBus().register(this);
    }

    @Override
    public void onCreate(@Nullable Bundle arguments, @Nullable Bundle savedInstanceState) {
        super.onCreate(arguments, savedInstanceState);

        isLoading = false;

        //this will be only not null in case the application was killed due to low memory
        if (savedInstanceState != null) {
            isLoading = savedInstanceState.getBoolean(PROGRESS_KEY);
        }

        loadContacts();
    }

    @Override
    public void bindView(@NonNull IContactsView view) {
        super.bindView(view);

        setProgressVisible(isLoading);
    }

    @Subscribe
    public void onContactsReceived(ContactsReceivedEvent contactsEvent) {
        contacts = contactsEvent.getContacts();
        setProgressVisible(false);
        showContacts();
    }

    @Subscribe
    public void onContactsRequestError(ContactsRequestErrorEvent errorEvent) {
        showContacts();
        setProgressVisible(false);
        showMessage(errorEvent.getMessage());
    }

    @Subscribe
    public void onContactSelected(ContactSelectedEvent contactEvent) {
        if (getView() != null) {
            getView().showContactDetail(contactEvent.getContact());
        }
    }

    @Subscribe
    public void onAddNewContact(AddContactEvent addContactEvent) {
        if (getView() != null) {
            getView().addNewContact();
        }
    }

    @Subscribe
    public void onNewContactAdded(ContactAddedEvent contactAddedEvent) {
        loadContacts();
    }

    public void loadContacts() {

        ContactsApplication.getApiClient().requestContactsFromCache();

        if (NetworkManager.isOnline()) {
            setProgressVisible(true);
            ContactsApplication.getApiClient().requestContacts();
        }
        else {
            setProgressVisible(false);
            showMessage("No connection");
        }
    }

    private void setProgressVisible(boolean visible) {
        isLoading = visible;

        if (getView() != null) {
            if (visible)
                getView().showLoading();
            else
                getView().hideLoading();
        }
    }

    private void showContacts() {
        if (getView() != null) {
            if (contacts.size() > 0) {
                getView().showContacts();
                getView().refreshContacts();
            }
            else {
                getView().showEmpty();
            }
        }
    }

    private void showMessage(String message) {
        if (getView() != null) {
            getView().showMessage(message);
        }
    }

    @Override
    public void saveState(@NonNull Bundle bundle) {
        super.saveState(bundle);

        bundle.putBoolean(PROGRESS_KEY, isLoading);
    }

    public List<Contact> getContacts() {
        return contacts;
    }
}
