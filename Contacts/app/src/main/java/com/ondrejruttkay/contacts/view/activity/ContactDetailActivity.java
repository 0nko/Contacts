package com.ondrejruttkay.contacts.view.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.gson.GsonBuilder;
import com.ondrejruttkay.contacts.ContactsConfig;
import com.ondrejruttkay.contacts.R;
import com.ondrejruttkay.contacts.databinding.ActivityContactDetailBinding;
import com.ondrejruttkay.contacts.databinding.ActivityContactsBinding;
import com.ondrejruttkay.contacts.model.Contact;
import com.ondrejruttkay.contacts.view.fragment.ContactDetailFragment;

import eu.inloop.viewmodel.base.ViewModelBaseActivity;

public class ContactDetailActivity extends ViewModelBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityContactDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_contact_detail);

        Intent intent = getIntent();
        String contactJson = intent.getStringExtra(ContactsConfig.CONTACT_INTENT_KEY);
        Contact contact = new GsonBuilder().create().fromJson(contactJson, Contact.class);

        setSupportActionBar(binding.toolbar.actionbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(contact.getName());

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.contact_detail_frame, ContactDetailFragment.newInstance(contact), "contact-detail-fragment").commit();
        }
    }

    @Override
    public Class getViewModelClass() {
        return null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
