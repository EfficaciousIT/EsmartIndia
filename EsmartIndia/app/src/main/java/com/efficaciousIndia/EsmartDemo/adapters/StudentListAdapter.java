package com.efficaciousIndia.EsmartDemo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.efficaciousIndia.EsmartDemo.R;
import com.efficaciousIndia.EsmartDemo.entity.DashboardDetail;

import java.util.ArrayList;


public class StudentListAdapter  extends RecyclerView.Adapter<StudentListAdapter.StudentListHolder> {

    private ArrayList<DashboardDetail> dataList;
    private final Context mcontext;

    public StudentListAdapter(ArrayList<DashboardDetail> dataList, Context context) {
        this.dataList = dataList;
        this.mcontext = context;
    }

    @Override
    public StudentListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.studentlist_row, parent, false);
        return new StudentListHolder(view);
    }

    @Override
    public void onBindViewHolder(final StudentListHolder holder, final int position) {
        try {
            holder.Standard.setText(dataList.get(position).getStandard());
            holder.Division.setText(dataList.get(position).getDivision());
            holder.Male.setText(String.valueOf(dataList.get(position).getMale()));
            holder.Female.setText(String.valueOf(dataList.get(position).getFemale()));
            holder.Total.setText(String.valueOf(dataList.get(position).getTotal()));
        } catch (Exception ex) {

        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class StudentListHolder extends RecyclerView.ViewHolder {

        TextView Standard,Division,Male,Female,Total;
        StudentListHolder(View itemView) {
            super(itemView);
             Standard = (TextView) itemView.findViewById(R.id.Std_studentlistrow);
             Division = (TextView) itemView.findViewById(R.id.div_studentlistrow);
             Male = (TextView) itemView.findViewById(R.id.male_studentlistrow);
             Female = (TextView) itemView.findViewById(R.id.female_studentlistrow);
             Total = (TextView) itemView.findViewById(R.id.total_studentlistrow);
        }


    }

}

