package com.skt.tmaphot;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class NestedScrollingView extends NestedScrollView {
    private int mState = RecyclerView.SCROLL_STATE_IDLE;

    public interface NestedScrollViewScrollStateListener {
        void onNestedScrollViewStateChanged(int state);
    }


    public void setScrollListener(NestedScrollViewScrollStateListener scrollListener) {
        this.mScrollListener = scrollListener;
    }

    private NestedScrollViewScrollStateListener mScrollListener;

    public NestedScrollingView(Context context) {
        super(context);
    }

    public NestedScrollingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NestedScrollingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

//    stopNestedScroll() -> SCROLL_STATE_IDLE
//    startNestedScroll() -> SCROLL_STATE_DRAGGING
//    dispatchNestedPreFling() -> SCROLL_STATE_FLINGING

    @Override
    public void stopNestedScroll() {
        dispatchScrollState(RecyclerView.SCROLL_STATE_IDLE);
        super.stopNestedScroll();
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        dispatchScrollState(RecyclerView.SCROLL_STATE_DRAGGING);
        return super.onStartNestedScroll(child, target, nestedScrollAxes);
    }


    @Override
    public boolean startNestedScroll(int axes) {
        boolean superScroll = super.startNestedScroll(axes);
        dispatchScrollState(RecyclerView.SCROLL_STATE_DRAGGING);
        return superScroll;
    }


    private void dispatchScrollState(int state) {
        if (mScrollListener != null ) {
            mScrollListener.onNestedScrollViewStateChanged(state);
            mState = state;
        }
    }

    @Override
    public boolean startNestedScroll(int axes, int type) {
        return super.startNestedScroll(axes, type);
    }

    @Override
    public void stopNestedScroll(int type) {
        super.stopNestedScroll(type);
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow, int type) {
        return super.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow, type);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow, int type) {
        return super.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow, type);
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow) {
        return super.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
        return super.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        return super.dispatchNestedFling(velocityX, velocityY, consumed);
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        return super.dispatchNestedPreFling(velocityX, velocityY);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int axes, int type) {
        return super.onStartNestedScroll(child, target, axes, type);
    }

    @Override
    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int axes, int type) {
        super.onNestedScrollAccepted(child, target, axes, type);
    }

    @Override
    public void onStopNestedScroll(@NonNull View target, int type) {
        super.onStopNestedScroll(target, type);
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
    }
}