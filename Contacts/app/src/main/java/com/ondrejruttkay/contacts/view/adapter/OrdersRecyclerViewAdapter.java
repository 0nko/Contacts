package com.ondrejruttkay.contacts.view.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ondrejruttkay.contacts.BR;
import com.ondrejruttkay.contacts.R;
import com.ondrejruttkay.contacts.model.Order;
import com.ondrejruttkay.contacts.viewmodel.ContactDetailViewModel;

public class OrdersRecyclerViewAdapter extends RecyclerView.Adapter<OrdersRecyclerViewAdapter.BindingHolder> {

    private ContactDetailViewModel contactDetailViewModel;

    public OrdersRecyclerViewAdapter(ContactDetailViewModel contactDetailViewModel) {
        this.contactDetailViewModel = contactDetailViewModel;
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int type) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_list_item, parent, false);
        return new BindingHolder(v);
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        final Order contact = contactDetailViewModel.getOrders().get(position);
        holder.getBinding().setVariable(BR.order, contact);
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return contactDetailViewModel.getOrders().size();
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
