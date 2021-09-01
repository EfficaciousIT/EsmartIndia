package com.efficaciousIndia.EsmartDemo.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.efficaciousIndia.EsmartDemo.Interface.DataService;
import com.efficaciousIndia.EsmartDemo.R;
import com.efficaciousIndia.EsmartDemo.Tab.AdminApproval_Tab;
import com.efficaciousIndia.EsmartDemo.Tab.Attendence_sliding_tab;
import com.efficaciousIndia.EsmartDemo.Tab.Chating_Sliding_Tab;
import com.efficaciousIndia.EsmartDemo.Tab.DailyDiary_Tab;
import com.efficaciousIndia.EsmartDemo.Tab.Event_Tab;
import com.efficaciousIndia.EsmartDemo.Tab.LibTab_layout;
import com.efficaciousIndia.EsmartDemo.Tab.PaymentDetailTab;
import com.efficaciousIndia.EsmartDemo.Tab.Payment_tab;
import com.efficaciousIndia.EsmartDemo.Tab.StudentAttendanceActivity;
import com.efficaciousIndia.EsmartDemo.Tab.TimetableActivity_student;
import com.efficaciousIndia.EsmartDemo.Tab.TimetableActivity_teacher;
import com.efficaciousIndia.EsmartDemo.Tab.Timetable_sliding_tab;
import com.efficaciousIndia.EsmartDemo.common.ConnectionDetector;
import com.efficaciousIndia.EsmartDemo.entity.BamkDetailResponse;
import com.efficaciousIndia.EsmartDemo.fragment.Admin_Dashboard;
import com.efficaciousIndia.EsmartDemo.fragment.DailyDiaryListFragment;
import com.efficaciousIndia.EsmartDemo.fragment.Event_list_fragment;
import com.efficaciousIndia.EsmartDemo.fragment.Gallery_Folder;
import com.efficaciousIndia.EsmartDemo.fragment.GrievancesFragment;
import com.efficaciousIndia.EsmartDemo.fragment.HolidayFragment;
import com.efficaciousIndia.EsmartDemo.fragment.LeaveListFragment;
import com.efficaciousIndia.EsmartDemo.fragment.MessageCenter;
import com.efficaciousIndia.EsmartDemo.fragment.Noticeboard;
import com.efficaciousIndia.EsmartDemo.fragment.OnlineClassDetail;
import com.efficaciousIndia.EsmartDemo.fragment.OnlineClassTimetable;
import com.efficaciousIndia.EsmartDemo.fragment.ParentDashboard;
import com.efficaciousIndia.EsmartDemo.fragment.Profile;
import com.efficaciousIndia.EsmartDemo.fragment.ResultFragment;
import com.efficaciousIndia.EsmartDemo.fragment.Sms_Fragment;
import com.efficaciousIndia.EsmartDemo.fragment.StudentChangePassword;
import com.efficaciousIndia.EsmartDemo.fragment.StudentExamFragment;
import com.efficaciousIndia.EsmartDemo.fragment.StudentSyllabusFragment;
import com.efficaciousIndia.EsmartDemo.fragment.Student_Std_Fragment;
import com.efficaciousIndia.EsmartDemo.webApi.RetrofitInstance;
import com.google.android.material.navigation.NavigationView;
import com.infideap.drawerbehavior.Advance3DDrawerLayout;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String PREFRENCES_NAME = "myprefrences";
    public static CircleImageView profile_img;
    public static FragmentManager fragmentManager;
    int k, FabmenuStatus = 0;
    String title = "";
    ConnectionDetector cd;
    ImageView chating_imgbtn;
    SharedPreferences settings;
    String role_id, name1, user_id, academic_id, school_id, stud_id, stand_id;
    String Teacher_statndard, Teacher_division;
    NavigationView navigationView;
    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    finish();
                    break;
                case DialogInterface.BUTTON_NEGATIVE:

                    break;
            }
        }
    };
    private Advance3DDrawerLayout drawer;

    //    private DrawerLayout drawer;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            settings = getSharedPreferences(PREFRENCES_NAME, Context.MODE_PRIVATE);
            role_id = settings.getString("TAG_USERTYPEID", "");
            user_id = settings.getString("TAG_USERID", "");
            academic_id = settings.getString("TAG_ACADEMIC_ID", "");
            school_id = settings.getString("TAG_SCHOOL_ID", "");
            getBankDetail();
        } catch (Exception ex) {

        }
        try {
            chating_imgbtn = (ImageView) findViewById(R.id.chating_imgbtn);
            fragmentManager = getSupportFragmentManager();
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            cd = new ConnectionDetector(getApplicationContext());
            profile_img = (CircleImageView) findViewById(R.id.profile_img);
            drawer = (Advance3DDrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();
//            FragmentManager mfragment = getSupportFragmentManager();
            navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            navigationView.setItemIconTintList(null);
//            drawer.setViewScale(Gravity.START, 0.96f);
//            drawer.setRadius(Gravity.START, 20);
//            drawer.setViewElevation(Gravity.START, 8);
//            drawer.setViewRotation(Gravity.START, 15);
        } catch (Exception Ex) {

        }

        try {
            Menu menu = navigationView.getMenu();
//            MenuItem result = menu.findItem(R.id.nav_Result);
//            result.setVisible(false);
            if (role_id.contentEquals("1") || role_id.contentEquals("2")) {
                MenuItem target1;
                target1 = menu.findItem(R.id.nav_library);
                target1.setVisible(true);
                MenuItem message = menu.findItem(R.id.nav_message);
                message.setVisible(false);
                MenuItem target = menu.findItem(R.id.nav_dashboard);
                target.setVisible(false);
                MenuItem teacher = menu.findItem(R.id.nav_teacherAttendence);
                teacher.setVisible(false);
                MenuItem student = menu.findItem(R.id.nav_studentAttendence);
                student.setTitle("Self Attendance");
                MenuItem diary = menu.findItem(R.id.nav_Diary);
                diary.setVisible(false);
                MenuItem staff = menu.findItem(R.id.nav_staffAttendence);
                staff.setVisible(false);
                MenuItem result = menu.findItem(R.id.nav_Result);
                result.setVisible(true);

                MenuItem payment = menu.findItem(R.id.nav_payment_detail);
                payment.setVisible(true);
                MenuItem paymment = menu.findItem(R.id.nav_payment);
                paymment.setVisible(true);

                MenuItem online_timetable = menu.findItem(R.id.nav_online_timetable);
                online_timetable.setVisible(true);
                MenuItem online_classes = menu.findItem(R.id.nav_online_classes);
                online_classes.setVisible(true);

                if (!cd.isConnectingToInternet()) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                    alert.setMessage("No Internet Connection");
                    alert.setPositiveButton("OK", null);
                    alert.show();
                } else {
                    try {
                        fragmentManager.beginTransaction().replace(R.id.content_main, new ParentDashboard()).commitAllowingStateLoss();
//                        fragmentManager.beginTransaction().replace(R.id.content_main, new Profile()).commitAllowingStateLoss();
                        getSupportActionBar().setTitle("Profile");
                    } catch (Exception ex) {

                    }

                }
            } else if (role_id.contentEquals("3")) {
                MenuItem target1;
                target1 = menu.findItem(R.id.nav_library);
                target1.setVisible(true);
                try {
                    Teacher_statndard = settings.getString("TAG_STANDERDID", "");
                    Teacher_division = settings.getString("TAG_DIVISIONID", "");

                    if (Teacher_statndard.contentEquals("0") || Teacher_division.contentEquals("0")) {
                        MenuItem target;
                        target = menu.findItem(R.id.nav_studentAttendence);
                        target.setVisible(false);

                    } else {
                        MenuItem target;
                        target = menu.findItem(R.id.nav_studentAttendence);
                        target.setVisible(true);
                    }
                    MenuItem staff = menu.findItem(R.id.nav_staffAttendence);
                    staff.setVisible(false);
                    MenuItem target = menu.findItem(R.id.nav_dashboard);
                    target.setVisible(false);
                    MenuItem message = menu.findItem(R.id.nav_message);
                    message.setVisible(false);
                    MenuItem student = menu.findItem(R.id.nav_teacherAttendence);
                    student.setTitle("Self Attendance");
                    MenuItem payment = menu.findItem(R.id.nav_payment);
                    payment.setVisible(false);
                    MenuItem resultt = menu.findItem(R.id.nav_Result);
                    resultt.setVisible(false);

                    MenuItem online_timetable = menu.findItem(R.id.nav_online_timetable);
                    online_timetable.setVisible(true);
                    MenuItem online_classes = menu.findItem(R.id.nav_online_classes);
                    online_classes.setVisible(true);

                    MenuItem paymment = menu.findItem(R.id.nav_payment_detail);
                    paymment.setVisible(false);
                    if (!cd.isConnectingToInternet()) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                        alert.setMessage("No Internet Connection");
                        alert.setPositiveButton("OK", null);
                        alert.show();
                    } else {
                        try {
//                            fragmentManager.beginTransaction().replace(R.id.content_main, new Profile_Fragment()).commitAllowingStateLoss();
                            fragmentManager.beginTransaction().replace(R.id.content_main, new Profile()).commitAllowingStateLoss();
                            getSupportActionBar().setTitle("Profile");
                        } catch (Exception ex) {
                        }
                    }
                } catch (Exception ex) {
                }
            } else if (role_id.contentEquals("4")) {
                MenuItem staff = menu.findItem(R.id.nav_staffAttendence);
                staff.setVisible(false);
                MenuItem target;
                target = menu.findItem(R.id.nav_studentAttendence);
                target.setTitle("Self Attendance");
                target = menu.findItem(R.id.nav_dashboard);
                target.setVisible(false);
                target = menu.findItem(R.id.nav_teacherAttendence);
                target.setVisible(false);
                target = menu.findItem(R.id.nav_syllabus);
                target.setVisible(false);
                target = menu.findItem(R.id.nav_timetable);
                target.setVisible(false);
                target = menu.findItem(R.id.nav_examination);
                target.setVisible(false);
                target = menu.findItem(R.id.nav_library);
                target.setVisible(false);
                target = menu.findItem(R.id.nav_Homework);
                target.setVisible(false);
                target = menu.findItem(R.id.nav_Diary);
                target.setVisible(false);
                target = menu.findItem(R.id.nav_Result);
                target.setVisible(false);
                target = menu.findItem(R.id.nav_payment);
                target.setVisible(false);
                target = menu.findItem(R.id.nav_Event);
                target.setVisible(false);
                target = menu.findItem(R.id.nav_message);
                target.setVisible(false);
                MenuItem results = menu.findItem(R.id.nav_Result);
                results.setVisible(false);
                MenuItem online_timetable = menu.findItem(R.id.nav_online_timetable);
                online_timetable.setVisible(false);
                MenuItem online_classes = menu.findItem(R.id.nav_online_classes);
                online_classes.setVisible(false);
                MenuItem paymment = menu.findItem(R.id.nav_payment_detail);
                paymment.setVisible(false);
                if (!cd.isConnectingToInternet()) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                    alert.setMessage("No Internet Connection");
                    alert.setPositiveButton("OK", null);
                    alert.show();
                } else {
                    try {
//                        fragmentManager.beginTransaction().replace(R.id.content_main, new Profile_Fragment()).commitAllowingStateLoss();
                        fragmentManager.beginTransaction().replace(R.id.content_main, new Profile()).commitAllowingStateLoss();
                        getSupportActionBar().setTitle("Profile");
                    } catch (Exception ex) {

                    }

                }
            } else {
                //Here we hide Payment and library option in admin login
                MenuItem target;
                target = menu.findItem(R.id.nav_payment);
                target.setVisible(false);
                MenuItem resulta = menu.findItem(R.id.nav_Result);
                resulta.setVisible(false);
                MenuItem target1;
                target1 = menu.findItem(R.id.nav_library);
                target1.setVisible(true);
                MenuItem paymment = menu.findItem(R.id.nav_payment_detail);
                paymment.setVisible(false);
                MenuItem online_timetable = menu.findItem(R.id.nav_online_timetable);
                online_timetable.setVisible(true);
                MenuItem online_classes = menu.findItem(R.id.nav_online_classes);
                online_classes.setVisible(true);

                //end here

                if (!cd.isConnectingToInternet()) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                    alert.setMessage("No Internet Connection");
                    alert.setPositiveButton("OK", null);
                    alert.show();
                } else {
                    try {
                        fragmentManager.beginTransaction().replace(R.id.content_main, new Admin_Dashboard()).commitAllowingStateLoss();
                        getSupportActionBar().setTitle("Dashboard");
                    } catch (Exception ex) {
                    }
                }
            }
        } catch (Exception ex) {
        }
        profile_img.setOnClickListener(v -> {
            try {
                if (!cd.isConnectingToInternet()) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                    alert.setMessage("No Internet Connection");
                    alert.setPositiveButton("OK", null);
                    alert.show();
                } else {
                    getSupportActionBar().setTitle("Profile");
//                    fragmentManager.beginTransaction().replace(R.id.content_main, new Profile_Fragment()).commitAllowingStateLoss();
                    fragmentManager.beginTransaction().replace(R.id.content_main, new Profile()).commitAllowingStateLoss();
                }
            } catch (Exception ex) {
            }
        });
        chating_imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!cd.isConnectingToInternet()) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                    alert.setMessage("No Internet Connection");
                    alert.setPositiveButton("OK", null);
                    alert.show();
                } else {
                    try {
                        getSupportActionBar().setTitle("Chat");
                        fragmentManager.beginTransaction().replace(R.id.content_main, new Chating_Sliding_Tab()).commitAllowingStateLoss();
                    } catch (Exception ex) {
                    }

                }
            }
        });

    }

    @Override
    public void onBackPressed() {

//        Toast.makeText(this, "onBackPressed", Toast.LENGTH_SHORT).show();
        Log.d("key", "getCurrentFragment" + getCurrentFragment());
        Log.e("TAG", "returnfragment" + String.valueOf(getVisibleFragment()));
        String fragname = String.valueOf(getVisibleFragment());
        if (fragname.contains("Parent_Dashboard")) {
            OnbackforDrawer();
        } else {
            if (role_id.equals("1") || role_id.equals("2"))
                getSupportFragmentManager().beginTransaction().replace(R.id.content_main, new ParentDashboard()).commitAllowingStateLoss();
            else {
                OnbackforDrawer();
            }
        }


//        k++;
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            drawer.openDrawer(GravityCompat.START);
//        }
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                k = 0;
//            }
//        }, 1000);
//        if (k == 1) {
//            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
//        } else {
//            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//            builder.setMessage("Are you sure want to Exit?").setPositiveButton("Yes", dialogClickListener)
//                    .setNegativeButton("No", dialogClickListener).show();
//        }
    }

    Fragment getVisibleFragment() {
        FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                if (fragment != null && fragment.isVisible())
                    return fragment;
            }
        }
        return null;
    }

    private Fragment getCurrentFragment() {
        Fragment currentFragment = getSupportFragmentManager()
                .findFragmentById(R.id.contain_main);
        return currentFragment;
    }

    private void OnbackforDrawer() {
        if (fragmentManager.getBackStackEntryCount() > 0)
            fragmentManager.popBackStackImmediate();
        else {
            k++;
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                drawer.openDrawer(GravityCompat.START);
            }
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    k = 0;
                }
            }, 1000);
            if (k == 1) {
                Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Are you sure want to Exit?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_ChangePassword) {
            try {
                getSupportActionBar().setTitle("Change Password");
                fragmentManager.beginTransaction().replace(R.id.content_main, new StudentChangePassword()).commitAllowingStateLoss();
            } catch (Exception ex) {
            }
            return true;
        }
        if (id == R.id.action_grievances) {
            try {
                getSupportActionBar().setTitle("Grievances");
                fragmentManager.beginTransaction().replace(R.id.content_main, new GrievancesFragment()).commitAllowingStateLoss();
            } catch (Exception ex) {

            }
            return true;
        }
        if (id == R.id.action_Logout) {
            try {
                SharedPreferences.Editor editor_delete = settings.edit();
                editor_delete.clear().commit();
                this.deleteDatabase("Notifications");
                Intent intent = new Intent(MainActivity.this, Login_activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            } catch (Exception ex) {
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager mfragment = getSupportFragmentManager();
        cd = new ConnectionDetector(getApplicationContext());
        drawer.closeDrawer(GravityCompat.START);
        try {
            if (!cd.isConnectingToInternet()) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setMessage("No Internet Connection");
                alert.setPositiveButton("OK", null);
                alert.show();
            } else {
                if (id == R.id.nav_about_effi) {
                    try {
                        openWebPage("http://efficacious.co.in/Index.aspx");
                    } catch (Exception ex) {

                    }
                }
                //Admin Manager Principal menu option  Admin roleid=5,Principal roleid=6 ,Manager roleid=7
                if (role_id.equalsIgnoreCase("5") || role_id.equalsIgnoreCase("6") || role_id.equalsIgnoreCase("7")) {
                    if (id == R.id.nav_dashboard) {
                        try {
                            title = "Dashboard";
                            mfragment.beginTransaction().replace(R.id.content_main, new Admin_Dashboard()).commitAllowingStateLoss();
                        } catch (Exception ex) {
                        }
                    } else if (id == R.id.nav_studentAttendence) {
                        try {
                            title = "Attendance";
                            Student_Std_Fragment student_std_activity = new Student_Std_Fragment();
                            Bundle args = new Bundle();
                            args.putString("pagename", "Std");
                            student_std_activity.setArguments(args);
                            MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, student_std_activity).commitAllowingStateLoss();
                        } catch (Exception ex) {
                        }
                    } else if (id == R.id.nav_teacherAttendence) {
                        try {
                            title = "Attendance";
                            StudentAttendanceActivity studentAttendanceActivity = new StudentAttendanceActivity();
                            Bundle args = new Bundle();
                            args.putString("selected_layout", "Teacher_linearlayout");
                            studentAttendanceActivity.setArguments(args);
                            MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, studentAttendanceActivity).commitAllowingStateLoss();
                        } catch (Exception ex) {
                        }
                    } else if (id == R.id.nav_syllabus) {
                        try {
                            title = "Syllabus";
                            Student_Std_Fragment student_std_activity = new Student_Std_Fragment();
                            Bundle args = new Bundle();
                            args.putString("pagename", "Syllabus");
                            student_std_activity.setArguments(args);
                            MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, student_std_activity).commitAllowingStateLoss();
                        } catch (Exception ex) {
                        }
                    } else if (id == R.id.nav_timetable) {
                        try {
                            title = "TimeTable";
                            Timetable_sliding_tab timetable_sliding_tab = new Timetable_sliding_tab();
                            Bundle args = new Bundle();
                            args.putString("pagename", "TimeTable");
                            timetable_sliding_tab.setArguments(args);
                            mfragment.beginTransaction().replace(R.id.content_main, new Timetable_sliding_tab()).commitAllowingStateLoss();
                        } catch (Exception ex) {
                        }
                    } else if (id == R.id.nav_examination) {
                        try {
                            title = "Examination";
                            Student_Std_Fragment student_std_activity = new Student_Std_Fragment();
                            Bundle args = new Bundle();
                            args.putString("pagename", "Exam");
                            student_std_activity.setArguments(args);
                            MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, student_std_activity).commitAllowingStateLoss();
                        } catch (Exception ex) {
                        }
                    } else if (id == R.id.nav_payment) {
                        try {
                            title = "Payment";
                            Payment_tab student_std_activity = new Payment_tab();
                            Bundle args = new Bundle();
                            args.putString("pagename", "Payment");
                            student_std_activity.setArguments(args);
                            MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, student_std_activity).commitAllowingStateLoss();
                        } catch (Exception ex) {
                        }
                    } else if (id == R.id.nav_library) {
                        try {
                            title = "Library";
//                           mfragment.beginTransaction().replace(R.id.content_main, new All_Standard_Book()).commitAllowingStateLoss();
                            mfragment.beginTransaction().replace(R.id.content_main, new LibTab_layout()).commitAllowingStateLoss();

                        } catch (Exception ex) {
                        }
                    } else if (id == R.id.nav_Diary) {
                        try {
                            title = "Daily Diary";
                            DailyDiaryListFragment dailyDiaryListFragment = new DailyDiaryListFragment();
                            Bundle args = new Bundle();
                            args.putString("value", "DailyDiary");
                            dailyDiaryListFragment.setArguments(args);
                            mfragment.beginTransaction().replace(R.id.content_main, dailyDiaryListFragment).commitAllowingStateLoss();
                        } catch (Exception ex) {
                        }
                    } else if (id == R.id.nav_Homework) {
                        try {
                            title = "Home Work";
                            DailyDiary_Tab dailyDiary_tab = new DailyDiary_Tab();
                            Bundle args = new Bundle();
                            args.putString("value", "HomeWork");
                            dailyDiary_tab.setArguments(args);
                            MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, dailyDiary_tab).commitAllowingStateLoss();
                        } catch (Exception ex) {
                        }
                    } else if (id == R.id.nav_calender) {
                        try {
                            title = "Calendar";
                            mfragment.beginTransaction().replace(R.id.content_main, new HolidayFragment()).commitAllowingStateLoss();
                        } catch (Exception ex) {
                        }
                    } else if (id == R.id.nav_leave) {
                        try {
                            title = "Leave";
                            mfragment.beginTransaction().replace(R.id.content_main, new AdminApproval_Tab()).commitAllowingStateLoss();
                        } catch (Exception ex) {
                        }
                    } else if (id == R.id.nav_Event) {
                        try {
                            title = "Event";
                            mfragment.beginTransaction().replace(R.id.content_main, new Event_list_fragment()).commitAllowingStateLoss();
                        } catch (Exception ex) {
                        }
                    } else if (id == R.id.nav_noticeboard) {
                        try {
                            title = "Noticeboard";
                            mfragment.beginTransaction().replace(R.id.content_main, new Noticeboard()).commitAllowingStateLoss();
                        } catch (Exception ex) {
                        }
                    } else if (id == R.id.nav_message) {
                        try {
                            title = "Messaging";
                            mfragment.beginTransaction().replace(R.id.content_main, new Sms_Fragment()).commitAllowingStateLoss();
                        } catch (Exception ex) {
                        }
                    } else if (id == R.id.nav_messageCenter) {
                        try {
                            title = "Notification";
                            mfragment.beginTransaction().replace(R.id.content_main, new MessageCenter()).commitAllowingStateLoss();
                        } catch (Exception ex) {
                        }
                    } else if (id == R.id.nav_Gallery) {
                        try {
                            title = "Gallery";
                            mfragment.beginTransaction().replace(R.id.content_main, new Gallery_Folder()).commitAllowingStateLoss();
                        } catch (Exception ex) {
                        }
                    } else if (id == R.id.nav_chat) {
                        try {
                            title = "Chat";
                            fragmentManager.beginTransaction().replace(R.id.content_main, new Chating_Sliding_Tab()).commitAllowingStateLoss();
                        } catch (Exception ex) {
                        }
                    } else if (id == R.id.nav_profile) {
                        try {
                            title = "Profile";
//                            fragmentManager.beginTransaction().replace(R.id.content_main, new Profile_Fragment()).commitAllowingStateLoss();
                            fragmentManager.beginTransaction().replace(R.id.content_main, new Profile()).commitAllowingStateLoss();
                        } catch (Exception ex) {
                        }
                    } else if (id == R.id.nav_changePassword) {
                        try {
                            title = "Change Password";
                            fragmentManager.beginTransaction().replace(R.id.content_main, new StudentChangePassword()).commitAllowingStateLoss();
                        } catch (Exception ex) {
                        }
                    } else if (id == R.id.nav_Logout) {
                        try {
                            SharedPreferences.Editor editor_delete = settings.edit();
                            editor_delete.clear().commit();
                            this.deleteDatabase("Notifications");
                            Intent intent = new Intent(MainActivity.this, Login_activity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        } catch (Exception ex) {
                        }
                    } else if (id == R.id.nav_staffAttendence) {
                        try {
//                            Toast.makeText(this, "You selected staff", Toast.LENGTH_SHORT).show();
                            title = "Attendance";
                            StudentAttendanceActivity studentAttendanceActivity = new StudentAttendanceActivity();
                            Bundle args = new Bundle();
                            args.putString("selected_layout", "Staff_layout");
                            studentAttendanceActivity.setArguments(args);
                            MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, studentAttendanceActivity).commitAllowingStateLoss();
                        } catch (Exception ex) {
                        }
                    } else if (id == R.id.nav_online_timetable) {
                        try {
                            title = "Online Timetable";
                            Student_Std_Fragment student_std_activity = new Student_Std_Fragment();
                            Bundle args = new Bundle();
                            args.putString("pagename", "OnlineTimetable");
                            student_std_activity.setArguments(args);
                            MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, student_std_activity).commitAllowingStateLoss();
                        } catch (Exception ex) {
                        }
                    } else if (id == R.id.nav_online_classes) {
                        try {
                            title = "Online Class Detail";
                            Student_Std_Fragment student_std_activity = new Student_Std_Fragment();
                            Bundle args = new Bundle();
                            args.putString("pagename", "OnlineClassDetail");
                            student_std_activity.setArguments(args);
                            MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, student_std_activity).commitAllowingStateLoss();
                        } catch (Exception ex) {
                        }
                    }
                }
                /// roleId=4 Staff Login
                else if (role_id.contentEquals("4")) {
                    if (id == R.id.nav_calender) {
                        try {
                            title = "Calendar";
                            mfragment.beginTransaction().replace(R.id.content_main, new HolidayFragment()).commitAllowingStateLoss();
                        } catch (Exception ex) {
                        }
                    } else if (id == R.id.nav_leave) {
                        try {
                            title = "Leave";
                            mfragment.beginTransaction().replace(R.id.content_main, new LeaveListFragment()).commitAllowingStateLoss();
                        } catch (Exception ex) {
                        }
                    } else if (id == R.id.nav_Gallery) {
                        try {
                            title = "Gallery";
                            mfragment.beginTransaction().replace(R.id.content_main, new Gallery_Folder()).commitAllowingStateLoss();
                        } catch (Exception ex) {
                        }
                    } else if (id == R.id.nav_studentAttendence) {
                        try {
                            title = "Attendance";
                            Attendence_sliding_tab attendence_sliding_tab = new Attendence_sliding_tab();
                            Bundle args = new Bundle();
                            args.putString("attendence", "staff_attendence");
                            attendence_sliding_tab.setArguments(args);
                            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, attendence_sliding_tab).commitAllowingStateLoss();

                        } catch (Exception ex) {
                        }

                    } else if (id == R.id.nav_noticeboard) {
                        try {
                            title = "Noticeboard";
                            mfragment.beginTransaction().replace(R.id.content_main, new Noticeboard()).commitAllowingStateLoss();
                        } catch (Exception ex) {
                        }
                    } else if (id == R.id.nav_messageCenter) {
                        try {
                            title = "Notification";
                            mfragment.beginTransaction().replace(R.id.content_main, new MessageCenter()).commitAllowingStateLoss();
                        } catch (Exception ex) {
                        }
                    } else if (id == R.id.nav_chat) {
                        try {
                            title = "Chat";
                            fragmentManager.beginTransaction().replace(R.id.content_main, new Chating_Sliding_Tab()).commitAllowingStateLoss();
                        } catch (Exception ex) {
                        }
                    } else if (id == R.id.nav_profile) {
                        try {
                            title = "Profile";
//                            fragmentManager.beginTransaction().replace(R.id.content_main, new Profile_Fragment()).commitAllowingStateLoss();
                            fragmentManager.beginTransaction().replace(R.id.content_main, new Profile()).commitAllowingStateLoss();
                        } catch (Exception ex) {
                        }
                    } else if (id == R.id.nav_changePassword) {
                        try {
                            title = "Change Password";
                            fragmentManager.beginTransaction().replace(R.id.content_main, new StudentChangePassword()).commitAllowingStateLoss();
                        } catch (Exception ex) {
                        }
                    } else if (id == R.id.nav_Logout) {
                        try {
                            SharedPreferences.Editor editor_delete = settings.edit();
                            editor_delete.clear().commit();
                            this.deleteDatabase("Notifications");
                            Intent intent = new Intent(MainActivity.this, Login_activity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        } catch (Exception ex) {
                        }
                    } else if (id == R.id.nav_about_effi) {
                        try {
                            openWebPage("http://efficacious.co.in/Index.aspx");
                        } catch (Exception ex) {

                        }

                    }

                }
                /// roleId=3 Teacher Login
                else if (role_id.contentEquals("3")) {
                    if (id == R.id.nav_syllabus) {
                        try {
                            title = "Syllabus";
                            Student_Std_Fragment student_std_activity = new Student_Std_Fragment();
                            Bundle args = new Bundle();
                            args.putString("pagename", "Syllabus");
                            student_std_activity.setArguments(args);
                            MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, student_std_activity).commitAllowingStateLoss();
                        } catch (Exception ex) {
                        }
                    } else if (id == R.id.nav_timetable) {
                        try {
                            title = "TimeTable";
                            mfragment.beginTransaction().replace(R.id.content_main, new TimetableActivity_teacher()).commitAllowingStateLoss();
                        } catch (Exception ex) {
                        }
                    } else if (id == R.id.nav_examination) {
                        try {
                            title = "Examination";
                            Student_Std_Fragment student_std_activity = new Student_Std_Fragment();
                            Bundle args = new Bundle();
                            args.putString("pagename", "Exam");
                            student_std_activity.setArguments(args);
                            MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, student_std_activity).commitAllowingStateLoss();
                        } catch (Exception ex) {
                        }
                    } else if (id == R.id.nav_calender) {
                        try {
                            title = "Calendar";
                            mfragment.beginTransaction().replace(R.id.content_main, new HolidayFragment()).commitAllowingStateLoss();
                        } catch (Exception ex) {
                        }
                    } else if (id == R.id.nav_leave) {
                        try {
                            title = "Leave";
                            mfragment.beginTransaction().replace(R.id.content_main, new LeaveListFragment()).commitAllowingStateLoss();
                        } catch (Exception ex) {
                        }
                    } else if (id == R.id.nav_Event) {
                        try {
                            title = "Event";
                            mfragment.beginTransaction().replace(R.id.content_main, new Event_Tab()).commitAllowingStateLoss();
                        } catch (Exception ex) {
                        }
                    } else if (id == R.id.nav_Gallery) {
                        try {
                            title = "Gallery";
                            mfragment.beginTransaction().replace(R.id.content_main, new Gallery_Folder()).commitAllowingStateLoss();
                        } catch (Exception ex) {
                        }
                    } else if (id == R.id.nav_Homework) {
                        try {
                            title = "Home Work";
                            DailyDiaryListFragment dailyDiaryListFragment = new DailyDiaryListFragment();
                            Bundle args = new Bundle();
                            args.putString("value", "HomeWork");
                            dailyDiaryListFragment.setArguments(args);
                            MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, dailyDiaryListFragment).commitAllowingStateLoss();
                        } catch (Exception ex) {
                        }
                    } else if (id == R.id.nav_Diary) {
                        try {
                            title = "Daily Diary";
                            DailyDiaryListFragment dailyDiaryListFragment = new DailyDiaryListFragment();
                            Bundle args = new Bundle();
                            args.putString("value", "DailyDiary");
                            dailyDiaryListFragment.setArguments(args);
                            MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, dailyDiaryListFragment).commitAllowingStateLoss();
                        } catch (Exception ex) {
                        }
                    }
//                    else if (id == R.id.nav_Result)
//                    {
//                        try
//                        {
//                            title = "Result";
//                            Student_Std_Fragment student_std_activity = new Student_Std_Fragment();
//                            Bundle args = new Bundle();
//                            args.putString("pagename", "Standarad_Result");
//                            student_std_activity.setArguments(args);
//                            MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, student_std_activity).commitAllowingStateLoss();
//
//                        }catch (Exception ex)
//                        {
//
//                        }
//
//                    }
                    else if (id == R.id.nav_studentAttendence) {
                        try {
                            // attendence class teacherwise
                            title = "Attendance";
                            Teacher_statndard = settings.getString("TAG_STANDERDID", "");
                            Teacher_division = settings.getString("TAG_DIVISIONID", "");
                            title = "Attendance";
                            StudentAttendanceActivity studentAttendanceActivity = new StudentAttendanceActivity();
                            Bundle args = new Bundle();
                            args.putString("std_id", Teacher_statndard);
                            args.putString("std_name", Teacher_division);
                            args.putString("std_div", Teacher_division);
                            args.putString("selected_layout", "Stdwise_name");
                            studentAttendanceActivity.setArguments(args);
                            MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, studentAttendanceActivity).commitAllowingStateLoss();
                            // attendence timetablewise
//                            Student_Std_Fragment student_std_activity = new Student_Std_Fragment();
//                            Bundle args = new Bundle();
//                            args.putString("pagename", "Std");
//                            student_std_activity.setArguments(args);
//                            MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, student_std_activity).commitAllowingStateLoss();
                        } catch (Exception ex) {
                        }

                    } else if (id == R.id.nav_teacherAttendence) {
                        try {
                            title = "Attendance";
                            Attendence_sliding_tab attendence_sliding_tab = new Attendence_sliding_tab();
                            Bundle args = new Bundle();
                            args.putString("attendence", "teacher_self_attendence");
                            args.putString("designation", "Teacher");
                            attendence_sliding_tab.setArguments(args);
                            MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, attendence_sliding_tab).commitAllowingStateLoss();
                        } catch (Exception ex) {
                        }
                    } else if (id == R.id.nav_noticeboard) {
                        try {
                            title = "Noticeboard";
                            mfragment.beginTransaction().replace(R.id.content_main, new Noticeboard()).commitAllowingStateLoss();
                        } catch (Exception ex) {
                        }
                    } else if (id == R.id.nav_messageCenter) {
                        try {
                            title = "Notification";
                            mfragment.beginTransaction().replace(R.id.content_main, new MessageCenter()).commitAllowingStateLoss();
                        } catch (Exception ex) {
                        }
                    } else if (id == R.id.nav_library) {
                        try {
                            title = "Library";
//                            Student_Std_Fragment student_std_activity = new Student_Std_Fragment();
//                            Bundle args = new Bundle();
//                            args.putString("pagename", "LibraryTeacher");
//                            student_std_activity.setArguments(args);
//                            MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, student_std_activity).commitAllowingStateLoss();
                            mfragment.beginTransaction().replace(R.id.content_main, new LibTab_layout()).commitAllowingStateLoss();
                        } catch (Exception ex) {
                        }
                    } else if (id == R.id.nav_chat) {
                        try {
                            title = "Chat";
                            fragmentManager.beginTransaction().replace(R.id.content_main, new Chating_Sliding_Tab()).commitAllowingStateLoss();
                        } catch (Exception ex) {
                        }
                    } else if (id == R.id.nav_profile) {
                        try {
                            title = "Profile";
//                            fragmentManager.beginTransaction().replace(R.id.content_main, new Profile_Fragment()).commitAllowingStateLoss();
                            fragmentManager.beginTransaction().replace(R.id.content_main, new Profile()).commitAllowingStateLoss();
                        } catch (Exception ex) {
                        }
                    } else if (id == R.id.nav_changePassword) {
                        try {
                            title = "Change Password";
                            fragmentManager.beginTransaction().replace(R.id.content_main, new StudentChangePassword()).commitAllowingStateLoss();
                        } catch (Exception ex) {
                        }
                    } else if (id == R.id.nav_Logout) {
                        try {
                            SharedPreferences.Editor editor_delete = settings.edit();
                            editor_delete.clear().commit();
                            this.deleteDatabase("Notifications");
                            Intent intent = new Intent(MainActivity.this, Login_activity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        } catch (Exception ex) {
                        }
                    } else if (id == R.id.nav_online_timetable) {
                        try {
                            title = "Online Timetable";
                            Student_Std_Fragment student_std_activity = new Student_Std_Fragment();
                            Bundle args = new Bundle();
                            args.putString("pagename", "OnlineTimetable");
                            student_std_activity.setArguments(args);
                            MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, student_std_activity).commitAllowingStateLoss();
                        } catch (Exception ex) {
                        }
                    } else if (id == R.id.nav_online_classes) {
                        try {
                            title = "Online Class Detail";
                            Student_Std_Fragment student_std_activity = new Student_Std_Fragment();
                            Bundle args = new Bundle();
                            args.putString("pagename", "OnlineClassDetail");
                            student_std_activity.setArguments(args);
                            MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, student_std_activity).commitAllowingStateLoss();
                        } catch (Exception ex) {
                        }
                    }
                }
                //Student Parent Login
                else if (role_id.contentEquals("1")) {
                    if (id == R.id.nav_syllabus) {
                        try {
                            title = "Syllabus";
                            stand_id = settings.getString("TAG_STANDERDID", "");
                            StudentSyllabusFragment subjectFragment = new StudentSyllabusFragment();
                            Bundle args = new Bundle();
                            args.putString("std_id", stand_id);
                            subjectFragment.setArguments(args);
                            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, subjectFragment).commitAllowingStateLoss();
                        } catch (Exception ex) {
                        }

                    } else if (id == R.id.nav_timetable) {
                        try {
                            title = "TimeTable";
                            stand_id = settings.getString("TAG_STANDERDID", "");
                            TimetableActivity_student timetableActivity_student = new TimetableActivity_student();
                            Bundle args = new Bundle();
                            args.putString("std_id", stand_id);
                            timetableActivity_student.setArguments(args);
                            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, timetableActivity_student).commitAllowingStateLoss();
                        } catch (Exception ex) {
                        }

                    } else if (id == R.id.nav_dashboard) {
                        try {

                            title = "Dashboard";
                            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, new ParentDashboard()).commitAllowingStateLoss();

                        } catch (Exception ex) {

                        }
                    } else if (id == R.id.nav_examination) {
                        try {
                            title = "Examination";
                            stand_id = settings.getString("TAG_STANDERDID", "");
                            StudentExamFragment studentExamActivity = new StudentExamFragment();
                            Bundle args = new Bundle();
                            args.putString("std_id", stand_id);
                            studentExamActivity.setArguments(args);
                            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, studentExamActivity).commitAllowingStateLoss();

                        } catch (Exception ex) {

                        }

                    } else if (id == R.id.nav_calender) {
                        try {
                            title = "Calendar";
                            mfragment.beginTransaction().replace(R.id.content_main, new HolidayFragment()).commitAllowingStateLoss();

                        } catch (Exception ex) {

                        }

                    } else if (id == R.id.nav_leave) {
                        try {
                            title = "Leave";
                            mfragment.beginTransaction().replace(R.id.content_main, new LeaveListFragment()).commitAllowingStateLoss();

                        } catch (Exception ex) {

                        }
                    } else if (id == R.id.nav_Event) {
                        try {
                            title = "Event";
                            mfragment.beginTransaction().replace(R.id.content_main, new Event_Tab()).commitAllowingStateLoss();

                        } catch (Exception ex) {

                        }

                    } else if (id == R.id.nav_Gallery) {
                        try {
                            title = "Gallery";
                            mfragment.beginTransaction().replace(R.id.content_main, new Gallery_Folder()).commitAllowingStateLoss();

                        } catch (Exception ex) {

                        }

                    } else if (id == R.id.nav_payment) {
                        title = "Payment";
                        Payment_tab student_std_activity = new Payment_tab();
                        Bundle args = new Bundle();
                        args.putString("pagename", "Payment");
                        student_std_activity.setArguments(args);
                        MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, student_std_activity).commitAllowingStateLoss();

                    } else if (id == R.id.nav_payment_detail) {
                        title = "Payment History";
                        PaymentDetailTab student_std_activity = new PaymentDetailTab();
                        Bundle args = new Bundle();
                        args.putString("pagename", "Payment");
                        student_std_activity.setArguments(args);
                        MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, student_std_activity).commitAllowingStateLoss();

                    } else if (id == R.id.nav_Homework) {
                        try {
                            title = "Home Work";
                            DailyDiaryListFragment dailyDiaryListFragment = new DailyDiaryListFragment();
                            Bundle args = new Bundle();
                            args.putString("value", "HomeWork");
                            dailyDiaryListFragment.setArguments(args);
                            mfragment.beginTransaction().replace(R.id.content_main, dailyDiaryListFragment).commitAllowingStateLoss();
                            // mfragment.beginTransaction().replace(R.id.content_main, new StudentHomeworkFragment()).commitAllowingStateLoss();

                        } catch (Exception ex) {

                        }

                    } else if (id == R.id.nav_Result) {
                        try {

                            title = "Result";
                            mfragment.beginTransaction().replace(R.id.content_main, new ResultFragment()).commitAllowingStateLoss();

                        } catch (Exception ex) {

                        }

                    } else if (id == R.id.nav_studentAttendence) {
                        try {
                            title = "Attendance";
                            stud_id = settings.getString("TAG_STUDENTID", "");
                            Attendence_sliding_tab attendence_sliding_tab = new Attendence_sliding_tab();
                            Bundle args = new Bundle();
                            args.putString("Stud_name", name1);
                            args.putString("stud_id12", stud_id);
                            args.putString("attendence", "student_self_attendence");
                            attendence_sliding_tab.setArguments(args);
                            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, attendence_sliding_tab).commitAllowingStateLoss();

                        } catch (Exception ex) {

                        }

                    } else if (id == R.id.nav_teacherAttendence) {
                        try {
                            title = "Attendance";
                            StudentAttendanceActivity studentAttendanceActivity = new StudentAttendanceActivity();
                            Bundle args = new Bundle();
                            args.putString("selected_layout", "Teacher_linearlayout");
                            studentAttendanceActivity.setArguments(args);
                            MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, studentAttendanceActivity).commitAllowingStateLoss();

                        } catch (Exception ex) {

                        }

                    } else if (id == R.id.nav_noticeboard) {
                        try {
                            title = "Noticeboard";
                            mfragment.beginTransaction().replace(R.id.content_main, new Noticeboard()).commitAllowingStateLoss();

                        } catch (Exception ex) {

                        }

                    } else if (id == R.id.nav_messageCenter) {
                        try {
                            title = "Notification";
                            mfragment.beginTransaction().replace(R.id.content_main, new MessageCenter()).commitAllowingStateLoss();

                        } catch (Exception ex) {

                        }

                    } else if (id == R.id.nav_library) {
                        try {
                            title = "Library";
//                            mfragment.beginTransaction().replace(R.id.content_main, new StandardWise_Book()).commitAllowingStateLoss();
                            mfragment.beginTransaction().replace(R.id.content_main, new LibTab_layout()).commitAllowingStateLoss();

                        } catch (Exception ex) {

                        }

                    } else if (id == R.id.nav_about_effi) {
                        try {
                            openWebPage("http://efficacious.co.in/Index.aspx");
                        } catch (Exception ex) {

                        }

                    } else if (id == R.id.nav_chat) {
                        try {
                            title = "Chat";
                            fragmentManager.beginTransaction().replace(R.id.content_main, new Chating_Sliding_Tab()).commitAllowingStateLoss();
                        } catch (Exception ex) {

                        }

                    } else if (id == R.id.nav_profile) {
                        try {
                            title = "Profile";
//                            fragmentManager.beginTransaction().replace(R.id.content_main, new Profile_Fragment()).commitAllowingStateLoss();
                            fragmentManager.beginTransaction().replace(R.id.content_main, new Profile()).commitAllowingStateLoss();
                        } catch (Exception ex) {

                        }

                    } else if (id == R.id.nav_changePassword) {
                        try {
                            title = "Change Password";
                            fragmentManager.beginTransaction().replace(R.id.content_main, new StudentChangePassword()).commitAllowingStateLoss();
                        } catch (Exception ex) {

                        }

                    } else if (id == R.id.nav_Logout) {
                        try {
                            SharedPreferences.Editor editor_delete = settings.edit();
                            editor_delete.clear().commit();
                            this.deleteDatabase("Notifications");
                            Intent intent = new Intent(MainActivity.this, Login_activity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        } catch (Exception ex) {

                        }

                    } else if (id == R.id.nav_online_timetable) {
                        try {
                            title = "Online Timetable";
                            mfragment.beginTransaction().replace(R.id.content_main, new OnlineClassTimetable()).commitAllowingStateLoss();
                        } catch (Exception ex) {
                        }
                    } else if (id == R.id.nav_online_classes) {
                        try {
                            title = "Online Class Detail";
                            mfragment.beginTransaction().replace(R.id.content_main, new OnlineClassDetail()).commitAllowingStateLoss();
                        } catch (Exception ex) {
                        }
                    }
                }
                DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

//                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                getSupportActionBar().setTitle(title);
            }
        } catch (Exception ex) {
        }
        return true;
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    public void openWebPage(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void getBankDetail() {
        try {
            ProgressDialog progressBar;
            progressBar = new ProgressDialog(MainActivity.this);
            progressBar.setCancelable(false);
            progressBar.setCanceledOnTouchOutside(false);
            progressBar.setMessage("loading...");
            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            Observable<BamkDetailResponse> call = service.getBankDetails("select", school_id);
            call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<BamkDetailResponse>() {
                @Override
                public void onSubscribe(Disposable disposable) {
                    progressBar.show();
                }

                @Override
                public void onNext(BamkDetailResponse body) {
                    try {
                        settings.edit().putString("TAG_vchBank_name", body.getAPKVersion().get(0).getVchBankName()).commit();
                        settings.edit().putString("TAG_vchIFSC", body.getAPKVersion().get(0).getVchIFSC()).commit();
                        settings.edit().putString("TAG_vchBankAc_no", body.getAPKVersion().get(0).getVchBankAcNo()).commit();
                        settings.edit().putString("TAG_vchAccountholder_name", body.getAPKVersion().get(0).getVchAccountholderName()).commit();
                    } catch (Exception ex) {
                        progressBar.dismiss();
                        Toast.makeText(MainActivity.this, "Response taking time seems Network issue!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(Throwable t) {
                    progressBar.dismiss();
                    Toast.makeText(MainActivity.this, "Response taking time seems Network issue!", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onComplete() {
                    progressBar.dismiss();

                }
            });
        } catch (Exception ex) {

        }
    }
}

