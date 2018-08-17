package com.skt.tmaphot;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.skt.tmaphot.activity.bottom.EventFragment;
import com.skt.tmaphot.activity.bottom.MainFragment;
import com.skt.tmaphot.activity.bottom.MyBlogFragment;
import com.skt.tmaphot.activity.bottom.RealReviewFragment;
import com.skt.tmaphot.activity.bottom.ShopFragment;
import com.skt.tmaphot.common.CommonUtil;
import com.skt.tmaphot.fragment.BaseFragment;
import com.skt.tmaphot.net.service.APIClient;
import com.skt.tmaphot.net.service.LoginInfo;

import java.util.Iterator;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {

    private FragmentManager fragmentManager;
    private DrawerLayout drawer;
    public BottomNavigationView navigation;
    public ImageButton fab;

    private LinearLayout drawer_menu;
    private ImageView profileImage;
    private TextView profileInfoModify, profileName, profileEmail, navi_close;
    private RelativeLayout logout_layout, login_layout;
    private Button login, logout;
    private MainFragment mainFragment;
    private RealReviewFragment realReviewFragment;
    private EventFragment eventFragment;
    private ShopFragment shopFragment;
    private MyBlogFragment myBlogFragment;

    private final long FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;

    private TextView navi_shop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);
        baceContext = this;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);

        mainFragment = new MainFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.fragment_content, mainFragment).commit();

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

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(0);

        drawer_menu = (LinearLayout) findViewById(R.id.main_navigation_menu);
        profileImage = (ImageView) findViewById(R.id.nav_profile_image);
        profileInfoModify = (TextView) findViewById(R.id.nav_profile_modify);
        profileName = (TextView) findViewById(R.id.nav_profile_name);
        profileEmail = (TextView) findViewById(R.id.nav_profile_email);
        navi_close = (TextView) findViewById(R.id.navi_close);
        logout_layout = (RelativeLayout) findViewById(R.id.main_navigation_logout);
        login_layout = (RelativeLayout) findViewById(R.id.main_navigation_login);
        login = (Button) findViewById(R.id.main_navigation_login_btn);
        logout = (Button) findViewById(R.id.main_navigation_logout_btn);
        navi_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawer.isDrawerOpen(GravityCompat.START))
                    drawer.closeDrawer(GravityCompat.START);
            }
        });

        fab = (ImageButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                    if (fragment.isVisible()) {
                        if (fragment instanceof MainFragment) {
                            mainFragment.nestedScrollView.scrollTo(0, 0);
                        }

                        if (fragment instanceof RealReviewFragment) {
                            realReviewFragment.setScrollTopFocus();
                        }
                    }
                }
            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            if (toolbar.getVisibility() == View.GONE) {
                toolbar.setVisibility(View.VISIBLE);
            }

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fab.setVisibility(View.VISIBLE);
                    mainFragment = new MainFragment();
                    fragmentManager.beginTransaction().replace(R.id.fragment_content, mainFragment).commit();
                    return true;
                case R.id.navigation_review:
                    fab.setVisibility(View.VISIBLE);
                    realReviewFragment = new RealReviewFragment();
                    fragmentManager.beginTransaction().replace(R.id.fragment_content, realReviewFragment).commit();
                    return true;
                case R.id.navigation_event:
                    fab.setVisibility(View.GONE);
                    eventFragment = new EventFragment();
                    fragmentManager.beginTransaction().replace(R.id.fragment_content, eventFragment).commit();
                    return true;
                case R.id.navigation_shop:
                    fab.setVisibility(View.GONE);
                    toolbar.setVisibility(View.GONE);
                    shopFragment = new ShopFragment();
                    fragmentManager.beginTransaction().replace(R.id.fragment_content, shopFragment).commit();
                    return true;
                case R.id.navigation_myblog:
                    fab.setVisibility(View.GONE);
                    myBlogFragment = new MyBlogFragment();
                    fragmentManager.beginTransaction().replace(R.id.fragment_content, myBlogFragment).commit();
                    return true;
            }
            return false;
        }
    };

    private void setNavigation() {

        if (!LoginInfo.getInstance().isLogin()) {
            login_layout.setVisibility(View.GONE);
            logout_layout.setVisibility(View.VISIBLE);

            profileName.setText("게스트");
            loadImage(baceContext, R.drawable.img_default_login_user, profileImage, true, BaseApplication.getInstance().DEFAULT_ORIGINAL);
            profileEmail.setText("로그인/회원가입을 해주세요!");
            profileInfoModify.setVisibility(View.GONE);

            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ActivityStart(new Intent(baceContext, LoginWebViewActivity.class), null);
                }
            });

        } else {  //on
            logout_layout.setVisibility(View.GONE);
            login_layout.setVisibility(View.VISIBLE);

            profileName.setText(LoginInfo.getInstance().getUserInfo().getItemList().getName());
            loadImage(baceContext, LoginInfo.getInstance().getUserInfo().getItemList().getImg(), profileImage, true, BaseApplication.getInstance().DEFAULT_ORIGINAL);
            profileEmail.setText(CommonUtil.getInstance().pack(LoginInfo.getInstance().getUserInfo().getItemList().getEmail()));
            profileInfoModify.setVisibility(View.VISIBLE);

            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    APIClient.getInstance().getClient("http://dev.ordertable.co.kr/member/").logout().enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            CommonUtil.getInstance().removePreferences(baceContext, "login", "userid");
                            BaseApplication.getInstance().ActivityStart(new Intent(baceContext, MainActivity.class), null);
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
                backPressedTime = tempTime;
                if (navigation.getVisibility() == View.GONE) {
                    slideUp(navigation);
                }
                Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    }
}
