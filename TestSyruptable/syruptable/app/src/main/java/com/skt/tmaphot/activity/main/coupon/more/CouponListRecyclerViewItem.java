package com.skt.tmaphot.activity.main.coupon.more;

public class CouponListRecyclerViewItem {
    private String url;
    private String title;
    private String menu;
    private String distance;
    private String sale;

    public CouponListRecyclerViewItem(String url, String title, String menu, String distance, String sale) {
        this.url = url;
        this.title = title;
        this.menu = menu;
        this.distance = distance;
        this.sale = sale;
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
