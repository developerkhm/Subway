package com.skt.tmaphot.activity.main.hotdeal;

public class HotdealRecyclerViewItem {

    private String id;
    private String url;
    private String grade;
    private String title;
    private String menu;
    private String distance;
    private String sale;

    public HotdealRecyclerViewItem(String id, String url, String grade, String title, String menu, String distance, String sale) {
        this.id = id;
        this.url = url;
        this.grade = grade;
        this.title = title;
        this.menu = menu;
        this.distance = distance;
        this.sale = sale;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getGrade() {
        return grade;
    }

    public String getTitle() {
        return title;
    }

    public String getMenu() {
        return menu;
    }

    public String getDistance() {
        return distance;
    }

    public String getSale() {
        return sale;
    }
}
