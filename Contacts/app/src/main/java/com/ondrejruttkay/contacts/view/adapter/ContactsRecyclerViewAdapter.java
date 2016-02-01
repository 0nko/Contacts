package com.ondrejruttkay.contacts.view.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ondrejruttkay.contacts.BR;
import com.ondrejruttkay.contacts.R;
import com.ondrejruttkay.contacts.model.Contact;
import com.ondrejruttkay.contacts.viewmodel.ContactsViewModel;

public class ContactsRecyclerViewAdapter extends RecyclerView.Adapter<ContactsRecyclerViewAdapter.BindingHolder> {

    private ContactsViewModel contactsViewModel;

    public ContactsRecyclerViewAdapter(ContactsViewModel contactsViewModel) {
        this.contactsViewModel = contactsViewModel;
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int type) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_list_item, parent, false);
        return new BindingHolder(v);
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        final Contact contact = contactsViewModel.getContacts().get(position);
        holder.getBinding().setVariable(BR.contact, contact);
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return contactsViewModel.getContacts().size();
    }

    public static class BindingHolder  extends RecyclerView.ViewHolder {

        private ViewDataBinding binding;

        public BindingHolder(View rowView) {
            super(rowView);
            binding = DataBindingUtil.bind(rowView);
        }

        public ViewDataBinding getBinding() {
            return binding;
        }
    }
}
