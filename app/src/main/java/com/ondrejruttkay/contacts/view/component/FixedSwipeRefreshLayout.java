package com.ondrejruttkay.contacts.view.component;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;

/**
 * Created by onko on 30/01/2016.
 */
public class FixedSwipeRefreshLayout extends SwipeRefreshLayout {

    private boolean mMeasured = false;
    private boolean mPreMeasureRefreshing = false;

    public FixedSwipeRefreshLayout(Context context) {
        super(context);
    }

    public FixedSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (!mMeasured) {
            mMeasured = true;
            setRefreshing(mPreMeasureRefreshing);
        }
    }

    @Override
    public void setRefreshing(boolean refreshing) {
        if (mMeasured) {
            super.setRefreshing(refreshing);
        } else {
            mPreMeasureRefreshing = refreshing;
        }
    }
}