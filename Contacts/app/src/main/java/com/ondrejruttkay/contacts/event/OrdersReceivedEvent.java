package com.ondrejruttkay.contacts.event;

import com.ondrejruttkay.contacts.model.Contact;
import com.ondrejruttkay.contacts.model.Order;

import java.util.List;

/**
 * Created by onko on 28/01/2016.
 */
public class OrdersReceivedEvent {
    private List<Order> orders;

    public OrdersReceivedEvent(List<Order> orders) {
        this.orders = orders;
    }

    public List<Order> getOrders() {
        return orders;
    }
}
