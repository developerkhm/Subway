package com.skt.tmaphot.activity.bottom;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;

import com.skt.tmaphot.MainActivity;
import com.skt.tmaphot.ObservableWebView;
import com.skt.tmaphot.R;
import com.skt.tmaphot.client.ShopWebChromeClient;
import com.skt.tmaphot.client.ShopWebViewClient;
import com.skt.tmaphot.fragment.BaseFragment;

public class ShopFragment extends BaseFragment {

//    private String url = "https://shop.ordertable.co.kr/";
//    private String url = "http://dev.ordertable.co.kr/member/join";
    private String url = "http://dev.ordertable.co.kr/";
    private ObservableWebView webView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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

        webView.setWebViewClient(new ShopWebViewClient(getActivity(), webView));
        webView.setWebChromeClient(new ShopWebChromeClient(getActivity(), webView));

        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                //This is the filter
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

        webView.loadUrl(url);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
}
