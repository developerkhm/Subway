package com.skt.tmaphot.temp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieSyncManager;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Toast;

import com.skt.tmaphot.R;
import com.skt.tmaphot.client.SyrupWebChromeClient;
import com.skt.tmaphot.client.ShopWebViewClient;
import com.skt.tmaphot.common.AndroidBridge;

public class MainActivityWebViewUp extends AppCompatActivity {

    public static MainActivityWebViewUp mContext;
    public static final String ENTRY_URL = "https://shop.ordertable.co.kr/intro";

    public static final String HOTPLACE_URL = "https://shop.ordertable.co.kr/hotplace";
    //    public static final String HOTPLACE_URL = "http://dev.ordertable.co.kr/";
    public static final String SHOP_URL = "https://shop.ordertable.co.kr/";
    public static final String SHARE_URL = "https://shop.ordertable.co.kr/share";
    public static final String DELIVERY_URL = "https://shop.ordertable.co.kr/delivery365";

    //ISP앱으로 부터 Call Back을 받기 위한 App스키마
    private final String APP_SCHEMA_URI = "syruptable://";
    //ISP앱에서 취소를 선택했을 때 받는 URI
    private final String APP_SCHEMA_CANCEL_URI = APP_SCHEMA_URI + "ISPCancel/";
    //ISP앱에서 인증을 성공했을 때 받는 URI
    private final String APP_SCHEMA_SUCCESS_URI = APP_SCHEMA_URI + "ISPSuccess/";

    public static final int FILECHOOSER_RESULTCODE = 1;
    public final static int FILECHOOSER_LOLLIPOP_REQ_CODE = 2;
    private final long FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;

    public ValueCallback<Uri> mUploadMessage = null;
    public ValueCallback<Uri[]> filePathCallbackLollipop;
    public ShopWebViewClient syrupWebViewClient;

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout_webview);

        mWebView = (WebView) findViewById(R.id.activity_main_webview);
//        mWebView.setWebViewClient(new ShopWebViewClient(this, mWebView));
//        mWebView.setWebChromeClient(new SyrupWebChromeClient(this, mWebView));
        mWebView.addJavascriptInterface(new AndroidBridge(), "MyApp");

        mWebView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() != KeyEvent.ACTION_DOWN)
                    return true;

                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (mWebView.canGoBack()) {
                        mWebView.goBack();
                    } else {
                        (MainActivityWebViewUp.this).onBackPressed();
                    }
                }
                return false;
            }
        });

        Uri uri = getIntent().getData();
        if (uri != null) {
            OResultPage(uri);
        } else {
            mWebView.loadUrl(HOTPLACE_URL);
        }
    }

    /**
     * ISP로부터 받은 URI에 따라 결제 최종 확인 페이지 혹은 결제 요청 전 페이지를 보여준다.  * @param resultUri  ISP로 부터 받은 URI
     */
    private void OResultPage(Uri resultUri) {
        String schemaUrl = resultUri.toString();
        String urlString = null;

        if (schemaUrl.startsWith(APP_SCHEMA_SUCCESS_URI)) { //ISP 인증을 성공한 경우
            urlString = schemaUrl.substring(APP_SCHEMA_SUCCESS_URI.length());
            mWebView.loadUrl(urlString);

        } else if (schemaUrl.startsWith(APP_SCHEMA_CANCEL_URI)) { //ISP앱에서 취소를 선택한 경우
            urlString = schemaUrl.substring(APP_SCHEMA_CANCEL_URI.length());
            mWebView.loadUrl(urlString);
        }
    }

    @SuppressLint("NewApi")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

        if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage)
                return;
            Uri result = intent == null || requestCode != RESULT_OK ? null : intent.getData();
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;
        } else if (requestCode == FILECHOOSER_LOLLIPOP_REQ_CODE) {
            if (filePathCallbackLollipop == null) return;
            filePathCallbackLollipop.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode, intent));
            filePathCallbackLollipop = null;

        } else if (requestCode == syrupWebViewClient.REQ_KFTC) {
            String strResultCode = intent.getExtras().getString("bankpay_code");
            String strResultValue = intent.getExtras().getString("bankpay_value");
            String strResultEpType = intent.getExtras().getString("hd_ep_type");

            String url = "https://pg1.payletter.com/PGSVC/SmartKFTC/KFTCCallBack.asp";
            String postData = "bankpay_code=" + strResultCode + "&bankpay_value=" + strResultValue + "&hd_ep_type=" + strResultEpType
                    + "&callbackparam1=" + syrupWebViewClient.mCallbackparam1 + "&callbackparam2=" + syrupWebViewClient.mCallbackparam2 + "&callbackparam3=" + syrupWebViewClient.mCallbackparam3 + "&launchmode=android_app";

            mWebView.clearHistory();
            mWebView.postUrl(url, Base64.encodeToString(postData.getBytes(), Base64.DEFAULT).getBytes());
        }
    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            long tempTime = System.currentTimeMillis();
            long intervalTime = tempTime - backPressedTime;

            if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime) {
                finish();
                android.os.Process.killProcess(android.os.Process.myPid());
            } else {
                backPressedTime = tempTime;
                Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            //noinspection deprecation
            CookieSyncManager.getInstance().startSync();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            CookieSyncManager.getInstance().stopSync();
        }
    }


}
