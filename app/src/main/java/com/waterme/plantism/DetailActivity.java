package com.waterme.plantism;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TableLayout;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = "Detail Activity";
    private static final String PLANTID = "PLANTID";
    private static final String CATEGORY = "CATEGORY";
    private static final String UID = "UID";

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private String uid;
    private String plantId;
    private String plantCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // get the current username
        SharedPreferences sharedPre = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        uid = sharedPre.getString("uid","");


        // set up page
        mViewPager = (ViewPager) findViewById(R.id.vp_detail);
        setUpViewPager(mViewPager);
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPager);



    }

    private void setUpViewPager(ViewPager viewPager) {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            plantId = bundle.getString(PLANTID);
            plantCategory = bundle.getString(CATEGORY);
        } else {
            Log.d(TAG, "new plant id pass in!");
        }
        bundle.putString(UID, uid);
        bundle.putString(PLANTID, plantId);
        bundle.putString(CATEGORY, plantCategory);

        //
        Adapter adapter = new Adapter(getSupportFragmentManager());

        StatusFragment statusFragment = new StatusFragment();
        statusFragment.setArguments(bundle);

        GuideFragment guideFragment = new GuideFragment();
        guideFragment.setArguments(bundle);

        adapter.addFragment(statusFragment, "STATUS");
        adapter.addFragment(guideFragment, "GUIDE");

        viewPager.setAdapter(adapter);

    }
    public class Adapter extends FragmentPagerAdapter {
        List<Fragment> mFragmentList = new ArrayList<>();
        List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter (FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String titile) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(titile);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
