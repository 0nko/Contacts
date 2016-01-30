package com.ondrejruttkay.contacts.utility;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ondrejruttkay.contacts.ContactsApplication;
import com.ondrejruttkay.contacts.model.Contact;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by onko on 29/01/2016.
 */
public class Cache {

    private static final String CONTACTS_PREFS_KEY = "CONTACTS_PREFS_KEY";

    public void saveContacts(List<Contact> contacts) {
        Gson gson = new GsonBuilder().create();
        String contactsJson = gson.toJson(contacts);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ContactsApplication.getContext());
        prefs.edit().putString(CONTACTS_PREFS_KEY, contactsJson).apply();
    }

    public List<Contact> loadContacts() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ContactsApplication.getContext());
        String contactsJson = prefs.getString(CONTACTS_PREFS_KEY, null);

        List<Contact> contacts = new ArrayList<>();
        if (!TextUtils.isEmpty(contactsJson)) {
            Gson gson = new GsonBuilder().create();
            contacts = Arrays.asList(gson.fromJson(contactsJson, Contact[].class));
        }
        return contacts;
    }
}
