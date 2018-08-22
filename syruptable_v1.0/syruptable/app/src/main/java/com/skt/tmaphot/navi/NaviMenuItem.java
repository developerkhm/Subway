package com.skt.tmaphot.navi;

public class NaviMenuItem {
    String id;
    String menu;

    public NaviMenuItem(String id, String menu) {
        this.id = id;
        this.menu = menu;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }
}
