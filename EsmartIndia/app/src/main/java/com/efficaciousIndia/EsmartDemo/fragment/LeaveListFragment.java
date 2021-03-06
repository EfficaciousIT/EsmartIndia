package com.efficaciousIndia.EsmartDemo.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.efficaciousIndia.EsmartDemo.Interface.DataService;
import com.efficaciousIndia.EsmartDemo.R;
import com.efficaciousIndia.EsmartDemo.activity.MainActivity;
import com.efficaciousIndia.EsmartDemo.adapters.LeaveApplyAdapter;
import com.efficaciousIndia.EsmartDemo.common.ConnectionDetector;
import com.efficaciousIndia.EsmartDemo.entity.LeaveDetail;
import com.efficaciousIndia.EsmartDemo.entity.LeaveDetailPojo;
import com.efficaciousIndia.EsmartDemo.webApi.RetrofitInstance;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class LeaveListFragment extends Fragment {
    RecyclerView recyclerView;
    LeaveApplyAdapter adapter;
    private static final String PREFRENCES_NAME = "myprefrences";
    SharedPreferences settings;
    String user_id;
    String UserType_id;
    String School_id;
    String Year_id, Schooli_id;
    ConnectionDetector cd;
    FloatingActionButton apply_leave;
    private ProgressDialog progress;
    public LeaveListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.leave_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        cd = new ConnectionDetector(getActivity());
        settings = getActivity().getSharedPreferences(PREFRENCES_NAME, Context.MODE_PRIVATE);
        user_id = settings.getString("TAG_USERID", "");
        UserType_id = settings.getString("TAG_USERTYPEID", "");
        Year_id = settings.getString("TAG_ACADEMIC_ID", "");
        School_id = settings.getString("TAG_SCHOOL_ID", "");
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.leavelist_list);
        apply_leave=(FloatingActionButton) getActivity().findViewById(R.id.apply_leave);
        progress = new ProgressDialog(getActivity());
        progress.setCancelable(false);
        progress.setCanceledOnTouchOutside(false);
        progress.setMessage("loading...");
        if (!cd.isConnectingToInternet()) {
            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
            alert.setMessage("No InternetConnection");
            alert.setPositiveButton("OK", null);
            alert.show();
        } else {
            try {
                AdminAsync();
            } catch (Exception ex) {
            }
        }
        apply_leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ApplyLeaveFragment applyLeaveFragment = new ApplyLeaveFragment();
                    MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, applyLeaveFragment).commitAllowingStateLoss();
                } catch (Exception ex) {

                }
            }
        });
    }
    public void  AdminAsync (){
        try {
            String command;
            command="Select";
            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            Observable<LeaveDetailPojo> call = service.getLeaveDetailDetails(command,Year_id,UserType_id,user_id,School_id,"");
            call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<LeaveDetailPojo>() {
                @Override
                public void onSubscribe(Disposable disposable) {
                    progress.show();
                }

                @Override
                public void onNext(LeaveDetailPojo body) {
                    try {
                        generateLeaveList((ArrayList<LeaveDetail>) body.getLeaveDetail());
                    } catch (Exception ex) {
                        progress.dismiss();
                        Toast.makeText(getActivity(), "Response taking time seems Network issue!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(Throwable t) {
                    progress.dismiss();
                    Toast.makeText(getActivity(), "Response taking time seems Network issue!", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onComplete() {
                    progress.dismiss();

                }
            });
        } catch (Exception ex) {
            progress.dismiss();
        }
    }

    public void generateLeaveList(ArrayList<LeaveDetail> taskListDataList) {
        try {
            if ((taskListDataList != null && !taskListDataList.isEmpty())) {
                adapter = new LeaveApplyAdapter(taskListDataList,getActivity());
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
            } else {
                Toast toast = Toast.makeText(getActivity(),
                        "No Data Available",
                        Toast.LENGTH_SHORT);
                View toastView = toast.getView();
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                toastView.setBackgroundResource(R.drawable.no_data_available);
                toast.show();
            }

        } catch (Exception ex) {
            progress.dismiss();
            Toast.makeText(getActivity(), "Response taking time seems Network issue!", Toast.LENGTH_SHORT).show();
        }
    }
}