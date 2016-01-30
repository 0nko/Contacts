package com.ondrejruttkay.contacts.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ondrejruttkay.contacts.R;
import com.ondrejruttkay.contacts.databinding.FragmentContactsBinding;
import com.ondrejruttkay.contacts.model.Contact;
import com.ondrejruttkay.contacts.view.IContactsView;
import com.ondrejruttkay.contacts.view.adapter.ContactRecyclerViewAdapter;
import com.ondrejruttkay.contacts.viewmodel.ContactsViewModel;

import eu.inloop.viewmodel.base.ViewModelBaseFragment;

public class ContactsFragment extends ViewModelBaseFragment<IContactsView, ContactsViewModel> implements IContactsView {

    FragmentContactsBinding binding;

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

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ContactRecyclerViewAdapter adapter = new ContactRecyclerViewAdapter(getViewModel());
        binding.contactsRecycler.setAdapter(adapter);
        binding.contactsRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        setupSwipeRefresh();
        setModelView(this);
    }

    @Override
    public void showProgress() {
        binding.swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        binding.swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showContactDetail(Contact contact) {
    }

    @Override
    public void showNoData() {
        binding.containerEmpty.setVisibility(View.VISIBLE);
        binding.containerOffline.setVisibility(View.GONE);
        binding.contactsRecycler.setVisibility(View.GONE);
    }

    @Override
    public void showOffline() {
        binding.containerEmpty.setVisibility(View.GONE);
        binding.containerOffline.setVisibility(View.VISIBLE);
        binding.contactsRecycler.setVisibility(View.GONE);
    }

    @Override
    public void refresh() {
        binding.contactsRecycler.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void showContacts() {
        binding.containerEmpty.setVisibility(View.GONE);
        binding.containerOffline.setVisibility(View.GONE);
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
