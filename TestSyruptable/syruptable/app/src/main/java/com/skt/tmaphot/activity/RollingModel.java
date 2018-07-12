package com.skt.tmaphot.activity;

public class RollingModel {

    private String key = "";
    private String resId;

    public RollingModel(String key, String resId){
        this.key = key;
        this.resId = resId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getResId() {
        return resId;
    }

    public void setResId(String resId) {
        this.resId = resId;
    }
}
