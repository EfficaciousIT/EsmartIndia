package com.efficaciousIndia.EsmartDemo.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.efficaciousIndia.EsmartDemo.R;
import com.efficaciousIndia.EsmartDemo.activity.LibCategoryBooklist_fragment;
import com.efficaciousIndia.EsmartDemo.entity.SubjLibraryDetail;

import java.util.ArrayList;

public class Book_Adapter extends RecyclerView.Adapter<Book_Adapter.ViewHolder> {
    private ArrayList<SubjLibraryDetail> data;
    public ArrayList<SubjLibraryDetail> orig;
    Context mcontext;
    String Standard_id;
    public Book_Adapter(ArrayList<SubjLibraryDetail> dataList, Context context, String Standard_id) {
        data = dataList;
        mcontext=context;
        this.Standard_id=Standard_id;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView= LayoutInflater.from(parent.getContext()).inflate(R.layout.book_detail_library,null);
        ViewHolder viewHolder=new ViewHolder(itemLayoutView);
        return viewHolder;
    }



    public void onBindViewHolder(final ViewHolder holder, int position) {
        try
        {
            holder.sub_nametv.setText(data.get(position).getVchBookLanguageName());
        }catch (Exception ex)
        {

        }
        holder.relativebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent=new Intent(mcontext, LibCategoryBooklist_fragment.class);
                    intent.putExtra("inBookLangId", String.valueOf(data.get(position).getIntBookLanguageId()));
                    intent.putExtra("SubjectName",data.get(position).getVchBookLanguageName());
                    intent.putExtra("Standard_id",Standard_id);
                    mcontext.startActivity(intent);
                } catch (Exception ex) {

                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView sub_nametv;
        RelativeLayout relativebook;
        public ViewHolder(View itemView) {
            super(itemView);
            sub_nametv=(TextView)itemView.findViewById(R.id.sub_nametv);
            relativebook=(RelativeLayout)itemView.findViewById(R.id.relativebook);
        }
    }
}