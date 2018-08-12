package com.skt.tmaphot.activity.main.review;


public class RealReviewRecyclerViewItem {

    private String id;
    private String imgUrl;

    public RealReviewRecyclerViewItem(String id, String imgUrl) {
        this.id = id;
        this.imgUrl = imgUrl;
    }

    public String getId() {
        return id;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}
