package com.skt.tmaphot.activity.main.review;


public class RealReviewRecyclerViewItem {

    private String id;
    private Object imgUrl;

    public RealReviewRecyclerViewItem(String id, Object imgUrl) {
        this.id = id;
        this.imgUrl = imgUrl;
    }

    public String getId() {
        return id;
    }

    public Object getImgUrl() {
        return imgUrl;
    }
}
