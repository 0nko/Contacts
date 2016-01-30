package com.ondrejruttkay.contacts.view;

import com.ondrejruttkay.contacts.model.Contact;

import eu.inloop.viewmodel.IView;

/**
 * Created by onko on 27/01/2016.
 */
public interface IContactsView extends IView {
    void showProgress();
    void hideProgress();
    void showContactDetail(Contact contact);
    void showNoData();
    void refresh();
    void showContacts();
    void showMessage(String message);
}
