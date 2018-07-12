package com.skt.tmaphot.common;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.skt.tmaphot.MainActivity;
import com.skt.tmaphot.location.GPSData;

/**
 * Created by home on 2018-05-26.
 */

public class AndroidBridge {

    public AndroidBridge() {
    }

    @JavascriptInterface
    public double getLatitudeValue() {
        return GPSData.latitude;
    }

    @JavascriptInterface
    public double getLongitudeValue() {
        return GPSData.longitude;
    }

    @JavascriptInterface
    public String getPhoneNumber() {
        return CommonUtil.getInstance().getPhoneNumber();
    }
}
