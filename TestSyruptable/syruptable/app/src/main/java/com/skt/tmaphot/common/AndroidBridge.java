package com.skt.tmaphot.common;

import android.webkit.JavascriptInterface;

import com.skt.tmaphot.location.GPSData;

/**
 * Created by home on 2018-05-26.
 */

public class AndroidBridge {

    public AndroidBridge() {
    }

    @JavascriptInterface
    public double getLatitudeValue() {
        return GPSData.LATITUDE;
    }

    @JavascriptInterface
    public double getLongitudeValue() {
        return GPSData.LONGITUDE;
    }

    @JavascriptInterface
    public String getPhoneNumber() {
        return CommonUtil.getInstance().getPhoneNumber();
    }
}
