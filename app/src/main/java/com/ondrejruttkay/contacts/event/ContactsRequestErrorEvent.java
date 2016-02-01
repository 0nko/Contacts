package com.ondrejruttkay.contacts.event;

import com.ondrejruttkay.contacts.model.Error;

/**
 * Created by onko on 28/01/2016.
 */
public class ContactsRequestErrorEvent extends Error {
    public ContactsRequestErrorEvent(String message) {
        super(message);
    }
}
