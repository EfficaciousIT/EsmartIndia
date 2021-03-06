package com.efficaciousIndia.EsmartDemo.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.efficaciousIndia.EsmartDemo.R;
import com.efficaciousIndia.EsmartDemo.Tab.AdminApproval_Tab;
import com.efficaciousIndia.EsmartDemo.fragment.DailyDiaryListFragment;
import com.efficaciousIndia.EsmartDemo.fragment.Event_list_fragment;
import com.efficaciousIndia.EsmartDemo.fragment.Gallery_Folder;
import com.efficaciousIndia.EsmartDemo.fragment.LeaveListFragment;
import com.efficaciousIndia.EsmartDemo.fragment.MessageCenter;

public class Notifiacton extends AppCompatActivity {
    String value;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);
        FragmentManager mfragment = getSupportFragmentManager();
        try {
            Intent intent = getIntent();
            value = intent.getStringExtra("pagename");
            if (value.contentEquals("Gallery")) {
                Gallery_Folder gallery_folder = new Gallery_Folder();
                mfragment.beginTransaction().replace(R.id.content_main, gallery_folder).commitAllowingStateLoss();
            }
            if (value.contentEquals("Event")) {
                Event_list_fragment event_list_fragment = new Event_list_fragment();
                mfragment.beginTransaction().replace(R.id.content_main, event_list_fragment).commitAllowingStateLoss();
            }
            if (value.contentEquals("LeaveApply")) {
                AdminApproval_Tab adminApproval_tab = new AdminApproval_Tab();
                mfragment.beginTransaction().replace(R.id.content_main, adminApproval_tab).commitAllowingStateLoss();
            }
            if (value.contentEquals("Leave Approval")) {
                LeaveListFragment leaveListFragment = new LeaveListFragment();
                mfragment.beginTransaction().replace(R.id.content_main, leaveListFragment).commitAllowingStateLoss();
            }
            if (value.contentEquals("DailyDiary")) {
                DailyDiaryListFragment dailyDiaryListFragment = new DailyDiaryListFragment();
                Bundle args = new Bundle();
                args.putString("value", "DailyDiary");
                dailyDiaryListFragment.setArguments(args);
                mfragment.beginTransaction().replace(R.id.content_main, dailyDiaryListFragment).commitAllowingStateLoss();
            }
            if (value.contentEquals("HomeWork")) {
                DailyDiaryListFragment dailyDiaryListFragment = new DailyDiaryListFragment();
                Bundle args = new Bundle();
                args.putString("value", "HomeWork");
                dailyDiaryListFragment.setArguments(args);
                mfragment.beginTransaction().replace(R.id.content_main, dailyDiaryListFragment).commitAllowingStateLoss();
            }
            if (value.contentEquals("Trafford School")) {
                MessageCenter msgcenter = new MessageCenter();
                Bundle args = new Bundle();
                args.putString("value", "Trafford School");
                msgcenter.setArguments(args);
                mfragment.beginTransaction().replace(R.id.content_main, msgcenter).commitAllowingStateLoss();
            } else {
                MessageCenter msgcenter = new MessageCenter();
                Bundle args = new Bundle();
                args.putString("value", "");
                msgcenter.setArguments(args);
                mfragment.beginTransaction().replace(R.id.content_main, msgcenter).commitAllowingStateLoss();
            }
        } catch (Exception ex) {

        }

    }
}
