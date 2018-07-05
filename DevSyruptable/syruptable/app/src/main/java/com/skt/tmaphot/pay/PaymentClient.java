package com.skt.tmaphot.pay;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.skt.tmaphot.MainActivity;
import com.skt.tmaphot.SyrupMainActivity;

import static com.skt.tmaphot.MainActivity.FILECHOOSER_LOLLIPOP_REQ_CODE;
import static com.skt.tmaphot.MainActivity.FILECHOOSER_RESULTCODE;

public class PaymentClient {

    private Activity activity;

    public final int FILECHOOSER_RESULTCODE = 1;
    public final int FILECHOOSER_LOLLIPOP_REQ_CODE = 2;
    public ValueCallback<Uri> mUploadMessage;
    public ValueCallback<Uri[]> filePathCallbackLollipop;


    public PaymentClient(Activity activity){
        this.activity = activity;
    }

    // For Android 3.0-
    @SuppressWarnings("unused")
    public void openFileChooser(ValueCallback<Uri> uploadMsg){
        mUploadMessage = uploadMsg;
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("*/*");
        activity.startActivityForResult(Intent.createChooser(i,"File Chooser"),FILECHOOSER_RESULTCODE);
    }

    // For Android 3.0+
    @SuppressWarnings("unused")
    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType){
        mUploadMessage = uploadMsg;
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("*/*");
        activity.startActivityForResult(Intent.createChooser(i,"File Browser"), FILECHOOSER_RESULTCODE);
    }

    // For Android 4.1+
    @SuppressWarnings("unused")
    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture){
        mUploadMessage = uploadMsg;
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("*/*");
        activity.startActivityForResult(Intent.createChooser(i,"File Browser"), FILECHOOSER_RESULTCODE);
    }

    // For Android 5.0+
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams){
        Log.d("MainActivity", "5.0+");
        if(filePathCallbackLollipop != null){
            filePathCallbackLollipop.onReceiveValue(null);
            filePathCallbackLollipop = null;
        }

        filePathCallbackLollipop = filePathCallback ;
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        activity.startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_LOLLIPOP_REQ_CODE);

        return true;
    }
}
