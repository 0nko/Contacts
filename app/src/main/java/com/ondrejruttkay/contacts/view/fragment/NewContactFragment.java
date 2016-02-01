package com.ondrejruttkay.contacts.view.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ondrejruttkay.contacts.R;
import com.ondrejruttkay.contacts.databinding.FragmentNewContactBinding;
import com.ondrejruttkay.contacts.view.INewContactView;
import com.ondrejruttkay.contacts.viewmodel.NewContactViewModel;

import eu.inloop.viewmodel.base.ViewModelBaseFragment;

public class NewContactFragment extends ViewModelBaseFragment<INewContactView, NewContactViewModel> implements INewContactView {

    private FragmentNewContactBinding binding;

    @Nullable
    @Override
    public Class<NewContactViewModel> getViewModelClass() {
        return NewContactViewModel.class;
    }

    public static NewContactFragment newInstance() {
        return new NewContactFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_new_contact,
                container,
                false);

        binding.setNewContactViewModel(getViewModel());

        setRetainInstance(true);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setModelView(this);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void close() {
        getActivity().finish();
    }

    @Override
    public void validate(@IdRes int view, boolean isValid) {
        switch (view) {
            case R.id.new_contact_name:
                binding.nameLayout.setErrorEnabled(!isValid);
                if (!isValid)
                    binding.nameLayout.setError(getString(R.string.name_error));
                break;
            case R.id.new_contact_phone:
                binding.phoneLayout.setErrorEnabled(!isValid);
                if (!isValid)
                    binding.phoneLayout.setError(getString(R.string.phone_error));
                break;
        }
    }
}
