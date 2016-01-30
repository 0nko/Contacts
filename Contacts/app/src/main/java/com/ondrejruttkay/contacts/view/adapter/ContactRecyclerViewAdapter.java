package com.ondrejruttkay.contacts.view.adapter;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ondrejruttkay.contacts.BR;
import com.ondrejruttkay.contacts.R;
import com.ondrejruttkay.contacts.model.Contact;
import com.ondrejruttkay.contacts.viewmodel.ContactsViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ContactRecyclerViewAdapter extends RecyclerView.Adapter<ContactRecyclerViewAdapter.BindingHolder> {

    private ContactsViewModel contactsViewModel;

    public ContactRecyclerViewAdapter(ContactsViewModel contactsViewModel) {
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

    @BindingAdapter("bind:pictureUrl")
    public static void loadImage(ImageView imageView, String url) {
        if (TextUtils.isEmpty(url))
            Picasso.with(imageView.getContext()).load(R.drawable.placeholder).into(imageView);
        else
            Picasso.with(imageView.getContext()).load(url).into(imageView);
    }

    @BindingAdapter({"app:onClick"})
    public static void bindOnClick(View view, final Runnable runnable) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                runnable.run();
            }
        });
    }
}
