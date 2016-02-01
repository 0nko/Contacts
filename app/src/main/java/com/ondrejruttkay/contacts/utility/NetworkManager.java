package com.ondrejruttkay.contacts.utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.ondrejruttkay.contacts.ContactsApplication;

public class NetworkManager {
    public static boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) ContactsApplication.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected());
    }
}
