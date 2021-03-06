package com.efficaciousIndia.EsmartDemo.Tab;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.efficaciousIndia.EsmartDemo.R;
import com.efficaciousIndia.EsmartDemo.fragment.DailyDiaryListFragment;
import com.efficaciousIndia.EsmartDemo.fragment.Homework_ApprovedList;
import com.efficaciousIndia.EsmartDemo.fragment.Homework_RejectedList;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class DailyDiary_Tab extends Fragment {

    ViewPager viewPager;
    TabLayout tabLayout;
    public static String value;
    private static final String PREFRENCES_NAME = "myprefrences";
    SharedPreferences settings;
    String role_id;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_calender, container, false);
        settings = getActivity().getSharedPreferences(PREFRENCES_NAME, Context.MODE_PRIVATE);
        role_id = settings.getString("TAG_USERTYPEID", "");
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);


        AppCompatActivity activity = (AppCompatActivity) getActivity();
        assert activity.getSupportActionBar() != null;
        // activity.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.lightorange)));

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setupViewPager(viewPager);
        // after you set the adapter you have to check if view is laid out, i did a custom method for it
        if (ViewCompat.isLaidOut(tabLayout)) {
            setViewPagerListener();
        } else {
            tabLayout.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    setViewPagerListener();
                    tabLayout.removeOnLayoutChangeListener(this);
                }
            });
        }
    }

    private void setViewPagerListener() {
        if (viewPager!=null)
        tabLayout.setupWithViewPager(viewPager);
        else
            tabLayout.setupWithViewPager(viewPager);

        // use class TabLayout.ViewPagerOnTabSelectedListener
        // note that it's a class not an interface as OnTabSelectedListener, so you can't implement it in your activity/fragment
        // methods are optional, so if you don't use them, you can not override them (e.g. onTabUnselected)
        tabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                super.onTabReselected(tab);
            }

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab!=null)
                super.onTabSelected(tab);
                else
                    super.onTabSelected(tab);
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        try {
            value = getArguments().getString("value");

            if (role_id.contentEquals("5") && value.contentEquals("DailyDiary")) {
                viewPagerAdapter.addFragment(new DailyDiaryListFragment(), "List");

            } else if (role_id.contentEquals("5") && value.contentEquals("HomeWork")) {
                viewPagerAdapter.addFragment(new DailyDiaryListFragment(), "List");
                viewPagerAdapter.addFragment(new Homework_ApprovedList(), "Approved List");
                viewPagerAdapter.addFragment(new Homework_RejectedList(), "Rejected List");
            } else if (role_id.contentEquals("6") && value.contentEquals("DailyDiary")) {
                viewPagerAdapter.addFragment(new DailyDiaryListFragment(), "List");

            } else if (role_id.contentEquals("6") && value.contentEquals("HomeWork")) {
                viewPagerAdapter.addFragment(new DailyDiaryListFragment(), "List");
                viewPagerAdapter.addFragment(new Homework_ApprovedList(), "Approved List");
                viewPagerAdapter.addFragment(new Homework_RejectedList(), "Rejected List");
            } else if (role_id.contentEquals("7") && value.contentEquals("DailyDiary")) {
                viewPagerAdapter.addFragment(new DailyDiaryListFragment(), "List");

            } else if (role_id.contentEquals("7") && value.contentEquals("HomeWork")) {
                viewPagerAdapter.addFragment(new DailyDiaryListFragment(), "List");
                viewPagerAdapter.addFragment(new Homework_ApprovedList(), "Approved List");
                viewPagerAdapter.addFragment(new Homework_RejectedList(), "Rejected List");
            }
        } catch (Exception ex) {

        }


        viewPager.setAdapter(viewPagerAdapter);
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        List<Fragment> fragmentList = new ArrayList<>();
        List<String> fragmentTitles = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitles.get(position);
        }

        public void addFragment(Fragment fragment, String name) {
            fragmentList.add(fragment);
            fragmentTitles.add(name);
        }
    }
}