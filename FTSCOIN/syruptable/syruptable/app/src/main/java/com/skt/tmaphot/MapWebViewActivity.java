package com.skt.tmaphot;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;

import com.skt.tmaphot.client.BaseWebChromeClient;
import com.skt.tmaphot.client.BaseWebViewClient;

public class MapWebViewActivity extends BaseWebViewActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        url = "http://shop.ordertable.co.kr/dev/";

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

        webView.loadUrl(url);
    }
}
