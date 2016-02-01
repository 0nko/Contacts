package com.ondrejruttkay.contacts.view;

import android.support.annotation.IdRes;

import eu.inloop.viewmodel.IView;

/**
 * Created by onko on 30/01/2016.
 */
public interface INewContactView extends IView {
    void showMessage(String message);
    void close();
    void validate(@IdRes int view, boolean isValid);
}
