package com.ondrejruttkay.contacts.model;

/**
 * Created by onko on 30/01/2016.
 */
public class Order {
    private String name;
    private int count;

    public Order(String name, int count) {
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
