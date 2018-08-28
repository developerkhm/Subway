package com.skt.tmaphot;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;

import com.skt.tmaphot.client.BaseWebChromeClient;
import com.skt.tmaphot.client.BaseWebViewClient;
import com.skt.tmaphot.location.GPSData;

import org.apache.http.util.EncodingUtils;

public class MapWebViewActivity extends BaseWebViewActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        url = "http://shop.ordertable.co.kr/dev/";
        url = "http://dev.ordertable.co.kr/maps/view";

        webView.setWebViewClient(new BaseWebViewClient(baceContext, webView));
        webView.setWebChromeClient(new BaseWebChromeClient(baceContext, webView));
        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() != KeyEvent.ACTION_DOWN)
                    return true;

                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (webView.canGoBack()) {
                        webView.goBack();
                    } else {
                        finish();
                    }
                    return true;
                }
                return false;
            }
        });

        String postData = "lat="+ String.valueOf(GPSData.getInstance().getLatitude()) + "&lng=" + String.valueOf(GPSData.getInstance().getLongitude());

        webView.postUrl(url, EncodingUtils.getBytes(postData, "BASE64"));
    }
}
