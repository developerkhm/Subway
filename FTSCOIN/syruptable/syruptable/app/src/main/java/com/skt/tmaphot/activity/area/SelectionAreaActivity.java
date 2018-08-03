package com.skt.tmaphot.activity.area;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.skt.tmaphot.R;
import com.skt.tmaphot.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class SelectionAreaActivity extends BaseActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;


    private ViewPager mViewPager;

    private List<Fragment> testList = new ArrayList<Fragment>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectionarea_layout);
        baceContext = this;
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.viewpager_container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

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



        testList.add(PopularityAreaFragment.newInstance(1));
        testList.add(SelectionAreaFragment.newInstance(1));
//        testList.add(SelectionAreaFragment.newInstance(1));

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
            return 2;
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
