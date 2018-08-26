package com.skt.tmaphot;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.skt.tmaphot.activity.bottom.EventFragment;
import com.skt.tmaphot.activity.bottom.MainFragment;
import com.skt.tmaphot.activity.bottom.MyBlogFragment;
import com.skt.tmaphot.activity.bottom.RealReviewFragment;
import com.skt.tmaphot.activity.bottom.ShopFragment;
import com.skt.tmaphot.common.CommonUtil;
import com.skt.tmaphot.navi.NaviMenuItem;
import com.skt.tmaphot.navi.NaviMenuRecyclerViewAdapter;
import com.skt.tmaphot.net.service.APIClient;
import com.skt.tmaphot.net.service.LoginInfo;
import com.skt.tmaphot.net.service.test.MainData;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private DrawerLayout drawer;
    public BottomNavigationView navigation;
    public ImageButton fab;

    private ImageView profileImage, navi_close;
    private TextView profileInfoModify, profileName, profileEmail;
    private Button login;

    private MainFragment mainFragment = new MainFragment();
    private RealReviewFragment realReviewFragment = new RealReviewFragment();
    private EventFragment eventFragment = new EventFragment();
    private ShopFragment shopFragment = new ShopFragment();
    private MyBlogFragment myBlogFragment = new MyBlogFragment();
    private ViewPager viewPager;

    private final long FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;

    //navi
    private RecyclerView naviMenuRecyclerView;
    private NaviMenuRecyclerViewAdapter naviMenuRecyclerViewAdapter;
    List<NaviMenuItem> naviMenuItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);
        baceContext = this;

        MainData mainData = getIntent().getParcelableExtra(LoadingActivity.MAINACTIVITY);
        Bundle bundle = new Bundle();
        bundle.putParcelable(LoadingActivity.MAINACTIVITY, mainData);
        mainFragment.setArguments(bundle);



        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);

//        realReviewFragment =  new RealReviewFragment();
//        eventFragment = new EventFragment();
//        myBlogFragment = new MyBlogFragment();

//        mainFragment = new MainFragment();
//
//        fragmentManager = getSupportFragmentManager();
//        fragmentTransaction = fragmentManager.beginTransaction();
//
//        fragmentTransaction.add(R.id.fragment_content, mainFragment,"main").commit();

//        replaceFragment(new MainFragment(), null,true, true);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            public void onDrawerOpened(View drawerView) {
                setNavigation();
                super.onDrawerOpened(drawerView);
            }
        };

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        viewPager = (ViewPager) findViewById(R.id.main_viewpager);
        viewPager.setOffscreenPageLimit(4);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {

                switch (i) {
                    case 0:    //메인
                        toolbar.setVisibility(View.VISIBLE);
                        fab.setVisibility(View.VISIBLE);
                        break;
                    case 1:    //리얼후기
                        Log.d("tt01", "리얼후기");
                        toolbar.setVisibility(View.VISIBLE);
                        fab.setVisibility(View.VISIBLE);
                        break;
                    case 2:    //이벤트
                        Log.d("tt01", "이벤트");
                        fab.setVisibility(View.GONE);
                        toolbar.setVisibility(View.GONE);
                        break;
                    case 3:    //샵
                        fab.setVisibility(View.GONE);
                        toolbar.setVisibility(View.GONE);
                        break;
                    case 4:    //내블로그
                        toolbar.setVisibility(View.VISIBLE);
                        fab.setVisibility(View.GONE);
                        break;
                }

                navigation.getMenu().getItem(i).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return mainFragment;
                    case 1:
                        return realReviewFragment;
                    case 2:
                        return eventFragment;
                    case 3:
                        return shopFragment;
                    case 4:
                        return myBlogFragment;
                }
                return null;
            }

            @Override
            public int getCount() {
                return 5;
            }
        });

        naviMenuRecyclerView = (RecyclerView) findViewById(R.id.navi_menu_recyclerview);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        profileImage = (ImageView) findViewById(R.id.nav_profile_image);
        profileInfoModify = (TextView) findViewById(R.id.nav_profile_modify);
        profileName = (TextView) findViewById(R.id.nav_profile_name);
        profileEmail = (TextView) findViewById(R.id.nav_profile_email);
        navi_close = (ImageView) findViewById(R.id.navi_close);
        login = (Button) findViewById(R.id.main_navigation_login_btn);
        navi_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawer.isDrawerOpen(GravityCompat.START))
                    drawer.closeDrawer(GravityCompat.START);
            }
        });

        naviMenuItems = new ArrayList<>();
        naviMenuItems.add(new NaviMenuItem("noti", "공지사항"));
        naviMenuItems.add(new NaviMenuItem("shop", "SHOP"));
        naviMenuItems.add(new NaviMenuItem("dining", "소셜다이닝"));
        naviMenuItems.add(new NaviMenuItem("365", "배달365"));

        naviMenuRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        naviMenuRecyclerView.setHasFixedSize(true);
        naviMenuRecyclerViewAdapter = new NaviMenuRecyclerViewAdapter(naviMenuItems);
        naviMenuRecyclerView.setAdapter(naviMenuRecyclerViewAdapter);
        naviMenuRecyclerViewAdapter.setOnClickReceivedEvent(new NaviMenuRecyclerViewAdapter.ClickEventListener() {
            @Override
            public void onReceivedEvent(String menuId) {
                Intent intent = new Intent(baceContext, WebViewActivity.class);

                if (menuId.equals("noti")) {
                    intent.putExtra("menuId", "noti");
                }
                if (menuId.equals("shop")) {
                    intent.putExtra("menuId", "shop");
                }
                if (menuId.equals("dining")) {
                    intent.putExtra("menuId", "dining");
                }
                if (menuId.equals("365")) {
                    intent.putExtra("menuId", "365");
                }
                if (menuId.equals("내블로그")) {
                    intent.putExtra("menuId", "내블로그");
                }
                if (menuId.equals("1:1문의")) {
                    intent.putExtra("menuId", "1:1문의");
                }

                ActivityStart(intent, null);
            }
        });

        fab = (ImageButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                    if (fragment.isVisible()) {
                        if (fragment instanceof MainFragment) {
//                            mainFragment.nestedScrollView.scrollTo(0, 0);
                            ((MainFragment) fragment).nestedScrollView.scrollTo(0, 0);
                        }

                        if (fragment instanceof RealReviewFragment) {
//                            realReviewFragment.setScrollTopFocus();
                            ((RealReviewFragment) fragment).setScrollTopFocus();
                        }
                    }
                }
            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

//            if (toolbar.getVisibility() == View.GONE) {
//                toolbar.setVisibility(View.VISIBLE);
//            }
//
//            Log.d("ddd", item.getOrder() + " ::");

            viewPager.setCurrentItem(item.getOrder());

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    viewPager.setCurrentItem(0);
                    break;
                case R.id.navigation_review:
                    viewPager.setCurrentItem(1);
                    break;
                case R.id.navigation_event:
                    viewPager.setCurrentItem(2);
                    break;
                case R.id.navigation_shop:
                    viewPager.setCurrentItem(3);
                    break;
                case R.id.navigation_myblog:
                    viewPager.setCurrentItem(4);
                    break;
            }


            /*
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fab.setVisibility(View.VISIBLE);
//                    if(mainFragment == null)
//                        mainFragment = new MainFragment();
//
//                    Fragment fragment = fragmentManager.findFragmentByTag("main");
//
//                    if (fragment == null) {
//                        fragmentTransaction.add(R.id.fragment_content, new MainFragment(), "main");
//                    }
//                    else { // re-use the old fragment
//                        fragmentTransaction.replace(R.id.fragment_content, fragment, "main");
//                    }
//
//                    fragmentManager.beginTransaction()
//                            .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
//                            .replace(R.id.fragment_content, mainFragment).addToBackStack(null).commit();

                    replaceFragment(new MainFragment(), null,true, true);
                    return true;
                case R.id.navigation_review:
                    fab.setVisibility(View.VISIBLE);
//                    if(realReviewFragment == null)
//                        realReviewFragment = new RealReviewFragment();
//
//                    fragmentManager.beginTransaction()
//                            .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
//                            .replace(R.id.fragment_content, realReviewFragment).addToBackStack(null).commit();
                    replaceFragment(new RealReviewFragment(), null,true, true);
                    return true;
                case R.id.navigation_event:
                    fab.setVisibility(View.GONE);
//                    if(eventFragment == null)
//                        eventFragment = new EventFragment();
//
//                    fragmentManager.beginTransaction()
//                            .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
//                            .replace(R.id.fragment_content, eventFragment).addToBackStack(null).commit();
                    replaceFragment(new EventFragment(), null,true, true);
                    return true;
                case R.id.navigation_shop:
                    fab.setVisibility(View.GONE);
                    toolbar.setVisibility(View.GONE);
//                    if(shopFragment == null)
//                        shopFragment = new ShopFragment();
//
//                    fragmentManager.beginTransaction()
//                            .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
//                            .replace(R.id.fragment_content, shopFragment).addToBackStack(null).commit();
                    replaceFragment(new ShopFragment(), null,true, true);
                    return true;
                case R.id.navigation_myblog:
                    fab.setVisibility(View.GONE);
//                    if(myBlogFragment == null)
//                        myBlogFragment = new MyBlogFragment();
//
//                    fragmentManager.beginTransaction()
//                            .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
//                            .replace(R.id.fragment_content, myBlogFragment).addToBackStack(null).commit();
                    replaceFragment(new MyBlogFragment(), null,true, true);
                    return true;
            }
            */
            return true;
        }
    };

//    public void replaceFragment(Fragment fragment, @Nullable Bundle bundle, boolean popBackStack, boolean findInStack) {
//        FragmentManager fm = getSupportFragmentManager();
//        FragmentTransaction ft = fm.beginTransaction();
//        String tag = fragment.getClass().getName();
//        Fragment parentFragment;
//        if (findInStack && fm.findFragmentByTag(tag) != null) {
//            parentFragment = fm.findFragmentByTag(tag);
//        } else {
//            parentFragment = fragment;
//        }
//        // if user passes the @bundle in not null, then can be added to the fragment
//        if (bundle != null)
//            parentFragment.setArguments(bundle);
//        else parentFragment.setArguments(null);
//        // this is for the very first fragment not to be added into the back stack.
//        if (popBackStack) {
//            fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//        } else {
//            ft.addToBackStack(parentFragment.getClass().getName() + "");
//        }
//        ft.replace(R.id.fragment_content, parentFragment, tag);
//        ft.commit();
//        fm.executePendingTransactions();
//    }

    private void setNavigation() {

        if (!LoginInfo.getInstance().isLogin()) {   //logout state

            profileName.setText("게스트");
            loadImage(baceContext, R.drawable.img_default_login_user, profileImage, true, BaseApplication.getInstance().DEFAULT_ORIGINAL);
            profileEmail.setText("로그인/회원가입을 해주세요!");
            profileInfoModify.setVisibility(View.INVISIBLE);
            login.setText("로그인/회원가입");

            if (naviMenuItems.size() > 4) {
                naviMenuItems.remove(4);
                naviMenuItems.remove(5);
                naviMenuRecyclerViewAdapter.notifyDataSetChanged();
            }

            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ActivityStart(new Intent(baceContext, LoginWebViewActivity.class), null);
                }
            });
        } else {    //login state
            profileName.setText(LoginInfo.getInstance().getUserInfo().getItemList().getName());
            if (LoginInfo.getInstance().getUserInfo().getItemList().getImg() != null)
                loadImage(baceContext, LoginInfo.getInstance().getUserInfo().getItemList().getImg(), profileImage, true, BaseApplication.getInstance().DEFAULT_ORIGINAL);

            profileEmail.setText(CommonUtil.getInstance().pack(LoginInfo.getInstance().getUserInfo().getItemList().getEmail()));
            profileInfoModify.setVisibility(View.VISIBLE);

            naviMenuItems.add(new NaviMenuItem("myblog", "내블로그"));
            naviMenuItems.add(new NaviMenuItem("inquiry", "1:1문의"));
            naviMenuRecyclerViewAdapter.notifyDataSetChanged();

            login.setText("로그아웃");
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Call<ResponseBody> call = APIClient.getInstance().getClient("").logout(LoginInfo.getInstance().getUserId());
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            LoginInfo.getInstance().setUserId("");
                            ActivityStart(new Intent(baceContext, MainActivity.class), null);
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });

                }
            });
        }
    }

    public void slideDown(final View view) {
        view.animate()
                .translationY(view.getHeight())
                .alpha(0.f)
                .setDuration(300)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        // superfluous restoration
                        view.setVisibility(View.GONE);
                        view.setAlpha(1.f);
                        view.setTranslationY(0.f);
                    }
                });
    }

    public void slideUp(final View view) {
        view.setVisibility(View.VISIBLE);
        view.setAlpha(0.f);

        if (view.getHeight() > 0) {
            slideUpNow(view);
        } else {
            // wait till height is measured
            view.post(new Runnable() {
                @Override
                public void run() {
                    slideUpNow(view);
                }
            });
        }
    }

    private void slideUpNow(final View view) {
        view.setTranslationY(view.getHeight());
        view.animate()
                .translationY(0)
                .alpha(1.f)
                .setDuration(500)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        view.setVisibility(View.VISIBLE);
                        view.setAlpha(1.f);
                    }
                });
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            long tempTime = System.currentTimeMillis();
            long intervalTime = tempTime - backPressedTime;

            if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime) {
                finish();
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(-1);
            } else {
                if (navigation.getSelectedItemId() != R.id.navigation_home) {
                    navigation.setSelectedItemId(R.id.navigation_home);

                    if (navigation.getVisibility() == View.GONE) {
                        slideUp(navigation);
                    }
                } else {
                    backPressedTime = tempTime;

                    CommonUtil.getInstance().custom_toast(baceContext, "\'뒤로\' 한번 더 누르면 종료됩니다.");
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    }
}
