package com.skt.tmaphot.activity.main.banner;

public class RollingModel {

    private String key = "";
    private Object resId;

    public RollingModel(String key, Object resId){
        this.key = key;
        this.resId = resId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getResId() {
        return resId;
    }

    public void setResId(Object resId) {
        this.resId = resId;
    }
}
