package com.skt.tmaphot.activity.main.review;

import com.skt.tmaphot.activity.IRecyclerItem;

public class RealReviewRecyclerViewItem implements IRecyclerItem {


    private String text;

    public RealReviewRecyclerViewItem(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
