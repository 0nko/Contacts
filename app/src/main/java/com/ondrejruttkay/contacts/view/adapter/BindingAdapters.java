package com.ondrejruttkay.contacts.view.adapter;

import android.databinding.BindingAdapter;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.ondrejruttkay.contacts.R;
import com.squareup.picasso.Picasso;

/**
 * Created by onko on 31/01/2016.
 */
public class BindingAdapters {

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
