package com.ondrejruttkay.contacts.view.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.ondrejruttkay.contacts.R;
import com.ondrejruttkay.contacts.databinding.ActivityNewContactBinding;

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
