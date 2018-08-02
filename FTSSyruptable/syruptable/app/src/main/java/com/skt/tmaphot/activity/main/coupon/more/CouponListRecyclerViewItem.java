package com.skt.tmaphot.activity.main.coupon.more;

public class CouponListRecyclerViewItem {
    private String url;
    private String id;
    private String name;
    private String menu;
    private String type;
    private String sale;
    private String preprice;
    private String price;

    public CouponListRecyclerViewItem(String url, String id, String name, String menu, String type, String sale, String preprice, String price) {
        this.url = url;
        this.id = id;
        this.name = name;
        this.menu = menu;
        this.type = type;
        this.sale = sale;
        this.preprice = preprice;
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMenu() {
        return menu;
    }

    public String getType() {
        return type;
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
