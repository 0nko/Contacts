package com.ondrejruttkay.contacts.client;

import com.ondrejruttkay.contacts.model.Contact;
import com.ondrejruttkay.contacts.model.Order;

import java.util.List;

/**
 * Created by onko on 28/01/2016.
 */
public class OrdersResponse {

    private List<Order> items;
    private Error error;

    public List<Order> getOrders() {
        return items;
    }

    public Error getError() {
        return error;
    }
}
