package com.ondrejruttkay.contacts.event;

import com.ondrejruttkay.contacts.model.Error;

/**
 * Created by onko on 28/01/2016.
 */
public class OrdersRequestErrorEvent extends Error {
    public OrdersRequestErrorEvent(String message) {
        super(message);
    }
}
