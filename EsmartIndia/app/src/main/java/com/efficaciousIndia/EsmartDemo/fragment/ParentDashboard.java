package com.efficaciousIndia.EsmartDemo.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.efficaciousIndia.EsmartDemo.R;
import com.efficaciousIndia.EsmartDemo.Tab.Attendence_sliding_tab;
import com.efficaciousIndia.EsmartDemo.Tab.Chating_Sliding_Tab;
import com.efficaciousIndia.EsmartDemo.Tab.TimetableActivity_student;
import com.efficaciousIndia.EsmartDemo.activity.MainActivity;
import com.efficaciousIndia.EsmartDemo.webApi.RetrofitInstance;

import de.hdodenhof.circleimageview.CircleImageView;


public class ParentDashboard extends Fragment implements View.OnClickListener {
    private static final String PREFRENCES_NAME = "myprefrences";
    CircleImageView ProfileImage_dash;
    String Url;
    String academic_id;
    LinearLayout attandance_layout;
    ImageView chat;
    LinearLayout exam_layout;
    FragmentManager fragmentManager;
    LinearLayout gallery_layout;
    LinearLayout holiday_layout;
    LinearLayout homework_layout;
    ImageView logout;
    DrawerLayout mDrawerLayout;
    View myview;
    String name;
    String name1;
    Button nav_button;
    LinearLayout noticeboard_layout;
    LinearLayout notification_layout;
    LinearLayout profile_layout;
    String role_id;
    String school_id;
    SharedPreferences settings;
    String stand_id;
    String stud_id;
    LinearLayout syllabusLayout;
    LinearLayout timetable_layout;
    String title = "";
    TextView userID;
    String user_id;
    TextView username;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_parent_dashboard, container, false);
        this.myview = inflate;
        initialization(inflate);
        this.fragmentManager = getFragmentManager();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(PREFRENCES_NAME, 0);
        this.settings = sharedPreferences;
        this.role_id = sharedPreferences.getString("TAG_USERTYPEID", "");
        this.user_id = this.settings.getString("TAG_USERID", "");
        this.academic_id = this.settings.getString("TAG_ACADEMIC_ID", "");
        this.school_id = this.settings.getString("TAG_SCHOOL_ID", "");
        this.name = this.settings.getString("TAG_NAME", "");
        operations();
        ((MainActivity) getActivity()).setActionBarTitle("Dashboard");
        return this.myview;
    }

    private void operations() {
        Log.e("TAG", "Username" + this.name + "__" + this.user_id);
        this.username.setText(this.name);
        StringBuilder sb = new StringBuilder();
        sb.append("TAG_PROFILE_IMAGE_PRO");
        sb.append(this.settings.getString("TAG_PROFILE_IMAGE_PRO", ""));
        Log.e("Image", sb.toString());
        Glide.with(getActivity()).load(RetrofitInstance.Image_URL + this.settings.getString("TAG_PROFILE_IMAGE_PRO", "")).error((int) R.mipmap.profile).into(this.ProfileImage_dash);
        if (this.role_id.contentEquals("1")||role_id.contentEquals("2")) {
            this.userID.setText("Parent");
        } else if (this.role_id.contentEquals("3")) {
            this.userID.setText("Teacher");
        } else if (this.role_id.contentEquals("5")) {
            this.userID.setText("Admin");
        }
    }

    private void initialization(View myview2) {
        this.ProfileImage_dash = (CircleImageView) myview2.findViewById(R.id.image_parent);
        this.profile_layout = (LinearLayout) myview2.findViewById(R.id.profile_icon);
        this.timetable_layout = (LinearLayout) myview2.findViewById(R.id.timetable_layout);
        this.exam_layout = (LinearLayout) myview2.findViewById(R.id.exam_layout);
        this.homework_layout = (LinearLayout) myview2.findViewById(R.id.homework_layout);
        this.attandance_layout = (LinearLayout) myview2.findViewById(R.id.attendance_layout);
        this.noticeboard_layout = (LinearLayout) myview2.findViewById(R.id.noticeboard_layout);
        this.holiday_layout = (LinearLayout) myview2.findViewById(R.id.holiday_layout);
        this.username = (TextView) myview2.findViewById(R.id.user_name);
        this.userID = (TextView) myview2.findViewById(R.id.user_id);
        this.gallery_layout = (LinearLayout) myview2.findViewById(R.id.gallery_layout);
        this.syllabusLayout = (LinearLayout) myview2.findViewById(R.id.syllabusLayout);
        this.nav_button = (Button) myview2.findViewById(R.id.btn_navigation);
        this.chat = (ImageView) myview2.findViewById(R.id.chat);
        this.logout = (ImageView) myview2.findViewById(R.id.logout);
        this.profile_layout.setOnClickListener(this);
        this.timetable_layout.setOnClickListener(this);
        this.exam_layout.setOnClickListener(this);
        this.homework_layout.setOnClickListener(this);
        this.attandance_layout.setOnClickListener(this);
        this.noticeboard_layout.setOnClickListener(this);
        this.holiday_layout.setOnClickListener(this);
        this.gallery_layout.setOnClickListener(this);
        this.syllabusLayout.setOnClickListener(this);
        this.nav_button.setOnClickListener(this);
        this.chat.setOnClickListener(this);
        this.logout.setOnClickListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.attendance_layout:
                this.title = "Attendance";
                this.stud_id = this.settings.getString("TAG_STUDENTID", "");
                Attendence_sliding_tab attendence_sliding_tab = new Attendence_sliding_tab();
                Bundle attendance = new Bundle();
                attendance.putString("Stud_name", this.name1);
                attendance.putString("stud_id12", this.stud_id);
                attendance.putString("attendence", "student_self_attendence");
                attendence_sliding_tab.setArguments(attendance);
                getFragmentManager().beginTransaction().replace(R.id.content_main, attendence_sliding_tab).commitAllowingStateLoss();
                return;
            case R.id.btn_navigation:
                DrawerLayout drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
                this.mDrawerLayout = drawerLayout;
                if (!drawerLayout.isDrawerOpen((int) GravityCompat.START)) {
                    this.mDrawerLayout.openDrawer((int) GravityCompat.START);
                    return;
                } else {
                    this.mDrawerLayout.closeDrawer((int) GravityCompat.END);
                    return;
                }
            case R.id.chat:
                this.mDrawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
                this.fragmentManager.beginTransaction().replace(R.id.content_main, new Chating_Sliding_Tab()).commitAllowingStateLoss();
                return;
            case R.id.exam_layout:
                this.title = "Examination";
                this.stand_id = this.settings.getString("TAG_STANDERDID", "");
                StudentExamFragment studentExamActivity = new StudentExamFragment();
                Bundle args1 = new Bundle();
                args1.putString("std_id", this.stand_id);
                studentExamActivity.setArguments(args1);
                getFragmentManager().beginTransaction().replace(R.id.content_main, studentExamActivity).commitAllowingStateLoss();
                return;
            case R.id.gallery_layout:
                this.title = "Gallery";
                this.fragmentManager.beginTransaction().replace(R.id.content_main, new Gallery_Folder()).commitAllowingStateLoss();
                return;
            case R.id.holiday_layout:
                this.title = "Holiday";
                getFragmentManager().beginTransaction().replace(R.id.content_main, new HolidayFragment()).commitAllowingStateLoss();
                return;
            case R.id.homework_layout:
                this.title = "Home Work";
                DailyDiaryListFragment dailyDiaryListFragment = new DailyDiaryListFragment();
                Bundle hwargs = new Bundle();
                hwargs.putString("value", "HomeWork");
                dailyDiaryListFragment.setArguments(hwargs);
                this.fragmentManager.beginTransaction().replace(R.id.content_main, dailyDiaryListFragment).commitAllowingStateLoss();
                return;
            case R.id.noticeboard_layout:
                this.title = "Noticeboard";
                getFragmentManager().beginTransaction().replace(R.id.content_main, new Noticeboard()).commitAllowingStateLoss();
                return;
            case R.id.profile_icon:
                this.fragmentManager.beginTransaction().replace(R.id.content_main, new Profile()).commitAllowingStateLoss();
                return;
            case R.id.syllabusLayout:
                this.title = "Syllabus";
                this.stand_id = this.settings.getString("TAG_STANDERDID", "");
                StudentSyllabusFragment subjectFragment = new StudentSyllabusFragment();
                Bundle sargs = new Bundle();
                sargs.putString("std_id", this.stand_id);
                subjectFragment.setArguments(sargs);
                getFragmentManager().beginTransaction().replace(R.id.content_main, subjectFragment).commitAllowingStateLoss();
                return;
            case R.id.timetable_layout:
                this.title = "TimeTable";
                this.stand_id = this.settings.getString("TAG_STANDERDID", "");
                TimetableActivity_student timetableActivity_student = new TimetableActivity_student();
                Bundle args = new Bundle();
                args.putString("std_id", this.stand_id);
                args.putString("key", "From_Parent_Dashboard");
                timetableActivity_student.setArguments(args);
                getFragmentManager().beginTransaction().replace(R.id.content_main, timetableActivity_student, (String) null).addToBackStack("TimeTable").commitAllowingStateLoss();
                return;
            default:
                return;
        }
    }

    public void setActionBarTitle(String title2) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle((CharSequence) title2);
    }
}
