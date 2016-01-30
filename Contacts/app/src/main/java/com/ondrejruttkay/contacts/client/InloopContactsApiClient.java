package com.ondrejruttkay.contacts.client;

import com.ondrejruttkay.contacts.ContactsApplication;
import com.ondrejruttkay.contacts.ContactsConfig;
import com.ondrejruttkay.contacts.event.ContactsRequestErrorEvent;
import com.ondrejruttkay.contacts.event.ContactsReceivedEvent;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;

/**
 * Created by onko on 28/01/2016.
 */
public class InloopContactsApiClient {

    private ContactsService contactsService;

    public InloopContactsApiClient() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
        Retrofit retrofitClient = new Retrofit.Builder()
                .client(client)
                .baseUrl(ContactsConfig.API_ENDPOINT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        contactsService = retrofitClient.create(ContactsService.class);
    }

    interface ContactsService {
        @GET("contactendpoint/v1/contact")
        Call<ContactsResponse> getContacts();
    }

    public void requestContacts() {
        Call<ContactsResponse> call = contactsService.getContacts();
        call.enqueue(new Callback<ContactsResponse>() {
            @Override
            public void onResponse(Response<ContactsResponse> response) {
                ContactsResponse contactsResponse = response.body();
                if (response.isSuccess()) {
                    if (contactsResponse != null && contactsResponse.getContacts() != null) {
                        ContactsApplication.getEventBus().post(new ContactsReceivedEvent(contactsResponse.getContacts()));
                    } else {
                        ContactsApplication.getEventBus().post(new ContactsRequestErrorEvent("Error: No data received"));
                    }
                } else {
                    if (contactsResponse.getError() != null) {
                        ContactsApplication.getEventBus().post(new ContactsRequestErrorEvent("Error: " + contactsResponse.getError().getMessage()));
                    } else {
                        ContactsApplication.getEventBus().post(new ContactsRequestErrorEvent("Unknown server error"));
                    }
                }
            }

            @Override
            public void onFailure(Throwable retrofitError) {
//                OnkoCycleApp.Bus.post(new AddressSearchError(location, retrofitError.getMessage()));
            }
        });
    }
}
