package com.ondrejruttkay.contacts.client;

import com.ondrejruttkay.contacts.model.Contact;

import java.util.List;

/**
 * Created by onko on 28/01/2016.
 */
public class ContactsResponse {

    private List<Contact> items;
    private Error error;

    public List<Contact> getContacts() {
        return items;
    }

    public Error getError() {
        return error;
    }
}
