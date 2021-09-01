package com.efficaciousIndia.EsmartDemo.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.efficaciousIndia.EsmartDemo.R;
import com.efficaciousIndia.EsmartDemo.entity.OnlineClassDetailPojo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class OnlineClassDetailAdapter extends RecyclerView.Adapter<OnlineClassDetailAdapter.ViewHolder> {
    private ArrayList<OnlineClassDetailPojo.OnlineSchedule> data;
    public OnlineClassDetailAdapter(ArrayList<OnlineClassDetailPojo.OnlineSchedule> dataList) {
        data = dataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView= LayoutInflater.from(parent.getContext()).inflate(R.layout.onlne_class_detail_row,null);
        ViewHolder viewHolder=new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    public void onBindViewHolder(final ViewHolder holder, int position) {
        try
        {
            holder.tv_meeting_details.setText("Subject: "+data.get(position).getSubject().toString());
            holder.tv_meeting_description.setText(data.get(position).getNotice().toString());
            holder.tv_meeting_id.setText("Id: "+data.get(position).getVchMeetingId().toString());
            holder.tv_meeting_password.setText("Password: "+data.get(position).getVchpassword().toString());
            holder.tv_teacher.setText("Teacher: "+data.get(position).getTeacher_name().toString());
            holder.time_tv.setText("Time: "+timeFormat(data.get(position).getDtFromTime().toString())+" - "+timeFormat(data.get(position).getDtToTime()));

        }catch (Exception ex) {}

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_meeting_details,tv_meeting_description,tv_meeting_id,tv_meeting_password,tv_teacher,time_tv;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_meeting_details=(TextView)itemView.findViewById(R.id.tv_meeting_details);
            tv_meeting_description=(TextView)itemView.findViewById(R.id.tv_meeting_description);
            tv_meeting_id=(TextView)itemView.findViewById(R.id.tv_meeting_id);
            tv_meeting_password=(TextView)itemView.findViewById(R.id.tv_meeting_password);
            tv_teacher=(TextView)itemView.findViewById(R.id.tv_teacher);
            time_tv=(TextView)itemView.findViewById(R.id.time_tv);
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
}
