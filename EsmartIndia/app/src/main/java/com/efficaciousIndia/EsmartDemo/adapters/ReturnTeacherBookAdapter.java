package com.efficaciousIndia.EsmartDemo.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.efficaciousIndia.EsmartDemo.R;
import com.efficaciousIndia.EsmartDemo.entity.TeacherLibDetailPojo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Rahul on 28,May,2020
 */
public class ReturnTeacherBookAdapter extends RecyclerView.Adapter<ReturnTeacherBookAdapter.ViewHolder> {
    private ArrayList<TeacherLibDetailPojo.LibraryDetail> data;
    Context mcontext;
    String Standard_id;
    Date date1,date2;
    public ReturnTeacherBookAdapter(ArrayList<TeacherLibDetailPojo.LibraryDetail> dataList, Context context, String Pagename) {
        data = dataList;
        mcontext=context;
        this.Standard_id=Standard_id;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.return_book_adapter, parent, false);
        return new ViewHolder(view);
    }


    public void onBindViewHolder(final ViewHolder holder, int position) {
        try
        {
            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String currendate = df.format(c);

            holder.name_tv.setText(data.get(position).getIntTeacherId());
            holder.roll_tv.setVisibility(View.GONE);
            holder.std_tv.setVisibility(View.GONE);
            holder.sub_tv.setText(data.get(position).getVchCategoryName());
            holder.book_tv.setText(data.get(position).getVchBookLanguageName());
            holder.issuedate_tv.setText("Issue: "+data.get(position).getDtAssignedDate());
            holder.enddate_tv.setText("Ex.Return: "+data.get(position).getDtExpectedReturnedDate());
            holder.accession_tv.setText("Accession No.: "+data.get(position).getIntBookDetailsId());
            if(TextUtils.isEmpty(data.get(position).getDtReturnDate())){
                holder.actual_return_tv.setVisibility(View.GONE);
            }else {
                holder.actual_return_tv.setVisibility(View.VISIBLE);
                holder.actual_return_tv.setText("A.Return: "+data.get(position).getDtReturnDate());
            }

            if(data.get(position).getVchStatus().contentEquals("Returned")){
                Glide.with(mcontext)
                        .load(R.drawable.alreadyreturn)
                        .error(R.mipmap.profile)
                        .into(holder.status_img);
            }else
            {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    date1 = sdf.parse(currendate);
                    date2 = sdf.parse(dateinWordConversion(data.get(position).getDtExpectedReturnedDate()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Calendar cal1 = Calendar.getInstance();
                Calendar cal2 = Calendar.getInstance();
                cal1.setTime(date1);
                cal2.setTime(date2);

                if(cal1.after(cal2)){
                    System.out.println("Date1 is after Date2");
                    Glide.with(mcontext)
                            .load(R.drawable.overdue)
                            .error(R.mipmap.profile)
                            .into(holder.status_img);
                }

                if(cal1.before(cal2)){
                    System.out.println("Date1 is before Date2");
                    Glide.with(mcontext)
                            .load(R.drawable.upcoming)
                            .error(R.mipmap.profile)
                            .into(holder.status_img);
                }

                if(cal1.equals(cal2)){
                    System.out.println("Date1 is equal Date2");
                    Glide.with(mcontext)
                            .load(R.drawable.currentduedate)
                            .error(R.mipmap.profile)
                            .into(holder.status_img);
                }
            }
        }catch (Exception ex)
        {

        }

    }
    public String dateinWordConversion(String givenDateString){
        String formattedDate = "";
        DateFormat originalFormat = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
        DateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;

        try {
            date = originalFormat.parse(givenDateString);
            formattedDate = targetFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  formattedDate;
    }
    @Override
    public int getItemCount() {
        return data.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name_tv,roll_tv,std_tv,sub_tv,book_tv,issuedate_tv,enddate_tv,accession_tv,actual_return_tv;
        ImageView status_img;
        public ViewHolder(View itemView) {
            super(itemView);
            name_tv=(TextView)itemView.findViewById(R.id.name_tv);
            roll_tv=(TextView)itemView.findViewById(R.id.roll_tv);
            std_tv=(TextView)itemView.findViewById(R.id.std_tv);
            sub_tv=(TextView)itemView.findViewById(R.id.sub_tv);
            book_tv=(TextView)itemView.findViewById(R.id.book_tv);
            issuedate_tv=(TextView)itemView.findViewById(R.id.issuedate_tv);
            enddate_tv=(TextView)itemView.findViewById(R.id.enddate_tv);
            accession_tv=(TextView)itemView.findViewById(R.id.accession_tv);
            actual_return_tv=(TextView)itemView.findViewById(R.id.actual_return_tv);
            status_img=(ImageView) itemView.findViewById(R.id.status_img);
        }
    }
}


