package com.ondrejruttkay.contacts.view.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.ondrejruttkay.contacts.ContactsConfig;
import com.ondrejruttkay.contacts.R;
import com.ondrejruttkay.contacts.databinding.FragmentContactsBinding;
import com.ondrejruttkay.contacts.model.Contact;
import com.ondrejruttkay.contacts.view.IContactsView;
import com.ondrejruttkay.contacts.view.activity.ContactDetailActivity;
import com.ondrejruttkay.contacts.view.activity.NewContactActivity;
import com.ondrejruttkay.contacts.view.adapter.ContactsRecyclerViewAdapter;
import com.ondrejruttkay.contacts.viewmodel.ContactsViewModel;

import eu.inloop.viewmodel.base.ViewModelBaseFragment;

public class ContactsFragment extends ViewModelBaseFragment<IContactsView, ContactsViewModel> implements IContactsView {

    private FragmentContactsBinding binding;

    public ContactsFragment() {
    }

    @Nullable
    @Override
    public Class<ContactsViewModel> getViewModelClass() {
        return ContactsViewModel.class;
    }

    public static ContactsFragment newInstance() {
        return new ContactsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_contacts,
                container,
                false);

        setRetainInstance(true);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ContactsRecyclerViewAdapter adapter = new ContactsRecyclerViewAdapter(getViewModel());
        binding.contactsRecycler.setAdapter(adapter);
        binding.contactsRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        setupSwipeRefresh();
        setModelView(this);
    }

    @Override
    public void showLoading() {
        binding.swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        binding.swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showContactDetail(Contact contact) {
        Intent intent = new Intent(getContext(), ContactDetailActivity.class);
        intent.putExtra(ContactsConfig.CONTACT_INTENT_KEY, new GsonBuilder().create().toJson(contact));
        getActivity().startActivity(intent);
    }

    @Override
    public void addNewContact() {
        Intent intent = new Intent(getContext(), NewContactActivity.class);
        getActivity().startActivity(intent);
    }

    @Override
    public void showEmpty() {
        binding.containerEmpty.setVisibility(View.VISIBLE);
        binding.contactsRecycler.setVisibility(View.GONE);
    }

    @Override
    public void refreshContacts() {
        binding.contactsRecycler.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void showContacts() {
        binding.containerEmpty.setVisibility(View.GONE);
        binding.contactsRecycler.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    private void setupSwipeRefresh() {
        binding.swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (getViewModel() != null) {
                    getViewModel().loadContacts();
                }
            }
        });
    }
}
