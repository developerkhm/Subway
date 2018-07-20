package com.skt.tmaphot.activity.main.hotplace;

public class HotplaceGridViewItem {
    private String url;
    private String title;
    private String menu;
    private String distance;
    private String sale;
    private String review;

    public String getReview() {
        return review;
    }

    public HotplaceGridViewItem(String url, String title, String menu, String distance, String sale, String review) {
        this.url = url;
        this.title = title;
        this.menu = menu;
        this.distance = distance;
        this.sale = sale;
        this.review = review;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getSale() {
        return sale;
    }

    public void setSale(String sale) {
        this.sale = sale;
    }
}
