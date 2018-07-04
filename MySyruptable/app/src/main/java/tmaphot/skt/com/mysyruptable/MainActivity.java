package tmaphot.skt.com.mysyruptable;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;

import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;


    public static int RENEW_GPS = 1;
    public static int SEND_PRINT = 2;


    public static final String INTRO_URL = "https://shop.ordertable.co.kr/intro";
    public static final String ENTRY_URL = "https://shop.ordertable.co.kr/hotplace";
    public static final String SHOP_URL = "https://shop.ordertable.co.kr/";
    public static final String SHARE_URL = "https://shop.ordertable.co.kr/share";
    public static final String DELIVERY_URL = "https://shop.ordertable.co.kr/delivery365";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
//        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
//        actionBar.setDisplayHomeAsUpEnabled(true);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
//
//        if (id== R.id.action_cart    ){
//
//            return true;
//        }
//
//        if(){
//
//        }

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        public static final String ARG_SECTION_NUMBER = "section_number";


        public SyrupWebViewClient syrupWebViewClient;
        public AndroidBridge androidBridge;
        public WebView mWebView;


        public PlaceholderFragment() {
        }


        public void refresh(int i) {
//            switch (i) {
//                case 1:
//                    mWebView.loadUrl(ENTRY_URL);
//                    break;
//                case 2:
//                    mWebView.loadUrl(ENTRY_URL);
//                    break;
//                case 3:
//                    mWebView.loadUrl(ENTRY_URL);
//                    break;
//                default:
//                    break;
//            }
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);


            //======================================================================================//

            mWebView = (WebView) rootView.findViewById(R.id.activity_main_webview);
            WebSettings webSettings = mWebView.getSettings();

            // 웹뷰가 캐시를 사용하지 않도록 설정
//            webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

            // javascript를 실행할 수 있도록 설정
            webSettings.setJavaScriptEnabled(true);

            // 화면에 문서 전체가 보이게 설정
            webSettings.setUseWideViewPort(true);
            webSettings.setLoadWithOverviewMode(true);

            // 확대,축소 기능을 사용할 수 있도록 설정
            webSettings.setSupportZoom(false);

            // 웹뷰 텍스트 고정
            webSettings.setTextZoom(100);
            // 쿠키
            CookieSyncManager.createInstance(getActivity());
            // 마쉬멜로우 버젼 이상일 경우
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                // 웹뷰내 https 이미지 나오게 처리 ( 혼합 콘텐츠가 타사 쿠키를 차단할 때 생기는 오류 처리 )
                webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
                CookieManager cookieManager = CookieManager.getInstance();
                cookieManager.setAcceptCookie(true);
                cookieManager.setAcceptThirdPartyCookies(mWebView, true);
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                WebView.setWebContentsDebuggingEnabled(true);
            }
            //자바스크립트의 window.open 허용
            webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

            // 여러개의 윈도우를 사용할 수 있도록 설정
            webSettings.setSupportMultipleWindows(false);

            // 웹뷰내의 localStorage 사용여부
            webSettings.setDomStorageEnabled(true);

            // 웹뷰내의 위치정보 사용여부
            webSettings.setGeolocationEnabled(true);

            androidBridge = new AndroidBridge(getActivity());
            // Bridge 인스턴스 등록
            mWebView.addJavascriptInterface(androidBridge, "MyApp");

            //==============================================//
            webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
            webSettings.setEnableSmoothTransition(true);
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            webSettings.setAppCacheEnabled(true);



            webSettings.setBuiltInZoomControls(false);

            mWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
            mWebView.setScrollbarFadingEnabled(true);

            mWebView.getSettings().setUseWideViewPort(true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                mWebView.getSettings().setLoadWithOverviewMode(true);
                mWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            } else {
                mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
            }

            //======================================================================================//


            syrupWebViewClient = new SyrupWebViewClient(getActivity(), mWebView);
            mWebView.setWebViewClient(syrupWebViewClient);

            mWebView.setWebChromeClient(new SyrupWebChromeClient(getActivity(), mWebView));

            mWebView.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {

                    Log.d("back","call KeyEvent");
                    //This is the filter
                    if (event.getAction()!=KeyEvent.ACTION_DOWN)
                        return true;

                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        if (mWebView.canGoBack()) {
                            mWebView.goBack();
                        } else {
                            ((MainActivity)getActivity()).onBackPressed();
                        }
//                        return false;
                    }
                    return false;
                }
            });


            switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
                case 1:
                    mWebView.loadUrl(ENTRY_URL);
                    break;
                case 2:
                    mWebView.loadUrl(SHOP_URL);
                    break;
                case 3:
                    mWebView.loadUrl(SHARE_URL);
                    break;
                case 4:
                    mWebView.loadUrl(DELIVERY_URL);
                    break;
            }


            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 4;
        }
    }


//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        Log.d("back","init keycode");
//        try {
//            if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
//                Log.d("back","init keycode 111111");
//                mWebView.goBack();
//                return false;
//            }else{
//                Log.d("back","init keycode 222222");
//            }
//        }catch(Exception ex){
//
//        }
//        return super.onKeyDown(keyCode, event);
//    }

    @Override
    public void onBackPressed() {
        Log.d("back","call onBackPressed");


        finish();

//
//        if (true) {
//            mWebView.goBack();
//            Log.d("back","init keycode 111111");
//
//        } else {
//
//            Log.d("back","init keycode 222222");
//            super.onBackPressed();

//        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}


