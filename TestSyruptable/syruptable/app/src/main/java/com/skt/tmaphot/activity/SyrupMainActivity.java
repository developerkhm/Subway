package com.skt.tmaphot.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
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
import android.util.Base64;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.Toast;

import com.skt.tmaphot.R;
import com.skt.tmaphot.client.SyrupWebChromeClient2;
import com.skt.tmaphot.client.SyrupWebViewClient;
import com.skt.tmaphot.common.AndroidBridge;

public class SyrupMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */

    public static PlaceholderFragment placeholderFragment;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private TabLayout tabLayout;


    private final long FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syrup_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//      toolbar.setLogo(R.drawable.logo);
        ImageView logo = (ImageView) findViewById(R.id.toolbar_logo);
        logo.setImageResource(R.drawable.logo);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SyrupMainActivity.this, TestStoreInfoActivity.class));
//                overridePendingTransition(R.anim.anim_slide_out_right, R.anim.anim_slide_in_left);
//                finish();
            }
        });


        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
//        actionBar.setDisplayHomeAsUpEnabled(true);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.


        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText("13434TAB"));
        tabLayout.addTab(tabLayout.newTab().setText("TAB23453"));
        tabLayout.addTab(tabLayout.newTab().setText("TAB3f"));
        tabLayout.addTab(tabLayout.newTab().setText("TAB4"));
        tabLayout.addTab(tabLayout.newTab().setText("TAB4dfg"));
        tabLayout.addTab(tabLayout.newTab().setText("TAB4"));
        tabLayout.addTab(tabLayout.newTab().setText("fgTAB4d"));
        tabLayout.addTab(tabLayout.newTab().setText("4fTAB"));
//        tabLayout.addTab(tabLayout.newTab().setText("TAB4d"));




        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), getIntent(), tabLayout.getTabCount());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        tabLayout.post(new Runnable() {
            @Override
            public void run() {

//                LinearLayout layout = ((LinearLayout) ((LinearLayout) tabLayout.getChildAt(0)).getChildAt(0));
//                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) layout.getLayoutParams();
//
//                layout.setLayoutParams(layoutParams);
//
//                Log.d("TABS", String.valueOf(layoutParams.width));

                // 디바이스 가로길이 나누기 4하면 될듯


                if(tabLayout.getTabCount() <= 5)
                {
                    tabLayout.setTabMode(TabLayout.MODE_FIXED);
                }
                else
                {
                    tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
                }
            }
        });
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.syrup_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_cart) {
            placeholderFragment.setLoadUrl(PlaceholderFragment.CART_URL, mViewPager.getCurrentItem());
            return true;
        }

        if (id == R.id.action_user) {
            placeholderFragment.setLoadUrl(PlaceholderFragment.LOGIN_URL, mViewPager.getCurrentItem());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {

            case R.id.nav_home:
                tabLayout.getTabAt(0).select();
                break;
            case R.id.nav_login:

                break;
            case R.id.nav_mypage:

                break;
            case R.id.nav_cart:

                break;

            case R.id.nav_noti:

                break;
            case R.id.nav_board:

                break;
            case R.id.nav_review:

                break;

            default:
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */

//        public static final String HOTPLACE_URL = "https://shop.ordertable.co.kr/hotplace";
        public static final String HOTPLACE_URL = "http://dev.ordertable.co.kr/";
        public static final String SHOP_URL = "https://shop.ordertable.co.kr/";
        public static final String SHARE_URL = "https://shop.ordertable.co.kr/share";
        public static final String DELIVERY_URL = "https://shop.ordertable.co.kr/delivery365";
        public static final String[] URL_LIST = new String[]{
                "http://dev.ordertable.co.kr/",
                "https://shop.ordertable.co.kr/",
                "https://shop.ordertable.co.kr/share",
                "https://shop.ordertable.co.kr/delivery365"
        };


        public static final String CART_URL = "https://shop.ordertable.co.kr/mypage/cart";
        public static final String LOGIN_URL = "https://shop.ordertable.co.kr/member/login";

        //ISP앱으로 부터 Call Back을 받기 위한 App스키마
        private final String APP_SCHEMA_URI = "syruptable://";
        //ISP앱에서 취소를 선택했을 때 받는 URI
        private final String APP_SCHEMA_CANCEL_URI = APP_SCHEMA_URI + "ISPCancel/";
        //ISP앱에서 인증을 성공했을 때 받는 URI
        private final String APP_SCHEMA_SUCCESS_URI = APP_SCHEMA_URI + "ISPSuccess/";

        //file upload
        public final int FILECHOOSER_RESULTCODE = 1;
        public final int FILECHOOSER_LOLLIPOP_REQ_CODE = 2;
        public ValueCallback<Uri> mUploadMessage;
        public ValueCallback<Uri[]> filePathCallbackLollipop;

        private static final String ARG_SECTION_NUMBER = "section_number";
        public static WebView[] WebViewTab;
        public WebView mWebView;
        private Intent intent;
        private int mNumOfTabs;
        public SyrupWebViewClient syrupWebViewClient;
        public SyrupWebChromeClient2 syrupWebChromeClient;

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber, Intent intent, int NumOfTabs) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            fragment.setIntent(intent);
            fragment.setNumOfTabs(NumOfTabs);
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            WebViewTab = new WebView[mNumOfTabs];

            mWebView = (WebView) rootView.findViewById(R.id.main_webview);

            syrupWebViewClient = new SyrupWebViewClient(getActivity(), mWebView);
            syrupWebChromeClient = new SyrupWebChromeClient2(getActivity(), mWebView);
            mWebView.setWebViewClient(syrupWebViewClient);
            mWebView.setWebChromeClient(syrupWebChromeClient);
            mWebView.addJavascriptInterface(new AndroidBridge(), "MyApp");

            mWebView.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {

                    //This is the filter
                    if (event.getAction() != KeyEvent.ACTION_DOWN)
                        return true;

                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        if (mWebView.canGoBack()) {
                            mWebView.goBack();
                        } else {
                            ((SyrupMainActivity) getActivity()).onBackPressed();
                        }
//                        return false;
                    }
                    return false;
                }
            });


            Uri uri = intent.getData();     //main에서 받으 intent; ISP 인증 여부???
            if (uri != null) {
                OResultPage(uri);
            } else {

                WebViewTab[getArguments().getInt(ARG_SECTION_NUMBER)] = mWebView;
                mWebView.loadUrl(URL_LIST[getArguments().getInt(ARG_SECTION_NUMBER)]);
//                mWebView.loadUrl(SHOP_URL);
//                switch (getArguments().getInt(ARG_SECTION_NUMBER)) {



//                    case 1:
//                        mWebView_1 = mWebView;
//                        mWebView.loadUrl(HOTPLACE_URL);
//                        break;
//                    case 2:
//                        mWebView_2 = mWebView;
//                        mWebView.loadUrl(SHOP_URL);
//                        break;
//                    case 3:
//                        mWebView_3 = mWebView;
//                        mWebView.loadUrl(SHARE_URL);
//                        break;
//                    case 4:
//                        mWebView_4 = mWebView;
//                        mWebView.loadUrl(DELIVERY_URL);
//                        break;
//                    default:
//                        break;
//                }
            }
            return rootView;
        }

        public void setLoadUrl(String urlstr, int curruntPosition) {
//            WebView webview = null;

//            String ttt = "javascript:window.location.href=\"https://shop.ordertable.co.kr/mypage/cart\";";
//            String ttt1 = "window.location.href=\"https://shop.ordertable.co.kr/mypage/cart\";";
//            switch (curruntPosition) {
//
//                case 1:
//                    webview = mWebView_1;
//                    break;
//                case 2:
//                    webview = mWebView_2;
//                    break;
//                case 3:
//                    webview = mWebView_3;
//                    break;
//                case 4:
//                    webview = mWebView_4;
//                    break;
//                default:
//                    break;
//            }

            WebViewTab[curruntPosition].loadUrl(urlstr);

//            webview.loadUrl(urlstr);
        }

        public void setIntent(Intent intent) {
            this.intent = intent;
        }

        public void setNumOfTabs(int NumOfTabs) {
            this.mNumOfTabs = NumOfTabs;
        }

        /**
         * ISP로부터 받은 URI에 따라 결제 최종 확인 페이지 혹은 결제 요청 전 페이지를 보여준다.  * @param resultUri  ISP로 부터 받은 URI
         */
        public void OResultPage(Uri resultUri) {
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
        public void onFragmentResult(int requestCode, int resultCode, Intent intent) {

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
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private Intent intent;
        private int mNumOfTabs;

        public SectionsPagerAdapter(FragmentManager fm, Intent intent, int NumOfTabs) {
            super(fm);
            this.intent = intent;
            this.mNumOfTabs = NumOfTabs;

        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            placeholderFragment = PlaceholderFragment.newInstance(position, intent, mNumOfTabs);
            return placeholderFragment;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return mNumOfTabs;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return super.getPageTitle(position);
        }
    }

    @SuppressLint("NewApi")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        placeholderFragment.onFragmentResult(requestCode, resultCode, intent);
    }

    @Override
    protected void onPause() {
        super.onPause();

    }
}
