package com.efficaciousIndia.EsmartDemo.dialogbox;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.efficaciousIndia.EsmartDemo.Interface.DataService;
import com.efficaciousIndia.EsmartDemo.R;
import com.efficaciousIndia.EsmartDemo.adapters.StandardAdapter;
import com.efficaciousIndia.EsmartDemo.adapters.Student_division_adapter;
import com.efficaciousIndia.EsmartDemo.entity.StandardDetail;
import com.efficaciousIndia.EsmartDemo.entity.StandardDetailsPojo;
import com.efficaciousIndia.EsmartDemo.webApi.RetrofitInstance;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class Standard_division_dialog extends Activity {
    TextView heading;
    RecyclerView recyclerView;
    String value;
    private static final String PREFRENCES_NAME = "myprefrences";
    SharedPreferences settings;
    String role_id, academic_id, div = null;
    String Standard_id, userid, school_id;
    private CompositeDisposable mCompositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_box);
        settings = getSharedPreferences(PREFRENCES_NAME, Context.MODE_PRIVATE);
        role_id = settings.getString("TAG_USERTYPEID", "");
        div = settings.getString("TAG_DIV", "");
        try {
            if (role_id.contentEquals("6") || role_id.contentEquals("7") || role_id.contentEquals("3")) {
                school_id = StandardAdapter.intSchool_id;
            } else {
                school_id = settings.getString("TAG_SCHOOL_ID", "");
            }
            userid = settings.getString("TAG_USERID", "");
            academic_id = settings.getString("TAG_ACADEMIC_ID", "");
            heading = (TextView) findViewById(R.id.title);
            heading.setText(" Division");

            recyclerView = (RecyclerView) findViewById(R.id.notificationlistView);
            value = StandardAdapter.page_name;
            Standard_id = StandardAdapter.Std_id_division;
            HolidayAsync();
        } catch (Exception ex) {

        }

    }

    public void HolidayAsync() {
        try {
            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            if (role_id.contentEquals("3")) {
                mCompositeDisposable.add(service.getStandardDetails("selectDivisionByLectures", school_id, academic_id, Standard_id, "", userid, "")
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribeWith(getObserver()));
            } else if (div != null) {
                mCompositeDisposable.add(service.getStandardDetails("GetDivision", school_id, "", Standard_id, div, "", "")
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribeWith(getObserver()));

            }

        } catch (Exception ex) {
        }
    }

    public DisposableObserver<StandardDetailsPojo> getObserver() {
        return new DisposableObserver<StandardDetailsPojo>() {

            @Override
            public void onNext(@NonNull StandardDetailsPojo dashboardDetailsPojo) {
                try {
                    generateStandradByLectures((ArrayList<StandardDetail>) dashboardDetailsPojo.getStandardDetails());
                } catch (Exception ex) {

                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Toast.makeText(Standard_division_dialog.this, "Response taking time seems Network issue!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {

            }
        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    public void generateStandradByLectures(ArrayList<StandardDetail> taskListDataList) {
        try {
            if ((taskListDataList != null && !taskListDataList.isEmpty())) {
                Student_division_adapter division_adapter = new Student_division_adapter(Standard_division_dialog.this, taskListDataList, value);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Standard_division_dialog.this);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(division_adapter);
            } else {
                finish();
                Toast toast = Toast.makeText(Standard_division_dialog.this,
                        "No Data Available",
                        Toast.LENGTH_SHORT);
                View toastView = toast.getView();
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                toastView.setBackgroundResource(R.drawable.no_data_available);
                toast.show();
                // Toast.makeText(Standard_division_dialog.this, "No Data Available", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception ex) {
            Toast.makeText(Standard_division_dialog.this, "Response taking time seems Network issue!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}