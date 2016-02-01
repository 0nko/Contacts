package com.ondrejruttkay.contacts.view.activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.ondrejruttkay.contacts.ContactsApplication;
import com.ondrejruttkay.contacts.R;
import com.ondrejruttkay.contacts.databinding.ActivityContactsBinding;
import com.ondrejruttkay.contacts.event.AddContactEvent;
import com.ondrejruttkay.contacts.viewmodel.ContactsViewModel;

import eu.inloop.viewmodel.base.ViewModelBaseActivity;

public class ContactsActivity extends ViewModelBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityContactsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_contacts);
        setSupportActionBar(binding.toolbar.actionbar);
    }

    @Override
    public Class getViewModelClass() {
        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_contacts, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add_contact:
                ContactsApplication.getEventBus().post(new AddContactEvent());
                break;
        }
        return true;
    }
}
