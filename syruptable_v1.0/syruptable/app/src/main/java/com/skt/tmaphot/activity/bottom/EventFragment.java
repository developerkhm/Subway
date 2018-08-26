package com.skt.tmaphot.activity.bottom;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.skt.tmaphot.MainActivity;
import com.skt.tmaphot.ObservableWebView;
import com.skt.tmaphot.R;
import com.skt.tmaphot.fragment.BaseFragment;

public class EventFragment extends BaseFragment {

    private String url = "http://dev.ordertable.co.kr/event/";
    private ObservableWebView webView;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser)
    {
        Log.d("TestFg",getClass().getName() + "setUserVisibleHint : " + isVisibleToUser);

        if (isVisibleToUser)
        {
            //화면에 실제로 보일때
        }
        else
        {
            //preload 될때(전페이지에 있을때)
        }

        super.setUserVisibleHint(isVisibleToUser);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.d("TestFg",getClass().getName() + "onCreateView");
        View view = inflater.inflate(R.layout.fragment_bottom_event_layout, container, false);
        view.findViewById(R.id.toolbar).setVisibility(View.GONE);

        webView = (ObservableWebView) view.findViewById(R.id.base_webview);
        webView.setOnScrollChangeListener(new ObservableWebView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(WebView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                if (((MainActivity) getActivity()).navigation.getVisibility() == View.VISIBLE)
                    ((MainActivity) getActivity()).slideDown(((MainActivity) getActivity()).navigation);

                if (scrollY == 0 && oldScrollY >= 5)
                    ((MainActivity) getActivity()).slideUp(((MainActivity) getActivity()).navigation);
            }
        });

        initView();

        webView.loadUrl(url);

        return view;
    }

    public void initView() {

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            // 웹뷰내 https 이미지 나오게 처리 ( 혼합 콘텐츠가 타사 쿠키를 차단할 때 생기는 오류 처리 )
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        webView.getSettings().setJavaScriptEnabled(true);

        // 확대,축소 가능 여부
        webView.getSettings().setSupportZoom(false);
        webView.getSettings().setBuiltInZoomControls(false);
        // 확대,축소 아이콘 표시 유무
        webView.getSettings().setDisplayZoomControls(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //평균적으로 킷캣 이상에서는 하드웨어 가속이 성능이 좋음.
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            //원격디버깅
            webView.setWebContentsDebuggingEnabled(true);
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            //최신 SDK 에서는 Deprecated 이나 아직 성능상에서는 유용하다
            if (!webView.getSettings().getLayoutAlgorithm().equals(WebSettings.LayoutAlgorithm.SINGLE_COLUMN))
                webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        } else {
            //웹뷰가 html 컨텐츠가 웹뷰보다 클 경우 스크린 크기에 맞게 조정되도록 한다.
            if (!webView.getSettings().getLoadWithOverviewMode())
                webView.getSettings().setLoadWithOverviewMode(true);
            //웹뷰가 html의 viewport 메타 태그를 지원하게 한다.
            if (!webView.getSettings().getUseWideViewPort())
                webView.getSettings().setUseWideViewPort(true);
        }

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O) {
            //기기에 따라서 동작할수도있는걸 확인
            webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
            //부드러운 전환 또한 아직 동작
            webView.getSettings().setEnableSmoothTransition(true);
        }

        //캐쉬 사용
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);


        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }
        });

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d("FFFA", "shouldOverrideUrlLoading");
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.d("FFFA", "onPageStarted");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.d("FFFA", "onPageFinished");
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                Log.d("FFFA", description);
            }

        });

        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                
                if (event.getAction() != KeyEvent.ACTION_DOWN)
                    return true;


                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (webView.canGoBack()) {
                        webView.goBack();
                    } else {
                        ((MainActivity) getActivity()).onBackPressed();
                    }

                    return true;
                }

                return false;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        webView.reload();
        webView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
