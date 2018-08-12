package com.skt.tmaphot;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;

public class BaseWebViewActivity extends BaseActivity {


    protected String url;
    protected ObservableWebView webView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_webview_layout);
        baceContext = this;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        webView = (ObservableWebView)findViewById(R.id.base_webview);
    }
}
