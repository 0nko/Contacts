package com.ondrejruttkay.contacts.client;

import android.util.Log;

import com.ondrejruttkay.contacts.ContactsApplication;
import com.ondrejruttkay.contacts.ContactsConfig;
import com.ondrejruttkay.contacts.event.ContactAddedEvent;
import com.ondrejruttkay.contacts.event.ContactsReceivedEvent;
import com.ondrejruttkay.contacts.event.ContactsRequestErrorEvent;
import com.ondrejruttkay.contacts.event.NewContactRequestErrorEvent;
import com.ondrejruttkay.contacts.event.OrdersReceivedEvent;
import com.ondrejruttkay.contacts.event.OrdersRequestErrorEvent;
import com.ondrejruttkay.contacts.model.Contact;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by onko on 28/01/2016.
 */
public class InloopContactsApiClient {

    private ContactsService contactsService;
    private static final int CACHE_SIZE = 10 * 1024 * 1024;
    private static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            okhttp3.Request.Builder requestBuilder = chain.request().newBuilder();
            okhttp3.Response originalResponse = chain.proceed(requestBuilder.build());
            return originalResponse.newBuilder()
                    .removeHeader("Expires")
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, max-age=60")
                    .build();
        }
    };

    public InloopContactsApiClient() {

        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));

        // Setup caching
        try {
            File cacheFile = new File(ContactsApplication.getContext().getCacheDir(), "contacts.cache");
            Cache cache = new Cache(cacheFile, CACHE_SIZE);
            clientBuilder.cache(cache);
            clientBuilder.networkInterceptors().add(REWRITE_CACHE_CONTROL_INTERCEPTOR);
        } catch (Exception e) {
            Log.e("InloopContactsApiClient", "Caching error", e);
        }

        Retrofit retrofitClient = new Retrofit.Builder()
                .client(clientBuilder.build())
                .baseUrl(ContactsConfig.API_ENDPOINT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        contactsService = retrofitClient.create(ContactsService.class);
    }

    interface ContactsService {
        @GET("contactendpoint/v1/contact")
        Call<ContactsResponse> getContacts();

        @Headers({"Cache-Control: public, only-if-cached, max-stale=" + 60 * 60 * 24 * 28})
        @GET("contactendpoint/v1/contact")
        Call<ContactsResponse> getContactsFromCache();

        @GET("orderendpoint/v1/order/{id}")
        Call<OrdersResponse> getOrders(@Path("id") String id);

        @Headers({"Content-Type: application/json"})
        @POST("contactendpoint/v1/contact")
        Call<NewContactResponse> addContact(@Body Contact contact);
    }

    private Callback<ContactsResponse> contactsCallback = new Callback<ContactsResponse>() {
        @Override
        public void onResponse(Response<ContactsResponse> response) {
            ContactsResponse contactsResponse = response.body();

            Log.d("Contacts cache", "" + response.raw().cacheResponse());
            Log.d("Contacts network", "" + response.raw().networkResponse());

            if (response.isSuccess()) {
                if (contactsResponse != null && contactsResponse.getContacts() != null) {
                    ContactsApplication.getEventBus().post(new ContactsReceivedEvent(contactsResponse.getContacts()));
                } else {
                    ContactsApplication.getEventBus().post(new ContactsRequestErrorEvent("Error: No data received"));
                }
            } else {
                if (contactsResponse != null && contactsResponse.getError() != null) {
                    ContactsApplication.getEventBus().post(new ContactsRequestErrorEvent("Error: " + contactsResponse.getError().getMessage()));
                } else {
                    ContactsApplication.getEventBus().post(new ContactsRequestErrorEvent("Unknown server error"));
                }
            }
        }

        @Override
        public void onFailure(Throwable retrofitError) {
            ContactsApplication.getEventBus().post(new ContactsRequestErrorEvent("Error: " + retrofitError.getMessage()));
        }
    };

    public void requestContactsFromCache() {
        Call<ContactsResponse> call = contactsService.getContactsFromCache();
        call.enqueue(contactsCallback);
    }

    public void requestContacts() {
        Call<ContactsResponse> call = contactsService.getContacts();
        call.enqueue(contactsCallback);
    }

    public void requestOrders(String id) {
        Call<OrdersResponse> call = contactsService.getOrders(id);
        call.enqueue(new Callback<OrdersResponse>() {
            @Override
            public void onResponse(Response<OrdersResponse> response) {
                OrdersResponse ordersResponse = response.body();
                if (response.isSuccess()) {
                    if (ordersResponse != null && ordersResponse.getOrders() != null) {
                        ContactsApplication.getEventBus().post(new OrdersReceivedEvent(ordersResponse.getOrders()));
                    } else {
                        ContactsApplication.getEventBus().post(new OrdersRequestErrorEvent("Error: No data received"));
                    }
                } else {
                    if (ordersResponse != null && ordersResponse.getError() != null) {
                        ContactsApplication.getEventBus().post(new OrdersRequestErrorEvent("Error: " + ordersResponse.getError().getMessage()));
                    } else {
                        ContactsApplication.getEventBus().post(new OrdersRequestErrorEvent("Unknown server error"));
                    }
                }
            }

            @Override
            public void onFailure(Throwable retrofitError) {
                ContactsApplication.getEventBus().post(new OrdersRequestErrorEvent("Error: " + retrofitError.getMessage()));
            }
        });
    }

    public void addNewContact(Contact contact) {
        Call<NewContactResponse> call = contactsService.addContact(contact);
        call.enqueue(new Callback<NewContactResponse>() {
            @Override
            public void onResponse(Response<NewContactResponse> response) {
                NewContactResponse newContactResponse = response.body();
                if (response.isSuccess()) {
                    if (newContactResponse != null) {
                        ContactsApplication.getEventBus().post(new ContactAddedEvent());
                    } else {
                        ContactsApplication.getEventBus().post(new NewContactRequestErrorEvent("Error: No data received"));
                    }
                } else {
                    if (newContactResponse != null && newContactResponse.getError() != null) {
                        ContactsApplication.getEventBus().post(new NewContactRequestErrorEvent("Error: " + newContactResponse.getError().getMessage()));
                    } else {
                        ContactsApplication.getEventBus().post(new NewContactRequestErrorEvent("Unknown server error"));
                    }
                }
            }

            @Override
            public void onFailure(Throwable retrofitError) {
                ContactsApplication.getEventBus().post(new NewContactRequestErrorEvent("Error: " + retrofitError.getMessage()));
            }
        });
    }
}
