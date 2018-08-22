package com.skt.tmaphot.activity.area;

public class PopularityAreaRecylerItem {
    private String areaName;
    private String areaId;

    public PopularityAreaRecylerItem(String areaName, String areaId) {
        this.areaName = areaName;
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public String getAreaId() {
        return areaId;
    }
}