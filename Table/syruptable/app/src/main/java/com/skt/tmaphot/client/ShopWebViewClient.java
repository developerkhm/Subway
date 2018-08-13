package com.skt.tmaphot.client;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.HttpAuthHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.skt.tmaphot.BaseApplication;
import com.skt.tmaphot.LoginWebViewActivity;
import com.skt.tmaphot.MainActivity;
import com.skt.tmaphot.ObservableWebView;
import com.skt.tmaphot.common.CommonUtil;
import com.skt.tmaphot.pay.PaymentScheme;

import java.net.URISyntaxException;

/**
 * Created by home on 2018-05-26.
 */

public class ShopWebViewClient extends WebViewClient {
    private Activity activity;
    private ObservableWebView target;
    private final String LOG_TAG = "client";
    public String mCallbackparam1, mCallbackparam2, mCallbackparam3;
    private String mRequestUrl = null;
    public final int REQ_KFTC = 1;

    public ShopWebViewClient(Activity activity, ObservableWebView target) {
        this.activity = activity;
        this.target = target;
        init();
    }

    private void init() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //평균적으로 킷캣 이상에서는 하드웨어 가속이 성능이 좋음.
            target.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            //원격디버깅
            target.setWebContentsDebuggingEnabled(true);
        }
        //스크롤바 화면에 차지 및 표시
        target.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        target.setScrollbarFadingEnabled(false);

        WebSettings settings = target.getSettings();

        // Image set to width of device. (Must be done differently for API < 19 (kitkat))
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            //최신 SDK 에서는 Deprecated 이나 아직 성능상에서는 유용하다
            if (!settings.getLayoutAlgorithm().equals(WebSettings.LayoutAlgorithm.SINGLE_COLUMN))
                settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        } else {
            //웹뷰가 html 컨텐츠가 웹뷰보다 클 경우 스크린 크기에 맞게 조정되도록 한다.
            if (!settings.getLoadWithOverviewMode()) settings.setLoadWithOverviewMode(true);
            //웹뷰가 html의 viewport 메타 태그를 지원하게 한다.
            if (!settings.getUseWideViewPort()) settings.setUseWideViewPort(true);
        }

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O) {
            //기기에 따라서 동작할수도있는걸 확인
            settings.setRenderPriority(WebSettings.RenderPriority.HIGH);

            //부드러운 전환 또한 아직 동작
            settings.setEnableSmoothTransition(true);
        }

        //앱 캐쉬 사용
        //settings.setAppCacheEnabled(true); setAppCachePath
        //Cache를 사용하는 mode 설정
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);

        // 네트워크의 이미지의 리소스를 로드하지않음 기본값 FALSE
        settings.setBlockNetworkImage(false);
        // 웹뷰가 앱에 등록되어 있는 이미지 리소스를 자동으로 로드하도록 설정
        settings.setLoadsImagesAutomatically(true);

        // javascript를 실행할 수 있도록 설정
        settings.setJavaScriptEnabled(true);

        // 확대,축소 기능을 사용할 수 있도록 설정
        settings.setSupportZoom(false);
        // 웹뷰 텍스트 고정
        settings.setTextZoom(100);

        // 쿠키
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//            CookieSyncManager.createInstance(activity);
//        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            CookieSyncManager.createInstance(activity);  //hak 내가보기엔 startSync도 있음
        } else {
            // 웹뷰내 https 이미지 나오게 처리 ( 혼합 콘텐츠가 타사 쿠키를 차단할 때 생기는 오류 처리 )
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            cookieManager.setAcceptThirdPartyCookies(target, true);
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);   // 롤리팝 이후는 기본 설정되어 있음
        }

        //자바스크립트의 window.open 허용
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        // 여러개의 윈도우를 사용할 수 있도록 설정
        settings.setSupportMultipleWindows(false);
        // 웹뷰내의 localStorage 사용여부
        settings.setDomStorageEnabled(true);
        // 웹뷰내의 위치정보 사용여부
        settings.setGeolocationEnabled(true);
        //확대 축소 컨트롤
        settings.setBuiltInZoomControls(false);
    }

    // 로딩이 시작될 때
    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
    }

    // 리소스를 로드하는 중 여러번 호출
    @Override
    public void onLoadResource(WebView view, String url) {
        super.onLoadResource(view, url);
    }

    // 방문 내역을 히스토리에 업데이트 할 때
    @Override
    public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
        super.doUpdateVisitedHistory(view, url, isReload);
    }

    // 로딩이 완료됬을 때 한번 호출
    @Override
    public void onPageFinished(WebView view, String url) {
        Log.d(LOG_TAG, "onPageFinished : " + url);


        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                CookieSyncManager.getInstance().sync();
            } else {
                CookieManager.getInstance().flush();
            }

            // 여기서 WebView의 데이터를 가져오는 작업을 한다.
//            if (CommonUtil.getInstance().devicePhoneNumber == null) {
//
//                CommonUtil.getInstance().devicePhoneNumber = CommonUtil.getInstance().getPhoneNumber();
//                String script = "javascript:function afterLoad() {"
//                        + "document.getElementById('userPhone').value = '" + CommonUtil.getInstance().devicePhoneNumber + "';" + "};";

//            view.loadUrl(script);
//            }

            CookieManager cookieManager = CookieManager.getInstance();
            String cookies = cookieManager.getCookie(url);
            String[] temp1 = cookies.split(";");

            for(int i = 0 ; i < temp1.length ; i++){
                String[] temp2 = temp1[i].split("=");
                for(int j = 0 ; j < temp2.length ; j++){
                    Log.d("QQQ", "index : " +  j + "   " + temp2[j]);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 오류가 났을 경우, 오류는 복수할 수 없음
    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
        Log.d(LOG_TAG, "onReceivedError : " + errorCode + "failingUrl : " + failingUrl);
        switch (errorCode) {
            case ERROR_AUTHENTICATION:
                break;               // 서버에서 사용자 인증 실패
            case ERROR_BAD_URL:
                break;                           // 잘못된 URL
            case ERROR_CONNECT:
                break;                          // 서버로 연결 실패
            case ERROR_FAILED_SSL_HANDSHAKE:
                break;    // SSL handshake 수행 실패
            case ERROR_FILE:
                break;                                  // 일반 파일 오류
            case ERROR_FILE_NOT_FOUND:
                break;               // 파일을 찾을 수 없습니다
            case ERROR_HOST_LOOKUP:
                break;           // 서버 또는 프록시 호스트 이름 조회 실패
            case ERROR_IO:
                break;                              // 서버에서 읽거나 서버로 쓰기 실패
            case ERROR_PROXY_AUTHENTICATION:
                break;   // 프록시에서 사용자 인증 실패
            case ERROR_REDIRECT_LOOP:
                break;               // 너무 많은 리디렉션
            case ERROR_TIMEOUT:
                break;                          // 연결 시간 초과
            case ERROR_TOO_MANY_REQUESTS:
                break;     // 페이지 로드중 너무 많은 요청 발생
            case ERROR_UNKNOWN:
                break;                        // 일반 오류
            case ERROR_UNSUPPORTED_AUTH_SCHEME:
                break; // 지원되지 않는 인증 체계
            case ERROR_UNSUPPORTED_SCHEME:
                break;          // URI가 지원되지 않는 방식
        }
    }

    // http 인증 요청이 있는 경우, 기본 동작은 요청 취소
    @Override
    public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
        super.onReceivedHttpAuthRequest(view, handler, host, realm);
    }

    // 확대나 크기 등의 변화가 있는 경우
    @Override
    public void onScaleChanged(WebView view, float oldScale, float newScale) {
        super.onScaleChanged(view, oldScale, newScale);
    }

    // 잘못된 키 입력이 있는 경우
    @Override
    public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
        return super.shouldOverrideKeyEvent(view, event);
    }

    // 새로운 URL이 webview에 로드되려 할 경우 컨트롤을 대신할 기회를 줌
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Log.d(LOG_TAG, "shouldOverrideUrlLoading : " + url);
        //url="intent://TID=moncube20180531011257#Intent;scheme=ispmobile;action=android.intent.action.VIEW;category=android.intent.category.BROWSABLE;package=kvp.jjy.MispAndroid320;end";
        //Log.d("shouldOverrid",url);

        if (!url.startsWith("http://") && !url.startsWith("https://") && !url.startsWith("javascript:")) {

            //Log.d("shouldOverrid2",url);
            Intent intent = null;
            //인텐트 정합성 체크`
            try {

                intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                //Log.d(TAG, "intent.getScheme = " + intent.getScheme());
                //Log.d(TAG, "intent.getDataString = " + intent.getDataString());

            } catch (URISyntaxException ex) {

                //Log.e(TAG, "Bad URI " + url + ":" + ex.getMessage());
                return false;
            }

            try {

                Uri uri = Uri.parse(intent.getDataString());
                //Log.d(TAG, "Intent.ACTION_VIEW");
                activity.startActivity(new Intent(Intent.ACTION_VIEW, uri));
                return true;

            } catch (ActivityNotFoundException e) {
                //Log.e(TAG, "error ====> " + e.getMessage());
                //e.printStackTrace();
                //if (intent == null) return false;

                //if (handleNotFoundPaymentScheme(intent.getScheme())) return true;

                String packageName = intent.getPackage();

                //Log.e(TAG, "packageName ====> " +packageName);

                if (packageName != null) {
                    //Log.e(TAG, "packageName ====> " + packageName);
                    activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName)));
                    return true;
                }

                return false;
            }
        }
        return super.shouldOverrideUrlLoading(view, url);
    }

    /**
     * KFTC앱(인터넷뱅킹)에서 callbackparam1,2,3를 돌려주지 않아서
     * callbackparam1, 2, 3을 저장함.
     *
     * @param reqParam
     */
    private void getCallbackparam(String reqParam) {
        String[] arry = reqParam.split("&");

        for (int i = 0; i < arry.length; i++) {

            if (arry[i].startsWith("callbackparam1=")) {
                mCallbackparam1 = arry[i].substring("callbackparam1=".length());
                //Log.d(TAG, "mCallbackparam1 == " + mCallbackparam1);

            } else if (arry[i].startsWith("callbackparam2=")) {
                mCallbackparam2 = arry[i].substring("callbackparam2=".length());
                //Log.d(TAG, "mCallbackparam2 == " + mCallbackparam2);

            } else if (arry[i].startsWith("callbackparam3=")) {
                mCallbackparam3 = arry[i].substring("callbackparam3=".length());
                //Log.d(TAG, "mCallbackparam3 == " + mCallbackparam3);

            }
        }
    }

    protected boolean handleNotFoundPaymentScheme(String scheme) {
        //PG사에서 호출하는 url에 package정보가 없어 ActivityNotFoundException이 난 후 market 실행이 안되는 경우
        if (PaymentScheme.ISP.equalsIgnoreCase(scheme)) {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + PaymentScheme.PACKAGE_ISP)));
            return true;
        } else if (PaymentScheme.BANKPAY.equalsIgnoreCase(scheme)) {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + PaymentScheme.PACKAGE_BANKPAY)));
            return true;
        }
        return false;
    }


}
