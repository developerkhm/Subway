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
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.skt.tmaphot.R;
import com.skt.tmaphot.activity.BaseActivity;
import com.skt.tmaphot.activity.area.PopularityAreaFragment;
import com.skt.tmaphot.activity.area.SelectionAreaActivity;
import com.skt.tmaphot.activity.area.SelectionAreaFragment;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends BaseActivity {

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

    private List<Fragment> testList = new ArrayList<Fragment>();
    private TabLayout tabLayout;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d("BBB", "onTabSelected");
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Log.d("BBB", "onTabUnselected");
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Log.d("BBB", "onTabReselected");


//                if(tab.getPosition() == 0)
//                    ((PopularityAreaFragment)testList.get(0)).testABC();
            }
        });

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        EditText edit = (EditText)findViewById(R.id.search_edit);
        edit.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        edit.setInputType(InputType.TYPE_CLASS_TEXT);

        edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId) {
                    case EditorInfo.IME_ACTION_SEARCH:
                        // 검색 동작
                        Toast.makeText(SearchActivity.this, "엔터 엔터 엔터", Toast.LENGTH_SHORT).show();
                        mViewPager.setCurrentItem(2);
//                        tabLayout.setVisibility(View.GONE);
                        break;
                    default:
                        // 기본 엔터키 동작
                        return false;
                }
                return true;
            }
        });

        testList.add(RecommanSearchWordFragment.newInstance(1));
        testList.add(LatestSearchWordFragment.newInstance(1));
        testList.add(SearchResultFragment.newInstance(1));
    }

    public void testTab(){
        Log.d("AAA","testTab 호출");


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

            Log.d("AAA", "getItem 호출 position : " + position);

            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
//            return PopularityAreaFragment.newInstance(position + 1);
            return testList.get(position);

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

//        @Override
//        public int getItemPosition(Object object) {
////            if (object instanceof MyFragment) {
////                // Create a new method notifyUpdate() in your fragment
////                // it will get call when you invoke
////                // notifyDatasetChaged();
////                ((MyFragment) object).notifyUpdate();
////            }
//            //don't return POSITION_NONE, avoid fragment recreation.
//            return super.getItemPosition(PopularityAreaTEST.newInstance(1));
//        }
//    }
    }
}
