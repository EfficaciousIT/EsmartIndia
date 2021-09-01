package com.efficaciousIndia.EsmartDemo.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.efficaciousIndia.EsmartDemo.Interface.DataService;
import com.efficaciousIndia.EsmartDemo.R;
import com.efficaciousIndia.EsmartDemo.adapters.Book_Adapter;
import com.efficaciousIndia.EsmartDemo.adapters.std_bottom_adapter;
import com.efficaciousIndia.EsmartDemo.common.ConnectionDetector;
import com.efficaciousIndia.EsmartDemo.entity.StandardDetail;
import com.efficaciousIndia.EsmartDemo.entity.StandardDetailsPojo;
import com.efficaciousIndia.EsmartDemo.entity.SubjLibraryDetail;
import com.efficaciousIndia.EsmartDemo.entity.SubjectDetailLibPojo;
import com.efficaciousIndia.EsmartDemo.webApi.RetrofitInstance;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Rahul on 18,May,2020
 */
public class BookList_Fragment extends Fragment implements std_bottom_adapter.STdDataClick {
    View mview;
    RecyclerView recycler;
    private static final String PREFRENCES_NAME = "myprefrences";
    SharedPreferences settings;
    private ProgressDialog progress;
    String Schooli_id;
    ConnectionDetector cd;
    private CompositeDisposable mCompositeDisposable;
    String value, academic_id, role_id, userid, Standard_id;
    ArrayList<StandardDetail> standardDetailArrayList = new ArrayList<>();
    LinearLayout std_linear;
    RelativeLayout std_relative;
    TextView std_tv1, std_tv;
    BottomSheetDialog dialog;
    Book_Adapter madapter;
    ImageView arrow;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mview = inflater.inflate(R.layout.booklist_fragment, null);
        settings = getActivity().getSharedPreferences(PREFRENCES_NAME, Context.MODE_PRIVATE);
        Schooli_id = settings.getString("TAG_SCHOOL_ID", "");
        userid = settings.getString("TAG_USERID", "");
        role_id = settings.getString("TAG_USERTYPEID", "");
        academic_id = settings.getString("TAG_ACADEMIC_ID", "");
        recycler = (RecyclerView) mview.findViewById(R.id.recycler);
        std_linear = mview.findViewById(R.id.std_linear);
        std_relative = mview.findViewById(R.id.std_relative);
        std_tv1 = mview.findViewById(R.id.std_tv1);
        std_tv = mview.findViewById(R.id.std_tv);
        arrow = mview.findViewById(R.id.arrow);
        cd = new ConnectionDetector(getContext());
        progress = new ProgressDialog(getActivity());
        progress.setCancelable(false);
        progress.setCanceledOnTouchOutside(false);
        progress.setMessage("loading...");
        if(role_id.contentEquals("1")||role_id.contentEquals("2")){
            Standard_id = settings.getString("TAG_STANDERDID", "");
            std_linear.setVisibility(View.GONE);
            std_relative.setVisibility(View.GONE);
        }else {
            std_linear.setVisibility(View.VISIBLE);
            std_relative.setVisibility(View.VISIBLE);
        }
        if (!cd.isConnectingToInternet()) {
            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
            alert.setMessage("No InternetConnection");
            alert.setPositiveButton("OK", null);
            alert.show();
        } else {
            try {
                if (role_id.contentEquals("3")) {
                    userid = settings.getString("TAG_USERID", "");
                   // StudenStandardtAsync();
                    LoginAsync();
                } else if (role_id.contentEquals("5") || role_id.contentEquals("6") || role_id.contentEquals("7")) {
                    LoginAsync();
                }else if(role_id.contentEquals("1")||role_id.contentEquals("2")){
                    StudentAsync (Integer.parseInt(Standard_id));
                }
            } catch (Exception ex) {

            }
        }
        std_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(role_id.contentEquals("3")||role_id.contentEquals("5")||role_id.contentEquals("6")||role_id.contentEquals("7")){
                    showlBottomSheetDialog(getActivity());
                }

            }
        });
        std_relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(role_id.contentEquals("3")||role_id.contentEquals("5")||role_id.contentEquals("6")||role_id.contentEquals("7")){
//                    showlBottomSheetDialog(getActivity());
                    if(recycler.getVisibility()== View.VISIBLE){
                        recycler.setVisibility(View.GONE);
                       arrow.animate().rotation(180).start();
                    }else {
                        recycler.setVisibility(View.VISIBLE);
                        arrow.animate().rotation(0).start();
                    }

                }
            }
        });
        return mview;
    }

    public void StudenStandardtAsync() {
        try {
            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            mCompositeDisposable.add(service.getStandardDetails("selectStandradByLectures", Schooli_id, academic_id, "", "", userid, "")
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribeWith(getObserver()));
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
                Toast.makeText(getActivity(), "Response taking time seems Network issue!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {

            }
        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mCompositeDisposable != null && !mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.dispose();
        }
    }


    public void generateStandradByLectures(ArrayList<StandardDetail> taskListDataList) {
        try {
            standardDetailArrayList.addAll(taskListDataList);
//            if ((taskListDataList != null && !taskListDataList.isEmpty())) {
//                std_tv.setText(standardDetailArrayList.get(0).getVchStandardName());
//                std_tv1.setText(standardDetailArrayList.get(0).getVchStandardName());
//                Standard_id= String.valueOf(standardDetailArrayList.get(0).getIntStandardId());
//                StudentAsync (standardDetailArrayList.get(0).getIntStandardId());
//            } else {
//                Toast toast = Toast.makeText(getActivity(),
//                        "No Data Available",
//                        Toast.LENGTH_SHORT);
//                View toastView = toast.getView();
//                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
//                toastView.setBackgroundResource(R.drawable.no_data_available);
//                toast.show();
//            }

        } catch (Exception ex) {
            Toast.makeText(getActivity(), "Response taking time seems Network issue!", Toast.LENGTH_SHORT).show();
        }
    }

    public void LoginAsync() {
        try {
            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            if (role_id.contentEquals("6") || role_id.contentEquals("7")) {
                mCompositeDisposable.add(service.getStandardDetails("selectStandardByPrincipal", "", "", "", "", "", "")
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribeWith(getObserver()));
            } else {
                mCompositeDisposable.add(service.getStandardDetails("select", Schooli_id, "", "", "", "", "")
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribeWith(getObserver()));
            }

        } catch (Exception ex) {
        }
    }


    public void showlBottomSheetDialog(Activity activity) {
        View view = activity.getLayoutInflater().inflate(R.layout.std_bottomsheet, null);
        RecyclerView std_recycler = view.findViewById(R.id.std_recycler);
        RelativeLayout minimize_relative = view.findViewById(R.id.minimize_relative);
        dialog = new BottomSheetDialog(view.getContext());
        dialog.setContentView(view);
        dialog.show();
        dialog.setCancelable(true);
        minimize_relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        std_bottom_adapter stdBottomAdapter = new std_bottom_adapter(getContext(), standardDetailArrayList, BookList_Fragment.this);
        std_recycler.setLayoutManager(new GridLayoutManager(getContext(), 3));
        std_recycler.setAdapter(stdBottomAdapter);
    }

    @Override
    public void onSTdDataClick(int id, String details) {
        std_tv.setText(details);
        std_tv1.setText(details);
        dialog.dismiss();
        Standard_id= String.valueOf(id);
        StudentAsync (id);
    }
    public void  StudentAsync (int standard_id){
        try {
            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            Observable<SubjectDetailLibPojo> call = service.getLibraryDetails("GetStandardWiseBookList",Schooli_id, String.valueOf(standard_id));
            call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<SubjectDetailLibPojo>() {
                @Override
                public void onSubscribe(Disposable disposable) {
                    progress.show();
                }

                @Override
                public void onNext(SubjectDetailLibPojo body) {
                    try {
                        generateBookList((ArrayList<SubjLibraryDetail>) body.getLibraryDetail());
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
            ex.getMessage();
            progress.dismiss();
        }
    }

    public void generateBookList(ArrayList<SubjLibraryDetail> taskListDataList) {
        try {
            if ((taskListDataList != null && !taskListDataList.isEmpty())) {
                madapter = new Book_Adapter(taskListDataList,getActivity(),Standard_id);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                recycler.setLayoutManager(layoutManager);
                recycler.setAdapter(madapter);
            } else {
                madapter = new Book_Adapter(taskListDataList,getActivity(),Standard_id);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                recycler.setLayoutManager(layoutManager);
                recycler.setAdapter(madapter);
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
