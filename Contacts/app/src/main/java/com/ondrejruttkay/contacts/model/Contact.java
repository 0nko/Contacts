package com.ondrejruttkay.contacts.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.Observable;
import android.view.View;
import android.widget.Toast;

import com.ondrejruttkay.contacts.BR;
import com.ondrejruttkay.contacts.ContactsApplication;
import com.ondrejruttkay.contacts.event.ContactSelectedEvent;

/**
 * Created by onko on 27/01/2016.
 */
public class Contact extends BaseObservable {

    private String id;
    private String name;
    private String phone;
    private String pictureUrl;

    public Contact() { }

    public Contact(String id, String name, String phoneNumber, String pictureUrl) {
        this.id = id;
        this.name = name;
        this.phone = phoneNumber;
        this.pictureUrl = pictureUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.contact);
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public void onClick(View view) {
        ContactsApplication.getEventBus().post(new ContactSelectedEvent(this));
    }
}
