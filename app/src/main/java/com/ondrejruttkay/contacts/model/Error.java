package com.ondrejruttkay.contacts.model;

/**
 * Created by onko on 28/01/2016.
 */
public class Error {

    private String message;
    private int code;

    public Error(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}
