package com.ondrejruttkay.contacts;

import android.app.Application;
import android.content.Context;

import com.ondrejruttkay.contacts.client.InloopContactsApiClient;
import com.squareup.otto.Bus;

/**
 * Created by onko on 27/01/2016.
 */
public class ContactsApplication extends Application {
    private static ContactsApplication instance;
    private static Bus eventBus;
    private static InloopContactsApiClient apiClient;

    public ContactsApplication() {
        instance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        eventBus = new Bus();
        apiClient = new InloopContactsApiClient();
    }

    public static InloopContactsApiClient getApiClient() {
        return apiClient;
    }

    public static Context getContext() {
        return instance;
    }

    public static Bus getEventBus() {
        return eventBus;
    }
}
