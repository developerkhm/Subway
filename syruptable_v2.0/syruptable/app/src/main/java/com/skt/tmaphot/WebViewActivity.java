package com.skt.tmaphot;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieSyncManager;

import com.skt.tmaphot.activity.blog.MyBlogActivity;
import com.skt.tmaphot.client.ShopWebChromeClient;
import com.skt.tmaphot.client.ShopWebViewClient;

public class WebViewActivity extends BaseWebViewActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        toolbar.setVisibility(View.GONE);

        Intent intent = getIntent();
        String url = intent.getStringExtra("urlid");

//        if (menuId.equals("noti")) {
//            finish();
//        } else if (menuId.equals("shop")) {
//            url = "http://dev.ordertable.co.kr/";
//        } else if (menuId.equals("dining")) {
//            url = "https://shop.ordertable.co.kr/share";
//        } else if (menuId.equals("365")) {
//            url = "https://shop.ordertable.co.kr/delivery365";
//        } else if (menuId.equals("내블로그")) {
//            ActivityStart(new Intent(baceContext, MyBlogActivity.class), null);
//        } else if (menuId.equals("1:1문의")) {
//
//        }else if()

        webView.setWebViewClient(new ShopWebViewClient(this, webView));
        webView.setWebChromeClient(new ShopWebChromeClient(this, webView));
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

    @Override
    public void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            //noinspection deprecation
            CookieSyncManager.getInstance().startSync();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            CookieSyncManager.getInstance().stopSync();
        }
    }

    @Override
    protected void onDestroy() {
        Log.d("YUYU", "LoginWebViewActivity onDestroy");
        super.onDestroy();
    }
}
