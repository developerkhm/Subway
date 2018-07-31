package com.skt.tmaphot.activity.search;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.skt.tmaphot.R;
import com.skt.tmaphot.BaseActivity;
import com.skt.tmaphot.common.CommonUtil;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends BaseActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private List<Fragment> searchFragmentList = new ArrayList<Fragment>();
    private TabLayout tabLayout;
    private EditText searchEdit;
    private final String KEYWORD = "keyword";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        searchEdit = (EditText) findViewById(R.id.search_edit);
        searchEdit.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        searchEdit.setInputType(InputType.TYPE_CLASS_TEXT);
        searchEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId) {
                    case EditorInfo.IME_ACTION_SEARCH:
                        // 검색 동작
                        searchAction(searchEdit.getText().toString());
                        break;
                    default:
                        // 기본 엔터키 동작
                        return false;
                }
                return true;
            }
        });

        searchFragmentList.add(RecommanSearchWordFragment.newInstance(1));
        searchFragmentList.add(LatestSearchWordFragment.newInstance(1));
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return searchFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

    private void searchAction(String keyword){
        if(searchFragmentList.get(1) != null){
            ((LatestSearchWordFragment)searchFragmentList.get(1)).saveSearchKeyword(keyword);
            CommonUtil.getInstance().savePreferencesString(this, keyword, CommonUtil.getInstance().getCurrenTime(), keyword);
        }
    }

    public void testTab() {
        Log.d("AAA", "testTab 호출");

//        Fragment productDetailFragment = PopularityAreaTEST.newInstance(1);
//
//
//        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
//        transaction.addToBackStack(null);
//        transaction.replace(R.id.products_list_linear, productDetailFragment).commit();

//        testList.set(0, new PopularityAreaTEST());
//
//        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
//        mViewPager.setAdapter(mSectionsPagerAdapter);
//
////        mViewPager.setCurrentItem(0);
//        mSectionsPagerAdapter.notifyDataSetChanged();
////        mViewPager.invalidate();

    }
}
