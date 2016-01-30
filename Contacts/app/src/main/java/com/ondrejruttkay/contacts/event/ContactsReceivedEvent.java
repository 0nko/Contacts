package com.ondrejruttkay.contacts.event;

import com.ondrejruttkay.contacts.model.Contact;

import java.util.List;

/**
 * Created by onko on 28/01/2016.
 */
public class ContactsReceivedEvent {
    private List<Contact> contacts;

    public ContactsReceivedEvent(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public List<Contact> getContacts() {
        return contacts;
    }
}
