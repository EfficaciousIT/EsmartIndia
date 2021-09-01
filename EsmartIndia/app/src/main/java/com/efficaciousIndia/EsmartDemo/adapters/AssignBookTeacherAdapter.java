package com.efficaciousIndia.EsmartDemo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.efficaciousIndia.EsmartDemo.R;
import com.efficaciousIndia.EsmartDemo.entity.TeacherLibDetailPojo;

import java.util.ArrayList;

/**
 * Created by Rahul on 27,May,2020
 */
public class AssignBookTeacherAdapter extends RecyclerView.Adapter<AssignBookTeacherAdapter.ViewHolder> {
    private ArrayList<TeacherLibDetailPojo.LibraryDetail> data;
    Context mcontext;
    String Standard_id;
    public AssignBookTeacherAdapter(ArrayList<TeacherLibDetailPojo.LibraryDetail> dataList, Context context, String Pagename) {
        data = dataList;
        mcontext=context;
        this.Standard_id=Standard_id;
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
            holder.name_tv.setText(data.get(position).getIntTeacherId());
            holder.roll_tv.setVisibility(View.GONE);
            holder.std_tv.setVisibility(View.GONE);
            holder.sub_tv.setText(data.get(position).getVchCategoryName());
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

