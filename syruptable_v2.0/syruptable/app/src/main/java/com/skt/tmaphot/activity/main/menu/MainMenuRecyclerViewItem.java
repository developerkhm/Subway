package com.skt.tmaphot.activity.main.menu;

public class MainMenuRecyclerViewItem {
    private String cate;
//    private String middle_cd;
//    private String small_cd;
    private String imageUrl;
    private int res;

    public MainMenuRecyclerViewItem(String cate, String imageUrl, int res) {
        this.cate = cate;
//        this.middle_cd = middle_cd;
//        this.small_cd = small_cd;
        this.imageUrl = imageUrl;
        this.res = res;
    }

//    public String getLarge_cd() {
//        return large_cd;
//    }
//
//    public void setLarge_cd(String large_cd) {
//        this.large_cd = large_cd;
//    }
//
//    public String getMiddle_cd() {
//        return middle_cd;
//    }
//
//    public void setMiddle_cd(String middle_cd) {
//        this.middle_cd = middle_cd;
//    }
//
//    public String getSmall_cd() {
//        return small_cd;
//    }
//
//    public void setSmall_cd(String small_cd) {
//        this.small_cd = small_cd;
//    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }

    public String getCate() {
        return cate;
    }
}
