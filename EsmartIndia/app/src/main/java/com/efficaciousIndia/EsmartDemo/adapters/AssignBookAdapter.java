package com.efficaciousIndia.EsmartDemo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.efficaciousIndia.EsmartDemo.R;
import com.efficaciousIndia.EsmartDemo.entity.AssignBookDetailLibPojo;

import java.util.ArrayList;

/**
 * Created by Rahul on 24,May,2020
 */
public class AssignBookAdapter extends RecyclerView.Adapter<AssignBookAdapter.ViewHolder> {
    private ArrayList<AssignBookDetailLibPojo.LibraryDetail> data;
    Context mcontext;
    String PageName;
    public AssignBookAdapter(ArrayList<AssignBookDetailLibPojo.LibraryDetail> dataList, Context context, String PageName) {
        data = dataList;
        mcontext=context;
        this.PageName=PageName;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.assign_book_adapter, parent, false);
        return new ViewHolder(view);
    }


    public void onBindViewHolder(final ViewHolder holder, int position) {
        try
        {
            holder.name_tv.setText(data.get(position).getIntStudentId());
            holder.roll_tv.setText("Roll No.:"+data.get(position).getIntRollNo());
            holder.std_tv.setText(data.get(position).getIntstandardId());
            holder.sub_tv.setText("/"+data.get(position).getVchCategoryName());
            holder.book_tv.setText(data.get(position).getVchBookLanguageName());
            holder.issuedate_tv.setText("Issue: "+data.get(position).getDtAssignedDate());
            holder.enddate_tv.setText("Return: "+data.get(position).getDtExpectedReturnedDate());
            holder.accession_tv.setText("Accession No.: "+data.get(position).getIntBookDetailsId());
        }catch (Exception ex)
        {

        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name_tv,roll_tv,std_tv,sub_tv,book_tv,issuedate_tv,enddate_tv,accession_tv;
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

        }
    }
}
