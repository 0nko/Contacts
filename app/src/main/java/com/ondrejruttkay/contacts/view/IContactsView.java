package com.ondrejruttkay.contacts.view;

import com.ondrejruttkay.contacts.model.Contact;

import eu.inloop.viewmodel.IView;

/**
 * Created by onko on 27/01/2016.
 */
public interface IContactsView extends IView {
    void showLoading();
    void hideLoading();
    void showContactDetail(Contact contact);
    void addNewContact();
    void showEmpty();
    void refreshContacts();
    void showContacts();
    void showMessage(String message);
}
