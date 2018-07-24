package com.skt.tmaphot.activity.main.store;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class StoreInfoImageViewPager extends ViewPager {
    public StoreInfoImageViewPager(Context context) {
        super(context);
    }

    public StoreInfoImageViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException e) {
        } catch (ArrayIndexOutOfBoundsException e) {

        }
        return false;
    }
}