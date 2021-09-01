package com.efficaciousIndia.EsmartDemo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.efficaciousIndia.EsmartDemo.R;
import com.efficaciousIndia.EsmartDemo.entity.CategoryDetailLibPojo;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryListHolder> {
    Context context;
    String DaySelected;
    OptionDataClick optionDataClick;
    String SelectedPosition = "0";
    private List<CategoryDetailLibPojo.LibraryDetail> OptionHeading;

    public CategoryAdapter(Context context, List<CategoryDetailLibPojo.LibraryDetail> OptionHeading) {
        this.context = context;
        this.OptionHeading = OptionHeading;

    }

    public void setOnClickListener(OptionDataClick optionDataClick) {
        this.optionDataClick = optionDataClick;
    }

    @Override
    public CategoryListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.category_adapter, parent, false);
        return new CategoryListHolder(view);
    }

    @Override
    public void onBindViewHolder(final CategoryListHolder holder, final int position) {
        try {
            holder.name_tv.setText(OptionHeading.get(position).getVchCategoryName());
            holder.category_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SelectedPosition = String.valueOf(position);
                    optionDataClick.onOptionDataClick(OptionHeading.get(position).getIntCategoryId());
                    notifyDataSetChanged();
                }
            });
            if (SelectedPosition.contentEquals(String.valueOf(position))) {
                holder.name_tv.setTextColor(context.getResources().getColor(R.color.white));
                holder.category_card.setCardBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));

            } else {
                holder.name_tv.setTextColor(context.getResources().getColor(R.color.black));
                holder.category_card.setCardBackgroundColor(context.getResources().getColor(R.color.light_gray));
            }
        } catch (Exception e) {
            e.getMessage();
        }

    }

    @Override
    public int getItemCount() {
        return OptionHeading.size();
    }

    public interface OptionDataClick {
        void onOptionDataClick(int Position);
    }

    class CategoryListHolder extends RecyclerView.ViewHolder {
        CardView category_card;
        TextView name_tv;

        public CategoryListHolder(@NonNull View itemView) {
            super(itemView);
            name_tv = itemView.findViewById(R.id.name_tv);
            category_card = itemView.findViewById(R.id.category_card);

        }


    }
}