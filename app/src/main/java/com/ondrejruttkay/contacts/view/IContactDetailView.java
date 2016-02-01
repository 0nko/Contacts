package com.ondrejruttkay.contacts.view;

import eu.inloop.viewmodel.IView;

/**
 * Created by onko on 30/01/2016.
 */
public interface IContactDetailView extends IView {
    void showLoading();
    void showOrders();
    void showEmpty();
    void refresh();
    void showMessage(String message);
}
