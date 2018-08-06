package com.skt.tmaphot.client;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.HttpAuthHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.skt.tmaphot.ObservableWebView;

public class BaseWebViewClient extends WebViewClient {
    private Context context;
    private ObservableWebView webView;

    public BaseWebViewClient(Context context, ObservableWebView webView) {
        this.context = context;
        this.webView = webView;
        init();
    }

    private void init() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //평균적으로 킷캣 이상에서는 하드웨어 가속이 성능이 좋음.
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            //원격디버깅
            webView.setWebContentsDebuggingEnabled(false);
        }
        //스크롤바 화면에 차지 및 표시
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(false);

        WebSettings settings = webView.getSettings();

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

        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        // 네트워크의 이미지의 리소스를 로드하지않음 기본값 FALSE
        settings.setBlockNetworkImage(false);
        // 웹뷰가 앱에 등록되어 있는 이미지 리소스를 자동으로 로드하도록 설정
        settings.setLoadsImagesAutomatically(true);

        // javascript를 실행할 수 있도록 설정
        settings.setJavaScriptEnabled(true);

        // 확대,축소 기능을 사용할 수 있도록 설정
        settings.setSupportZoom(true);
        // 웹뷰 텍스트 고정
        settings.setTextZoom(100);

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

    }

    // 오류가 났을 경우, 오류는 복수할 수 없음
    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
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

        return super.shouldOverrideUrlLoading(view, url);
    }
}
