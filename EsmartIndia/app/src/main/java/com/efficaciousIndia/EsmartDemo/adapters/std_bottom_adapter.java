package com.efficaciousIndia.EsmartDemo.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.efficaciousIndia.EsmartDemo.R;
import com.efficaciousIndia.EsmartDemo.entity.StandardDetail;
import com.efficaciousIndia.EsmartDemo.fragment.BookList_Fragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rahul on 18,May,2020
 */
public class std_bottom_adapter extends RecyclerView.Adapter<std_bottom_adapter.ItemListHolder> implements Filterable {
    Context context;
    List<StandardDetail> StandardDetailListDetails = new ArrayList<>();
    List<StandardDetail> StandardDetailList = new ArrayList<>();
    STdDataClick itemDataClick;
    public std_bottom_adapter(Context context, List<StandardDetail> StandardDetailListDetails, BookList_Fragment activity) {
        this.context = context;
        this.StandardDetailListDetails = StandardDetailListDetails;
        this.StandardDetailList = StandardDetailListDetails;
        this.itemDataClick = (STdDataClick) activity;
    }


    public interface STdDataClick {
        void onSTdDataClick(int id, String details);
    }

    @Override
    public ItemListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.std_bottom_adapter, parent, false);
        return new ItemListHolder(view);
    }

    @Override
    public void onBindViewHolder(final ItemListHolder holder, final int position) {
        try {
            if(!TextUtils.isEmpty(StandardDetailListDetails.get(position).getVchStandardName())){
                holder.detail_tv.setText(StandardDetailListDetails.get(position).getVchStandardName());

            }else {
                holder.detail_tv.setText("");
            }
            holder.main_cardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemDataClick.onSTdDataClick(StandardDetailListDetails.get(position).getIntStandardId(),StandardDetailListDetails.get(position).getVchStandardName());
                }
            });

        } catch (Exception e) {
            e.getMessage();
        }

    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String query = charSequence.toString();

                List<StandardDetail> filtered = new ArrayList<>();

                if (query.isEmpty()) {
                    filtered = StandardDetailList;
                } else {
                    for (StandardDetail list : StandardDetailList) {
                        if (list.getVchStandardName().toLowerCase().contains(query.toLowerCase())) {
                            filtered.add(list);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.count = filtered.size();
                results.values = filtered;
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults results) {
                StandardDetailListDetails = (List<StandardDetail>) results.values;
                notifyDataSetChanged();
            }
        };
    }
    @Override
    public int getItemCount() {
        return StandardDetailListDetails.size();
    }


    class ItemListHolder extends RecyclerView.ViewHolder {
        CardView main_cardview;
        TextView detail_tv;
        public ItemListHolder(@NonNull View itemView) {
            super(itemView);
            main_cardview = itemView.findViewById(R.id.main_cardview);
            detail_tv = itemView.findViewById(R.id.detail_tv);

        }


    }
}

