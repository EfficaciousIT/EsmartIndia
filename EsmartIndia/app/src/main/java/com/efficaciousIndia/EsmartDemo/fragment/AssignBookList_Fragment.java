package com.efficaciousIndia.EsmartDemo.fragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.efficaciousIndia.EsmartDemo.Interface.DataService;
import com.efficaciousIndia.EsmartDemo.R;
import com.efficaciousIndia.EsmartDemo.SearchableSpinner.SearchableSpinner;
import com.efficaciousIndia.EsmartDemo.adapters.AssignBookAdapter;
import com.efficaciousIndia.EsmartDemo.adapters.AssignBookTeacherAdapter;
import com.efficaciousIndia.EsmartDemo.common.ConnectionDetector;
import com.efficaciousIndia.EsmartDemo.entity.AssignBookDetailLibPojo;
import com.efficaciousIndia.EsmartDemo.entity.DeptDetailPojo;
import com.efficaciousIndia.EsmartDemo.entity.DeptOnlineTimetable;
import com.efficaciousIndia.EsmartDemo.entity.StandardDetail;
import com.efficaciousIndia.EsmartDemo.entity.StandardDetailsPojo;
import com.efficaciousIndia.EsmartDemo.entity.SubjLibraryDetail;
import com.efficaciousIndia.EsmartDemo.entity.SubjectDetailLibPojo;
import com.efficaciousIndia.EsmartDemo.entity.TeacherLibDetailPojo;
import com.efficaciousIndia.EsmartDemo.webApi.RetrofitInstance;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

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
public class AssignBookList_Fragment extends Fragment {
    View mView;
    private static final String PREFRENCES_NAME = "myprefrences";
    SharedPreferences settings;
    private ProgressDialog progress;
    String Schooli_id;
    ConnectionDetector cd;
    private CompositeDisposable mCompositeDisposable;
    String value, academic_id, role_id, userid, Standard_id="0",SubjectId="0",Dept_id="0";

    RelativeLayout statrt_date_relative,end_date_relative;
    TextView cal_startdate,end_date_tv,stdTvlabel,SubTvLabel;
    SearchableSpinner std_spinner,subj_spinner;
    Button btnserach;
    RecyclerView assign_recycler;

    String startdate="",enddate="";
    Calendar calendarStartDate;
    private boolean isEndDate = false;
    String currentdate;
//    StandardAdapter_Lib standardAdapter_lib;
//    Subject_LibAdapter subject_libAdapter;
    AssignBookAdapter madapter;
    AssignBookTeacherAdapter assignBookTeacherAdapter;
    ArrayList<StandardDetail> standardDetailArrayList=new ArrayList<>();
    ArrayList<DeptOnlineTimetable> deptDetailPojoArrayList=new ArrayList<>();
    ArrayList<SubjLibraryDetail> subjectList=new ArrayList<>();
    public  boolean isStudentSearch=true;
    public  boolean isCreate=false;
    private Calendar calendar;
    private int year, month, day;

    LinearLayout student_card,teacher_card,return_linear;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView=inflater.inflate(R.layout.assign_book_list_fragment,null);
        settings = getActivity().getSharedPreferences(PREFRENCES_NAME, Context.MODE_PRIVATE);
        Schooli_id = settings.getString("TAG_SCHOOL_ID", "");
        userid = settings.getString("TAG_USERID", "");
        role_id = settings.getString("TAG_USERTYPEID", "");
        academic_id = settings.getString("TAG_ACADEMIC_ID", "");

        statrt_date_relative=mView.findViewById(R.id.statrt_date_relative);
        SubTvLabel=mView.findViewById(R.id.textview2);
        SubTvLabel.setText("Subject");
        stdTvlabel=mView.findViewById(R.id.textview1);
        stdTvlabel.setText("Standard");
        cal_startdate=mView.findViewById(R.id.start_date_tv);
        end_date_tv=mView.findViewById(R.id.end_date_tv);
        end_date_relative=mView.findViewById(R.id.end_date_relative);
        subj_spinner=mView.findViewById(R.id.subj_spinner);
        std_spinner=mView.findViewById(R.id.std_spinner);
        btnserach=mView.findViewById(R.id.btnserach);
        assign_recycler=mView.findViewById(R.id.assign_recycler);
        student_card = (LinearLayout) mView.findViewById(R.id.student_card);
        teacher_card = (LinearLayout) mView.findViewById(R.id.teacher_card);
        return_linear = (LinearLayout) mView.findViewById(R.id.return_linear);
        return_linear.setVisibility(View.GONE);
        student_card.setBackground(getResources().getDrawable(R.drawable.rect_round_yellow));
        teacher_card.setBackground(getResources().getDrawable(R.drawable.round_rect_gray));
//        Date c = Calendar.getInstance().getTime();
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//        enddate = df.format(c);
//        startdate = df.format(c);
//        currentdate=enddate;
        progress = new ProgressDialog(getActivity());
        progress.setCancelable(false);
        progress.setCanceledOnTouchOutside(false);
        progress.setMessage("loading...");
        subj_spinner.setHint("--Select Subject--");
        std_spinner.setHint("--Select Standard--");

//
//        calendar = Calendar.getInstance();
//        year = calendar.get(Calendar.YEAR);
//        month = calendar.get(Calendar.MONTH);
//        day = calendar.get(Calendar.DAY_OF_MONTH);
//        NumberFormat f = new DecimalFormat("00");
//        startdate = ((year + "-" + (f.format(month )) + "-" + (f.format(day))));
//        cal_startdate.setText(dateinWordConversion( ((f.format(month+1)) + "-" + (f.format(01)) + "-" + year)));
//
//        enddate = ((year + "-" + (f.format(month + 1)) + "-" + (f.format(day))));
//        end_date_tv.setText(dateinWordConversion( ((f.format(month + 1)) + "-" + (f.format(day)) + "-" + year)));
        //start date on ui click
        statrt_date_relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                try
                                {
                                    NumberFormat f = new DecimalFormat("00");
                                    startdate = ((year + "-" + (f.format(monthOfYear + 1)) + "-" + (f.format(dayOfMonth))));
                                    cal_startdate.setText(dateinWordConversion( ((f.format(monthOfYear + 1)) + "-" + (f.format(dayOfMonth)) + "-" + year)));

                                }catch (Exception ex)
                                {

                                }
                            }
                        }, year, month, day);

                datePickerDialog.show();
            }
        });
        //end date on ui click
        end_date_relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                try
                                {
                                    NumberFormat f = new DecimalFormat("00");
                                    enddate = ((year + "-" + (f.format(monthOfYear + 1)) + "-" + (f.format(dayOfMonth))));
                                    end_date_tv.setText(dateinWordConversion( ((f.format(monthOfYear + 1)) + "-" + (f.format(dayOfMonth)) + "-" + year)));

                                }catch (Exception ex)
                                {

                                }
                            }
                        }, year, month, day);

                datePickerDialog.show();
            }
        });
        LoginAsync();
        btnserach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isStudentSearch){
                    AssignBookListStudentAsync();
                }else {
                    AssignBookListTeacherAsync();
                }

            }
        });
        subj_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    SubjectId= String.valueOf(subjectList.get(position).getIntBookLanguageId());

                } catch (Exception ex) {
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        std_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    if(isStudentSearch){
                        Standard_id= String.valueOf(standardDetailArrayList.get(position).getIntStandardId());
                        StudentAsync(Integer.parseInt(Standard_id));
                    }else {
                        Dept_id= String.valueOf(deptDetailPojoArrayList.get(position).getIntDepartment());
                    }

                } catch (Exception ex) {
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        student_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isStudentSearch=true;
                isCreate=false;
                Standard_id="0";
                SubjectId="0";
                subj_spinner.setHint("--Select Subject--");
                std_spinner.setHint("--Select Standard--");
                stdTvlabel.setText("Standard");
                startdate="";
                cal_startdate.setText("");
                end_date_tv.setText("");
                enddate="";
                student_card.setBackground(getResources().getDrawable(R.drawable.rect_round_yellow));
                teacher_card.setBackground(getResources().getDrawable(R.drawable.round_rect_gray));
                LoginAsync();
                assign_recycler.setVisibility(View.GONE);
            }
        });
        teacher_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isCreate=false;
                isStudentSearch=false;
                subj_spinner.setHint("--Select Subject--");
                std_spinner.setHint("--Select Department--");
                stdTvlabel.setText("Department");
                startdate="";
                cal_startdate.setText("");
                end_date_tv.setText("");
                enddate="";
                student_card.setBackground(getResources().getDrawable(R.drawable.round_rect_gray));
                teacher_card.setBackground(getResources().getDrawable(R.drawable.rect_round_yellow));
                StudentAsync(0);
                 DeptAsync();
              // AssignBookListTeacherAsync();
                assign_recycler.setVisibility(View.GONE);

            }
        });
        return mView;
    }
    public void  DeptAsync (){
        try {
            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            Observable<DeptDetailPojo> call;
                call = service.getDepartment("GetDepartmentList",Schooli_id,"3");
            call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<DeptDetailPojo>() {
                @Override
                public void onSubscribe(Disposable disposable) {
                    progress.show();
                }

                @Override
                public void onNext(DeptDetailPojo body) {
                    try {
                        try {
                            deptDetailPojoArrayList.clear();
                            DeptOnlineTimetable deptOnlineTimetable;
                            deptOnlineTimetable = new DeptOnlineTimetable(0, "--Select Department--");
                            deptDetailPojoArrayList.addAll(Collections.singleton(deptOnlineTimetable));
                        }catch (Exception ex)
                        {

                        }
                        deptDetailPojoArrayList.addAll( body.getOnlineTimetable());
                        Dept_id= String.valueOf(deptDetailPojoArrayList.get(0).getIntDepartment());
                        ArrayAdapter<DeptOnlineTimetable> occupationArrayAdapter =
                                new ArrayAdapter<DeptOnlineTimetable>(getContext(), android.R.layout.simple_spinner_item,deptDetailPojoArrayList);
                        std_spinner.setAdapter(occupationArrayAdapter);
                        StudentAsync(0);
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
    public void generateStandradByLectures(ArrayList<StandardDetail> taskListDataList) {
        try {
            try {
                standardDetailArrayList.clear();
                StandardDetail standardDetail;
                standardDetail = new StandardDetail(0, "--Select Standard--",0);
                standardDetailArrayList.addAll(Collections.singleton(standardDetail));
            }catch (Exception ex)
            {

            }
            if ((taskListDataList != null && !taskListDataList.isEmpty())) {
                Standard_id= String.valueOf(taskListDataList.get(0).getIntStandardId());
                standardDetailArrayList.addAll(taskListDataList);
                ArrayAdapter<StandardDetail> occupationArrayAdapter =
                        new ArrayAdapter<StandardDetail>(getContext(), android.R.layout.simple_spinner_item,standardDetailArrayList);
                std_spinner.setAdapter(occupationArrayAdapter);
                StudentAsync (taskListDataList.get(0).getIntStandardId());
            } else {
//                Toast toast = Toast.makeText(getActivity(),
//                        "No Data Available",
//                        Toast.LENGTH_SHORT);
//                View toastView = toast.getView();
//                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
//                toastView.setBackgroundResource(R.drawable.no_data_available);
//                toast.show();
            }

        } catch (Exception ex) {
            Toast.makeText(getActivity(), "Response taking time seems Network issue!", Toast.LENGTH_SHORT).show();
        }
    }
    public void  StudentAsync (int standard_id){
        try {
            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            Observable<SubjectDetailLibPojo> call;
            if(isStudentSearch){
                 call = service.getLibraryDetails("GetStandardWiseBookList",Schooli_id, String.valueOf(standard_id));
            }else {
                 call = service.getLibraryDetailsTeacher("GetBookList",Schooli_id);
            }

            call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<SubjectDetailLibPojo>() {
                @Override
                public void onSubscribe(Disposable disposable) {
                    progress.show();
                }

                @Override
                public void onNext(SubjectDetailLibPojo body) {
                    try {
                        generateSubList((ArrayList<SubjLibraryDetail>) body.getLibraryDetail());
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

    public void generateSubList(ArrayList<SubjLibraryDetail> taskListDataList) {
        try {
            try {
                subjectList.clear();
                SubjLibraryDetail subjLibraryDetail;
                subjLibraryDetail = new SubjLibraryDetail(0, "--Select Subject--");
                subjectList.addAll(Collections.singleton(subjLibraryDetail));
            }catch (Exception ex)
            {

            }
            if ((taskListDataList != null && !taskListDataList.isEmpty())) {
                subjectList.addAll(taskListDataList);
                SubjectId= String.valueOf(subjectList.get(0).getIntBookLanguageId());
                ArrayAdapter<SubjLibraryDetail> occupationArrayAdapter =
                        new ArrayAdapter<SubjLibraryDetail>(getContext(), android.R.layout.simple_spinner_item,subjectList);
                subj_spinner.setAdapter(occupationArrayAdapter);
                if(isCreate){
                    isCreate=false;
                    if(isStudentSearch){
                        AssignBookListStudentAsync();
                    }else {
                        AssignBookListTeacherAsync();
                    }

                }

            } else {
                subjectList.addAll(taskListDataList);
                ArrayAdapter<SubjLibraryDetail> occupationArrayAdapter =
                        new ArrayAdapter<SubjLibraryDetail>(getContext(), android.R.layout.simple_spinner_item,subjectList);
                subj_spinner.setAdapter(occupationArrayAdapter);
                if(isCreate){
                    isCreate=false;
                    if(isStudentSearch){
                        AssignBookListStudentAsync();
                    }else {
                        AssignBookListTeacherAsync();
                    }

                }
//                Toast toast = Toast.makeText(getActivity(),
//                        "No Data Available",
//                        Toast.LENGTH_SHORT);
//                View toastView = toast.getView();
//                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
//                toastView.setBackgroundResource(R.drawable.no_data_available);
//                toast.show();
            }

        } catch (Exception ex) {
            progress.dismiss();
            Toast.makeText(getActivity(), "Response taking time seems Network issue!", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mCompositeDisposable != null && !mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.dispose();
        }
    }
    public String dateinWordConversion(String givenDateString){
        String formattedDate = "";
        DateFormat originalFormat = new SimpleDateFormat("MM-dd-yyyy", Locale.ENGLISH);
        DateFormat targetFormat = new SimpleDateFormat("MMM dd yyyy");
        Date date = null;

        try {
            date = originalFormat.parse(givenDateString);
            formattedDate = targetFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  formattedDate;
    }


    public void AssignBookListStudentAsync (){
        try {
            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            Observable<AssignBookDetailLibPojo> call = service.getLibraryAsiignBookDetails("AssignBookDetails",Schooli_id,"0",Standard_id,SubjectId,startdate,enddate);
            call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<AssignBookDetailLibPojo>() {
                @Override
                public void onSubscribe(Disposable disposable) {
                    progress.show();
                }

                @Override
                public void onNext(AssignBookDetailLibPojo body) {
                    try {
                        generateAssignList((ArrayList<AssignBookDetailLibPojo.LibraryDetail>) body.getLibraryDetail());
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
    public void AssignBookListTeacherAsync (){
        try {
            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            Observable<TeacherLibDetailPojo> call = service.getLibraryAsiignBookDetailsTeacher("TeacherAssignBookDetails",Schooli_id,"0",Dept_id,SubjectId,startdate,enddate);
            call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<TeacherLibDetailPojo>() {
                @Override
                public void onSubscribe(Disposable disposable) {
                    progress.show();
                }

                @Override
                public void onNext(TeacherLibDetailPojo body) {
                    try {
                        if ((body.getLibraryDetail() != null && !body.getLibraryDetail().isEmpty())) {
                            assign_recycler.setVisibility(View.VISIBLE);
                            assignBookTeacherAdapter = new AssignBookTeacherAdapter((ArrayList<TeacherLibDetailPojo.LibraryDetail>) body.getLibraryDetail(),getActivity(),"AssignBooks");
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                            assign_recycler.setLayoutManager(layoutManager);
                            assign_recycler.setAdapter(assignBookTeacherAdapter);
                        } else {
                            assign_recycler.setVisibility(View.GONE);
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
    public void generateAssignList(ArrayList<AssignBookDetailLibPojo.LibraryDetail> taskListDataList) {
        try {
            if ((taskListDataList != null && !taskListDataList.isEmpty())) {
                assign_recycler.setVisibility(View.VISIBLE);
                madapter = new AssignBookAdapter(taskListDataList,getActivity(),"AssignBooks");
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                assign_recycler.setLayoutManager(layoutManager);
                assign_recycler.setAdapter(madapter);
            } else {
                assign_recycler.setVisibility(View.GONE);
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
