package com.ondrejruttkay.contacts.view;

import eu.inloop.viewmodel.IView;

/**
 * Created by onko on 30/01/2016.
 */
public interface IContactDetailView extends IView {
    void showProgress();
    void showOrders();
    void showNoOrders();
    void refresh();
    void showMessage(String message);
}
