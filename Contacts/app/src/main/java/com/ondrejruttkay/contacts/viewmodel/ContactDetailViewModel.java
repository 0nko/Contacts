package com.ondrejruttkay.contacts.viewmodel;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ondrejruttkay.contacts.ContactsApplication;
import com.ondrejruttkay.contacts.event.OrdersReceivedEvent;
import com.ondrejruttkay.contacts.event.OrdersRequestErrorEvent;
import com.ondrejruttkay.contacts.model.Contact;
import com.ondrejruttkay.contacts.model.Order;
import com.ondrejruttkay.contacts.utility.NetworkManager;
import com.ondrejruttkay.contacts.view.IContactDetailView;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import eu.inloop.viewmodel.AbstractViewModel;

/**
 * Created by onko on 30/01/2016.
 */
public class ContactDetailViewModel extends AbstractViewModel<IContactDetailView> {

    private Contact contact;
    private List<Order> orders;

    public ContactDetailViewModel() {
        orders = new ArrayList<>();
        ContactsApplication.getEventBus().register(this);
    }

    @Override
    public void onCreate(@Nullable Bundle arguments, @Nullable Bundle savedInstanceState) {
        super.onCreate(arguments, savedInstanceState);
    }

    @Subscribe
    public void onOrdersReceived(OrdersReceivedEvent ordersEvent) {
        orders = ordersEvent.getOrders();
        showOrders();
    }

    @Subscribe
    public void onOrdersRequestError(OrdersRequestErrorEvent errorEvent) {
        showOrders();
        showMessage(errorEvent.getMessage());
    }

    public void loadOrders() {

        if (NetworkManager.isOnline()) {
            showProgress();
            ContactsApplication.getApiClient().requestOrders(contact.getId());
        }
        else {
            showMessage("No connection");
        }
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Contact getContact() {
        return contact;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    private void showOrders() {
        if (getView() != null) {
            if (orders.size() > 0) {
                getView().refresh();
                getView().showOrders();
            } else {
                getView().showNoOrders();
            }
        }
    }

    private void showProgress() {

        if (getView() != null) {
            getView().showProgress();
        }
    }

    private void showMessage(String message) {
        if (getView() != null) {
            getView().showMessage(message);
        }
    }
}
