package com.ondrejruttkay.contacts.event;

import com.ondrejruttkay.contacts.model.Error;

/**
 * Created by onko on 28/01/2016.
 */
public class NewContactRequestErrorEvent extends Error {
    public NewContactRequestErrorEvent(String message) {
        super(message);
    }
}
