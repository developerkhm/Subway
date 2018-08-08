package com.skt.tmaphot.activity.main.menu;


public class MainMenuRecyclerViewItem {
    private String menuType;
    private String menuImageUrl;
    private int res;

    public MainMenuRecyclerViewItem(String menuType, String menuImageUrl, int res) {
        this.menuType = menuType;
        this.menuImageUrl = menuImageUrl;
        this.res = res;
    }

    public String getMenuType() {
        return menuType;
    }

    public String getMenuImageUrl() {
        return menuImageUrl;
    }

    public int getRes() {
        return res;
    }
}
