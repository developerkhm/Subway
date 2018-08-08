package com.skt.tmaphot.activity.main.coupon;

import com.skt.tmaphot.activity.IRecyclerItem;

public class CouponRecyclerViewItem implements IRecyclerItem {
    private String id;
    private String url;
    private String title;
    private String menu;
    private String type;
    private String distance;
    private String sale;
    private String preprice;
    private String price;

    public CouponRecyclerViewItem(String id, String url, String title, String menu, String type, String distance, String sale, String preprice, String price) {
        this.id = id;
        this.url = url;
        this.title = title;
        this.menu = menu;
        this.type = type;
        this.distance = distance;
        this.sale = sale;
        this.preprice = preprice;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public String getMenu() {
        return menu;
    }

    public String getType() {
        return type;
    }

    public String getDistance() {
        return distance;
    }

    public String getSale() {
        return sale;
    }

    public String getPreprice() {
        return preprice;
    }

    public String getPrice() {
        return price;
    }
}
