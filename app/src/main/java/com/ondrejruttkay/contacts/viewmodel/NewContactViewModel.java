package com.ondrejruttkay.contacts.viewmodel;

import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.ondrejruttkay.contacts.ContactsApplication;
import com.ondrejruttkay.contacts.R;
import com.ondrejruttkay.contacts.event.ContactAddedEvent;
import com.ondrejruttkay.contacts.event.NewContactRequestErrorEvent;
import com.ondrejruttkay.contacts.model.Contact;
import com.ondrejruttkay.contacts.utility.NetworkManager;
import com.ondrejruttkay.contacts.view.INewContactView;
import com.squareup.otto.Subscribe;

import eu.inloop.viewmodel.AbstractViewModel;

/**
 * Created by onko on 30/01/2016.
 */
public class NewContactViewModel extends AbstractViewModel<INewContactView> {

    public ObservableField<Boolean> addButtonEnabled;

    private Contact contact;

    public NewContactViewModel() {
        contact = new Contact();
        addButtonEnabled = new ObservableField<>();
        addButtonEnabled.set(true);

        ContactsApplication.getEventBus().register(this);
    }

    @Override
    public void onCreate(@Nullable Bundle arguments, @Nullable Bundle savedInstanceState) {
        super.onCreate(arguments, savedInstanceState);
    }

    @Subscribe
    public void onNewContactAdded(ContactAddedEvent contactAddedEvent) {
        if (getView() != null) {
            getView().close();
        }
    }

    @Subscribe
    public void onNewContactError(NewContactRequestErrorEvent errorEvent) {
        addButtonEnabled.set(true);
        showMessage(errorEvent.getMessage());
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Contact getContact() {
        return contact;
    }

    private boolean isValid() {
        if (TextUtils.isEmpty(contact.getName()) || contact.getName().length() < 5) {
            if (getView() != null) {
                getView().validate(R.id.new_contact_name, false);
            }
            return false;
        }
        else if (getView() != null) {
            getView().validate(R.id.new_contact_name, true);
        }

        if (TextUtils.isEmpty(contact.getPhone()) || contact.getPhone().length() < 5) {
            if (getView() != null) {
                getView().validate(R.id.new_contact_phone, false);
            }
            return false;
        }
        else if (getView() != null) {
            getView().validate(R.id.new_contact_phone, true);
        }

        return true;
    }

    private void showMessage(String message) {
        if (getView() != null) {
            getView().showMessage(message);
        }
    }

    public void onNameChanged(CharSequence s, int start, int before, int count) {
        contact.setName(s.toString());
    }

    public void onPhoneChanged(CharSequence s, int start, int before, int count) {
        contact.setPhone(s.toString());
    }

    public void addContact(View view) {

        if (NetworkManager.isOnline()) {
            if (isValid()) {
                addButtonEnabled.set(false);
                ContactsApplication.getApiClient().addNewContact(contact);
            }
        }
        else {
            showMessage("No connection");
        }
    }
}
