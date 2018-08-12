package com.skt.tmaphot;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;

import com.skt.tmaphot.client.BaseWebChromeClient;
import com.skt.tmaphot.client.BaseWebViewClient;
import com.skt.tmaphot.client.ShopWebChromeClient;
import com.skt.tmaphot.client.ShopWebViewClient;
import com.skt.tmaphot.client.SyrupWebChromeClient;
import com.skt.tmaphot.location.GPSData;

import org.apache.http.util.EncodingUtils;

public class LoginWebViewActivity extends BaseWebViewActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        url = "http://dev.ordertable.co.kr/member/login";

        webView.setWebViewClient(new ShopWebViewClient((Activity) baceContext, webView));
        webView.setWebChromeClient(new ShopWebChromeClient((Activity)baceContext, webView));
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

        webView.loadUrl(url);
    }
}
