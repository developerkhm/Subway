package com.skt.tmaphot.activity.main.hotdeal;

public class HotdealRecyclerViewItem {
    private String url;
    private String title;
    private String menu;


    public HotdealRecyclerViewItem(String url, String title, String menu, String distance, String sale) {
        this.url = url;
        this.title = title;
        this.menu = menu;

    }

    public String getUrl() { return url; }
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
}
