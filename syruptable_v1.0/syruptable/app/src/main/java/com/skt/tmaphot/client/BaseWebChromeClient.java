package com.skt.tmaphot.client;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.webkit.GeolocationPermissions;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.skt.tmaphot.ObservableWebView;

import static com.skt.tmaphot.temp.MainActivityTab.placeholderFragment;
import static com.skt.tmaphot.temp.MainActivityWebViewUp.FILECHOOSER_LOLLIPOP_REQ_CODE;
import static com.skt.tmaphot.temp.MainActivityWebViewUp.FILECHOOSER_RESULTCODE;

/**
 * Created by home on 2018-05-26.
 */

public class BaseWebChromeClient extends WebChromeClient {

    private Context context;
    private ObservableWebView webView;

    public BaseWebChromeClient(Context context, ObservableWebView webView) {
        this.context = context;
        this.webView = webView;
    }

    @Override
    public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
        new AlertDialog.Builder(view.getContext())
                .setTitle("")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok,
                        new AlertDialog.OnClickListener(){
                            public void onClick(DialogInterface dialog, int which) {
                                result.confirm();
                            }
                        })
                .setCancelable(false)
                .create()
                .show();
        return true;
    }

    @Override
    public boolean onJsConfirm(WebView view, String url, String message,
                               final JsResult result) {
        new AlertDialog.Builder(view.getContext())
                .setTitle("")
                .setMessage(message)
                .setPositiveButton("Yes",
                        new AlertDialog.OnClickListener(){
                            public void onClick(DialogInterface dialog, int which) {
                                result.confirm();
                            }
                        })
                .setNegativeButton("No",
                        new AlertDialog.OnClickListener(){
                            public void onClick(DialogInterface dialog, int which) {
                                result.cancel();
                            }
                        })
                .setCancelable(false)
                .create()
                .show();
        return true;
    }
}
