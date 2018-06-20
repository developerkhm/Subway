package com.skt.tmaphot;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.GeolocationPermissions;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.net.URISyntaxException;
import java.util.List;

public class MainActivity1 extends AppCompatActivity {

    private WebView mWebView;
    private static final String ENTRY_URL = "https://shop.ordertable.co.kr/intro";

    private static final String HOTPLACE_URL = "https://shop.ordertable.co.kr/hotplace";
    private static final String SHOP_URL = "https://shop.ordertable.co.kr/";
    private static final String SHARE_URL = "https://shop.ordertable.co.kr/share";
    private static final String DELIVERY_URL = "https://shop.ordertable.co.kr/delivery365";

    public Double dLatitude ;  // 현재위치 경도
    public Double dLongitude ; // 현재위치 위도

    private static final int FILECHOOSER_RESULTCODE = 1;
    private final static int FILECHOOSER_LOLLIPOP_REQ_CODE = 2;

    private ValueCallback<Uri> mUploadMessage = null;
    private ValueCallback<Uri[]> filePathCallbackLollipop;
    //private String mCameraPhotoPath;

    private BackPressCloseHandler backPressCloseHandler;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWebView = (WebView) findViewById(R.id.activity_main_webview);
        WebSettings webSettings = mWebView.getSettings();

        // 웹뷰가 캐시를 사용하지 않도록 설정
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        // javascript를 실행할 수 있도록 설정
        webSettings.setJavaScriptEnabled(true);

        // 화면에 문서 전체가 보이게 설정
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);

        // 확대,축소 기능을 사용할 수 있도록 설정
        webSettings.setSupportZoom(false);

        // 마쉬멜로우 버젼 이상일 경우
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            // 웹뷰 텍스트 고정
            webSettings.setTextZoom(100);

            // 웹뷰내 https 이미지 나오게 처리 ( 혼합 콘텐츠가 타사 쿠키를 차단할 때 생기는 오류 처리 )
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

            // 쿠키
            CookieSyncManager.createInstance(this);

        }

        //자바스크립트의 window.open 허용
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        // 여러개의 윈도우를 사용할 수 있도록 설정
        webSettings.setSupportMultipleWindows(false);

        // 웹뷰내의 localStorage 사용여부
        webSettings.setDomStorageEnabled(true);

        // 웹뷰내의 위치정보 사용여부
        webSettings.setGeolocationEnabled(true);

        // Bridge 인스턴스 등록
        mWebView.addJavascriptInterface(new AndroidBridge(),"MyApp");

        //mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //meta태그의 viewport사용 가능
        //mWebView.getSettings().setUseWideViewPort(true);
        //mWebView.getSettings().setloadwithoverviewmode(true);


        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    CookieSyncManager.getInstance().sync();
                } else {
                    CookieManager.getInstance().flush();
                }

                // 여기서 WebView의 데이터를 가져오는 작업을 한다.
                if (url.equals(ENTRY_URL)) {
                    TelephonyManager mgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                    @SuppressLint("MissingPermission") String userPhone = mgr.getLine1Number();

                    String script = "javascript:function afterLoad() {"
                            + "document.getElementById('userPhone').value = '" + userPhone + "';"
                            + "};";
                    //+ "afterLoad();"
                    //+ "__init();";
                    view.loadUrl(script);
                }
            }

            @SuppressLint("MissingPermission")
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if(url == null) return false;

                if (url.startsWith("tel:")) {

                    //tel:01000000000
                    //Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));  // 전화다이얼창
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(url)); // 전화 바로걸기
                    startActivity(intent);
                    return true;

                } else if (url.startsWith("mailto:")) {

                    //mailto:ironnip@test.com
                    Intent i = new Intent(Intent.ACTION_SENDTO, Uri.parse(url));
                    startActivity(i);
                    return true;

                } else if (url.startsWith("intent:")) {

                    try {
                        Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                        Intent existPackage = getPackageManager().getLaunchIntentForPackage(intent.getPackage());
                        if (existPackage != null) {
                            startActivity(intent);
                        } else {
                            Intent marketIntent = new Intent(Intent.ACTION_VIEW);
                            marketIntent.setData(Uri.parse("market://details?id="+intent.getPackage()));
                            startActivity(marketIntent);
                        }
                        return true;
                    }catch (Exception e) {
                        e.printStackTrace();
                    }

                } else if (url.startsWith("market://")) {

                    try {
                        Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                        if (intent != null) {
                            startActivity(intent);
                        }
                        return true;
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }

                } else if (url.startsWith("vguardend://")) {
                    Log.i("#@#", "Skip : " + url);
                    return false;
                }

                if (view.canGoBack()) {
                    view.loadUrl(url);
                    return true;
                }

                return false;
            }

        });




        mWebView.setWebChromeClient(new WebChromeClient() {
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

            // For Android 3.0-
            @SuppressWarnings("unused")
            public void openFileChooser(ValueCallback<Uri> uploadMsg){
                mUploadMessage = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("*/*");
                startActivityForResult(Intent.createChooser(i,"File Chooser"),FILECHOOSER_RESULTCODE);
            }

            // For Android 3.0+
            @SuppressWarnings("unused")
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType){
                mUploadMessage = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("*/*");
                startActivityForResult(Intent.createChooser(i,"File Browser"), FILECHOOSER_RESULTCODE);
            }

            // For Android 4.1+
            @SuppressWarnings("unused")
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture){
                mUploadMessage = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("*/*");
                startActivityForResult(Intent.createChooser(i,"File Browser"), FILECHOOSER_RESULTCODE);
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
                startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_LOLLIPOP_REQ_CODE);

                return true;
            }

            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                super.onGeolocationPermissionsShowPrompt(origin, callback);
                callback.invoke(origin, true, false);
            }

        });



        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            // 롤리팝 이하의 버전일 경우
            //Toast.makeText(getApplicationContext(),"롤리팝", Toast.LENGTH_LONG).show();
            mWebView.loadUrl(ENTRY_URL);

        }else{
            // 롤리팝 이상의 버전일 경우, 마쉬멜로우 이상
            //Toast.makeText(getApplicationContext(),"마시멜로우", Toast.LENGTH_LONG).show();

            int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);

            if(permissionCheck != PackageManager.PERMISSION_GRANTED) {

                // 핸드폰에 전화번호 접근하는 권한이 없을 때, 권한이 필요한 이유를 설명합니다
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE)) {
                    // 권한 설명하는 영역
                    //Toast.makeText(getApplicationContext(), "설명전", Toast.LENGTH_LONG);
                    AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity1.this);

                    dialog.setTitle("단말기 전화번호에 접근권한이 필요합니다.")
                            .setMessage("이 앱을 사용하기 위해서는 단말기의 \"전화번호확인\" 권한이 필요합니다. 계속하시겠습니까?")
                            .setPositiveButton("네", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ActivityCompat.requestPermissions(MainActivity1.this, new String[]{Manifest.permission.READ_PHONE_STATE},1);
                                }
                            })

                            .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(MainActivity1.this, "접근권한을 허용하지 않았습니다.", Toast.LENGTH_SHORT).show();
                                    mWebView.loadUrl("https://shop.ordertable.co.kr/app_android/err_appChecker.html");
                                }
                            })
                            .create()
                            .show();

                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
                }

            }else{


                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    //Manifest.permission.ACCESS_FINE_LOCATION 접근 승낙 상태 일때
                    startLocationService(); // 현재위치 변수값 할당
                    mWebView.loadUrl(ENTRY_URL);
                } else{
                    //Manifest.permission.ACCESS_FINE_LOCATION 접근 거절 상태 일때
                    // 사용자에게 접근권한 설정을 요구하는 다이얼로그를 띄운다.
                    ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
                }

            }

            // 뒤로가기 핸들러
            backPressCloseHandler = new BackPressCloseHandler(this);

        }

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        if(requestCode == FILECHOOSER_RESULTCODE){
            if(null == mUploadMessage)
                return;
            Uri result = intent == null || requestCode != RESULT_OK ? null : intent.getData();
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;
        }else if(requestCode == FILECHOOSER_LOLLIPOP_REQ_CODE){
            if(filePathCallbackLollipop == null) return;
            filePathCallbackLollipop.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode,intent));
            filePathCallbackLollipop = null;
        }
    }

    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults){
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // 권한 허가
                    // 해당 권한을 사용해서 작업을 진행할 수 있습니다
                    //Toast.makeText(this,"권한허용함",Toast.LENGTH_LONG).show();
                    //mWebView.loadUrl(ENTRY_URL);

                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                        //Manifest.permission.ACCESS_FINE_LOCATION 접근 승낙 상태 일때
                        startLocationService(); // 현재위치 변수값 할당
                        mWebView.loadUrl(ENTRY_URL);
                    } else{
                        //Manifest.permission.ACCESS_FINE_LOCATION 접근 거절 상태 일때
                        // 사용자에게 접근권한 설정을 요구하는 다이얼로그를 띄운다.
                        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
                    }

                } else {
                    // 권한 거부
                    // 사용자가 해당권한을 거부했을때 해주어야 할 동작을 수행합니다
                    //Toast.makeText(this,"권한승인안함",Toast.LENGTH_LONG).show();
                    mWebView.loadUrl("https://a225.a-gen.co.kr/app_android/err_appChecker.html");
                }
                return;
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        CookieSyncManager.getInstance().startSync();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            CookieSyncManager.getInstance().stopSync();
        }
    }


/*
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(mWebView.canGoBack()){
                mWebView.goBack();
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
*/

    // 디바이스 기기의 back 버튼 인식
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mWebView.getUrl().equals(HOTPLACE_URL) || mWebView.getUrl().equals(SHOP_URL) || mWebView.getUrl().equals(SHARE_URL) || mWebView.getUrl().equals(DELIVERY_URL)) {
            backPressCloseHandler.onBackPressed();
            return false;
        }
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 위치 정보 확인을 위해 정의한 메소드
     */
    private void startLocationService() {
        // 위치 관리자 객체 참조
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // 위치 정보를 받을 리스너 생성
        GPSListener gpsListener = new GPSListener();
        long minTime = 0;
        float minDistance = 0;

        try {
            // GPS를 이용한 위치 요청
            manager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    minTime,
                    minDistance,
                    gpsListener);

            // 네트워크를 이용한 위치 요청
            manager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    minTime,
                    minDistance,
                    gpsListener);

            /** 현재 사용가능한 위치 정보 장치 검색*/


            //위치정보 하드웨어 목록
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_FINE); //정확도(ACCURACY_COARSE :네트웍, ACCURACY_FINE :GPS)
            criteria.setAltitudeRequired(false);//고도
            criteria.setBearingRequired(false);	//방위, 방향
            criteria.setCostAllowed(true);	//과금여부
            criteria.setPowerRequirement(Criteria.POWER_HIGH);	//전력사용:GPS는 전력사용량이 많다.
            //최적의 하드웨어 이름을 리턴받는다.
            String provider = manager.getBestProvider(criteria, true);
            // 최적의 값이 없거나, 해당 장치가 사용가능한 상태가 아니라면,
            //모든 장치 리스트에서 사용가능한 항목 얻기
            if(provider == null || !manager.isProviderEnabled(provider)){
                // 모든 장치 목록
                List<String> list = manager.getAllProviders();

                for(int i = 0; i < list.size(); i++){
                    //장치 이름 하나 얻기
                    String temp = list.get(i);

                    //사용 가능 여부 검사
                    if(manager.isProviderEnabled(temp)){
                        provider = temp;
                        break;
                    }
                }
            }// (end if)위치정보 검색 끝

            /**마지막으로  조회했던 위치 얻기*/
            // 위치 확인이 안되는 경우에도 최근에 확인된 위치 정보 먼저 확인
            Location lastLocation = manager.getLastKnownLocation(provider);
            if (lastLocation != null) {
                Double latitude = lastLocation.getLatitude();
                Double longitude = lastLocation.getLongitude();

                dLatitude = lastLocation.getLatitude();
                dLongitude = lastLocation.getLongitude();

                //textView.setText("내 위치 : " + latitude + ", " + longitude);
                //Toast.makeText(getApplicationContext(), "Last Known Location : " + "Latitude : " + latitude + "\nLongitude:" + longitude, Toast.LENGTH_LONG).show();
            }
        } catch(SecurityException ex) {
            ex.printStackTrace();
        }

    }


    /**
     * 리스너 클래스 정의
     */
    private class GPSListener implements LocationListener {
        /**
         * 위치 정보가 확인될 때 자동 호출되는 메소드
         */
        public void onLocationChanged(Location location) {
            Double latitude = location.getLatitude();
            Double longitude = location.getLongitude();

            dLatitude = latitude;
            dLongitude = longitude;

            //String msg = "Latitude : "+ latitude + "\nLongitude:"+ longitude;
            //Log.i("GPSListener", msg);

            //textView.setText("내 위치 : " + latitude + ", " + longitude);
            //Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

    }

    private class AndroidBridge {

        @JavascriptInterface
        public double getLatitudeValue(){
            return dLatitude ;
        }

        @JavascriptInterface
        public double getLongitudeValue(){
            return dLongitude ;
        }

        @JavascriptInterface
        public String getPhoneNumber(){
            TelephonyManager mgr2 = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            @SuppressLint("MissingPermission") String userPhone2 = mgr2.getLine1Number();
            return userPhone2 ;
        }
    }


    public class BackPressCloseHandler {
        private long backKeyPressedTime = 0;
        private Toast toast;

        private Activity activity;

        public BackPressCloseHandler(Activity context) {
            this.activity = context;
        }

        public void onBackPressed() {
            if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
                backKeyPressedTime = System.currentTimeMillis();
                showGuide();
                return ;
            }

            if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
                toast.cancel();

                Intent t = new Intent(activity, MainActivity1.class);
                activity.startActivity(t);

                activity.moveTaskToBack(true);
                activity.finish();
                android.os.Process.killProcess(android.os.Process.myPid());
            }

        }

        public void showGuide() {
            toast = Toast.makeText(activity, "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
            toast.show();
        }

    }

}
