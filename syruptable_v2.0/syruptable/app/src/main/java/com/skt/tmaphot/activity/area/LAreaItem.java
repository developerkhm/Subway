package com.skt.tmaphot.activity.area;

public class LAreaItem {
    private int preid;
    private int id;
    private String area;
    private String lati;
    private String longti;

    public LAreaItem() { }

    public int getPreid() {
        return preid;
    }

    public int getId() {
        return id;
    }

    public String getArea() {
        return area;
    }

    public String getLati() {
        return lati;
    }

    public String getLongti() {
        return longti;
    }

    public void setPreid(int preid) {
        this.preid = preid;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setLati(String lati) {
        this.lati = lati;
    }

    public void setLongti(String longti) {
        this.longti = longti;
    }
}
