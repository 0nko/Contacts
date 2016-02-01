package com.ondrejruttkay.contacts.view.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ondrejruttkay.contacts.R;
import com.ondrejruttkay.contacts.databinding.FragmentContactDetailBinding;
import com.ondrejruttkay.contacts.model.Contact;
import com.ondrejruttkay.contacts.view.IContactDetailView;
import com.ondrejruttkay.contacts.view.adapter.OrdersRecyclerViewAdapter;
import com.ondrejruttkay.contacts.viewmodel.ContactDetailViewModel;

import eu.inloop.viewmodel.base.ViewModelBaseFragment;

public class ContactDetailFragment extends ViewModelBaseFragment<IContactDetailView, ContactDetailViewModel> implements IContactDetailView {

    private FragmentContactDetailBinding binding;
    private Contact contact;

    @Nullable
    @Override
    public Class<ContactDetailViewModel> getViewModelClass() {
        return ContactDetailViewModel.class;
    }

    public static ContactDetailFragment newInstance(Contact contact) {
        ContactDetailFragment fragment = new ContactDetailFragment();
        fragment.setArguments(contact);
        return fragment;
    }

    public void setArguments(Contact contact) {
        this.contact = contact;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_contact_detail,
                container,
                false);

        if (getViewModel() != null) {
            getViewModel().setContact(contact);
            binding.setContactViewModel(getViewModel());

        }

        setRetainInstance(true);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        OrdersRecyclerViewAdapter adapter = new OrdersRecyclerViewAdapter(getViewModel());
        binding.ordersRecycler.setAdapter(adapter);
        binding.ordersRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        setModelView(this);

        if (getViewModel() != null) {
            getViewModel().loadOrders();
        }
    }

    @Override
    public void showLoading() {
        binding.containerProgress.setVisibility(View.VISIBLE);
        binding.containerEmpty.setVisibility(View.GONE);
        binding.ordersRecycler.setVisibility(View.GONE);
    }

    @Override
    public void showOrders() {
        binding.containerProgress.setVisibility(View.GONE);
        binding.containerEmpty.setVisibility(View.GONE);
        binding.ordersRecycler.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEmpty() {
        binding.containerProgress.setVisibility(View.GONE);
        binding.containerEmpty.setVisibility(View.VISIBLE);
        binding.ordersRecycler.setVisibility(View.GONE);
    }

    @Override
    public void refresh() {
        binding.ordersRecycler.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }
}
