package com.ondrejruttkay.contacts.view.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.gson.GsonBuilder;
import com.ondrejruttkay.contacts.ContactsConfig;
import com.ondrejruttkay.contacts.R;
import com.ondrejruttkay.contacts.databinding.ActivityContactDetailBinding;
import com.ondrejruttkay.contacts.databinding.ActivityNewContactBinding;
import com.ondrejruttkay.contacts.model.Contact;
import com.ondrejruttkay.contacts.view.fragment.ContactDetailFragment;

import eu.inloop.viewmodel.base.ViewModelBaseActivity;

public class NewContactActivity extends ViewModelBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityNewContactBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_new_contact);

        setSupportActionBar(binding.toolbar.actionbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
    }

    @Override
    public Class getViewModelClass() {
        return null;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
