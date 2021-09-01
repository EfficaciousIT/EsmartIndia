package com.efficaciousIndia.EsmartDemo.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.efficaciousIndia.EsmartDemo.Interface.DataService;
import com.efficaciousIndia.EsmartDemo.R;
import com.efficaciousIndia.EsmartDemo.entity.OnlineClassDetailsPojo;
import com.efficaciousIndia.EsmartDemo.entity.OnlineClassTimetablePojo;
import com.efficaciousIndia.EsmartDemo.webApi.RetrofitInstance;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class OnlineClassTimetableAdapter extends RecyclerView.Adapter<OnlineClassTimetableAdapter.ViewHolder> {
    private ArrayList<OnlineClassTimetablePojo.OnlineTimetable> data;
    Context context;
    AlertDialog alertDialog;
    private static final String PREFRENCES_NAME = "myprefrences";
    SharedPreferences settings;
    String school_id, academic_id;
    TextView tv_meeting_details,tv_meeting_description,tv_meeting_id,tv_meeting_password,tv_teacher,time_tv,tv_nodata;
    public OnlineClassTimetableAdapter(ArrayList<OnlineClassTimetablePojo.OnlineTimetable> dataList, Context context) {
        data = dataList;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView= LayoutInflater.from(parent.getContext()).inflate(R.layout.onlne_class_timetable_row,null);
        ViewHolder viewHolder=new ViewHolder(itemLayoutView);
        return viewHolder;
    }



    public void onBindViewHolder(final ViewHolder holder, int position) {
        try
        {
            holder.PeriodTime.setText(timeFormat(data.get(position).getDtFromTime().toString())+" - "+timeFormat(data.get(position).getDtToTime().toString()));
            holder.Teacher.setText(data.get(position).getTeacherName().toString());
            holder.pidtv.setText(data.get(position).getVchLectureName().toString());
            holder.subject.setText(data.get(position).getVchSubjectName().toString());

            holder.view_detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    WelcomeDialog(data.get(position).getIntOnlinelectureId().toString());
                }
            });
        }catch (Exception ex)
        {

        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView pidtv,PeriodTime,Teacher,subject;
        CircleImageView view_detail;
        public ViewHolder(View itemView) {
            super(itemView);
            pidtv=(TextView)itemView.findViewById(R.id.pidtv);
            PeriodTime=(TextView)itemView.findViewById(R.id.PeriodTime);
            Teacher=(TextView)itemView.findViewById(R.id.Teacher);
            subject=(TextView)itemView.findViewById(R.id.subject);
            view_detail=(CircleImageView)itemView.findViewById(R.id.view_detail);
        }
    }
    public String timeFormat(String strtime)
    {
        SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");
        SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");
        Date _24HourDt = null;
        try {
            _24HourDt = _24HourSDF.parse(strtime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(_24HourDt);
        System.out.println(_12HourSDF.format(_24HourDt));

        return String.valueOf(_12HourSDF.format(_24HourDt));
    }
    public String setDateTimeFormatFromServer(String strdate) {
        String formattedDate = "";
        try {
            //String curerentdate = latest SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(latest Date());
            DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
            DateFormat targetFormat = new SimpleDateFormat("dd MMMM yyyy");
            Date date = null;

            try {
                date = originalFormat.parse(strdate);
                formattedDate = targetFormat.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            return formattedDate;
        }
        return formattedDate;
    }
    private void WelcomeDialog(String id) {
        try
        {
            settings = context.getSharedPreferences(PREFRENCES_NAME, Context.MODE_PRIVATE);
            school_id = settings.getString("TAG_SCHOOL_ID", "");
            academic_id = settings.getString("TAG_ACADEMIC_ID", "");


            final androidx.appcompat.app.AlertDialog.Builder dilaog =
                    new androidx.appcompat.app.AlertDialog.Builder(context, R.style.CustomDialogs);
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            final View itemView = layoutInflater.inflate(R.layout.class_detail_dialog,null);
            dilaog.setView(itemView);
            alertDialog = dilaog.create();
            tv_meeting_details=(TextView)itemView.findViewById(R.id.tv_meeting_details);
            tv_meeting_description=(TextView)itemView.findViewById(R.id.tv_meeting_description);
            tv_meeting_id=(TextView)itemView.findViewById(R.id.tv_meeting_id);
            tv_meeting_password=(TextView)itemView.findViewById(R.id.tv_meeting_password);
            tv_teacher=(TextView)itemView.findViewById(R.id.tv_teacher);
            time_tv=(TextView)itemView.findViewById(R.id.time_tv);
            tv_nodata=(TextView)itemView.findViewById(R.id.tv_nodata);
            OnlineClassDetailAsync(id);
            dilaog.setCancelable(true);
            alertDialog.setCancelable(true);

            alertDialog.show();
        }catch (Exception e)
        {
            e.getMessage();
        }

    }
    public void OnlineClassDetailAsync(String id) {
        try {
            Observable<OnlineClassDetailsPojo> call = null;
            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);

                call = service.getOnlineTTdetailById("GetOnliceClassDetails", academic_id, school_id, id);

            call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<OnlineClassDetailsPojo>() {
                @Override
                public void onSubscribe(Disposable disposable) {
                }

                @Override
                public void onNext(OnlineClassDetailsPojo body) {
                    try {
                        generateNoticeList((ArrayList<OnlineClassDetailsPojo.OnlineTimetable>) body.getOnlineTimetable());
                    } catch (Exception ex) {

                    }
                }

                @Override
                public void onError(Throwable t) {


                }

                @Override
                public void onComplete() {

                }
            });
        } catch (Exception ex) {

        }
    }

    public void generateNoticeList(ArrayList<OnlineClassDetailsPojo.OnlineTimetable> taskListDataList) {
        try {
            if ((taskListDataList != null && !taskListDataList.isEmpty())) {
                tv_nodata.setVisibility(View.GONE);
                    tv_meeting_details.setText("Subject: "+taskListDataList.get(0).getSubject().toString());
                    tv_meeting_description.setText(taskListDataList.get(0).getNotice().toString());
                    tv_meeting_id.setText("Id: "+taskListDataList.get(0).getVchMeetingId().toString());
                tv_meeting_password.setText("Password: "+taskListDataList.get(0).getVchpassword().toString());
                tv_teacher.setText("Teacher: "+taskListDataList.get(0).getTeacherName().toString());
                    time_tv.setText("Time: "+timeFormat(taskListDataList.get(0).getDtFromTime().toString())+" - "+timeFormat(taskListDataList.get(0).getDtToTime()));

            } else {
                tv_nodata.setVisibility(View.VISIBLE);
                tv_nodata.setTextColor(Color.RED);
                tv_nodata.setText("No Data Available");
//                Toast toast = Toast.makeText(context,
//                        "No Data Available",
//                        Toast.LENGTH_SHORT);
//                View toastView = toast.getView();
//                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
//                toastView.setBackgroundResource(R.drawable.no_data_available);
//                toast.show();
            }

        } catch (Exception ex) {

        }
    }
}
