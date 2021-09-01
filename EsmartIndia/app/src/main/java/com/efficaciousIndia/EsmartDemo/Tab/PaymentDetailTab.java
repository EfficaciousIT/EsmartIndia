package com.efficaciousIndia.EsmartDemo.Tab;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.efficaciousIndia.EsmartDemo.R;
import com.efficaciousIndia.EsmartDemo.fragment.Event_Payment_Fragment;
import com.efficaciousIndia.EsmartDemo.fragment.Other_Payment_Fragment;
import com.efficaciousIndia.EsmartDemo.fragment.PaymentDetailFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class PaymentDetailTab extends Fragment {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final String PREFRENCES_NAME = "myprefrences";
    String role_id;
    SharedPreferences settings;
    TabLayout tabLayout;
    ViewPager viewPager;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_calender, container, false);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(PREFRENCES_NAME, 0);
        this.settings = sharedPreferences;
        this.role_id = sharedPreferences.getString("TAG_USERTYPEID", "");
        this.tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        this.viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupViewPager(this.viewPager);
        if (ViewCompat.isLaidOut(this.tabLayout)) {
            setViewPagerListener();
        } else {
            this.tabLayout.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    PaymentDetailTab.this.setViewPagerListener();
                    PaymentDetailTab.this.tabLayout.removeOnLayoutChangeListener(this);
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void setViewPagerListener() {
        this.tabLayout.setupWithViewPager(this.viewPager);
        this.tabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(this.viewPager) {
            public void onTabReselected(TabLayout.Tab tab) {
                super.onTabReselected(tab);
            }

            public void onTabSelected(TabLayout.Tab tab) {
                super.onTabSelected(tab);
            }
        });
    }

    private void setupViewPager(ViewPager viewPager2) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        try {
            viewPagerAdapter.addFragment(new PaymentDetailFragment(), "School Fees");
            viewPagerAdapter.addFragment(new Event_Payment_Fragment(), "Event Payment");
            viewPagerAdapter.addFragment(new Other_Payment_Fragment(), "Other Payment");
        } catch (Exception e) {
        }
        viewPager2.setAdapter(viewPagerAdapter);
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        List<Fragment> fragmentList = new ArrayList();
        List<String> fragmentTitles = new ArrayList();

        public ViewPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        public Fragment getItem(int position) {
            return this.fragmentList.get(position);
        }

        public int getCount() {
            return this.fragmentList.size();
        }

        public CharSequence getPageTitle(int position) {
            return this.fragmentTitles.get(position);
        }

        public void addFragment(Fragment fragment, String name) {
            this.fragmentList.add(fragment);
            this.fragmentTitles.add(name);
        }
    }
}
