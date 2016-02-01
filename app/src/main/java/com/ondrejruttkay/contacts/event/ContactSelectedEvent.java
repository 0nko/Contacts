package com.ondrejruttkay.contacts.event;

import com.ondrejruttkay.contacts.model.Contact;

/**
 * Created by onko on 30/01/2016.
 */
public class ContactSelectedEvent {
    private Contact contact;

    public ContactSelectedEvent(Contact contact) {
        this.contact = contact;
    }

    public Contact getContact() {
        return contact;
    }
}
