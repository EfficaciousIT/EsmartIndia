package com.efficaciousIndia.EsmartDemo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.efficaciousIndia.EsmartDemo.R;
import com.efficaciousIndia.EsmartDemo.entity.BookDetailLibPojo;

import java.util.List;

/**
 * Created by Rahul on 21,May,2020
 */
public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.CategoryListHolder> {
    Context context;
    private List<BookDetailLibPojo.LibraryDetail> OptionHeading;
    String SelectedPosition = "";
    private int rotationAngle = 0;
    public BookListAdapter(Context context, List<BookDetailLibPojo.LibraryDetail> OptionHeading) {
        this.context = context;
        this.OptionHeading = OptionHeading;

    }


    @Override
    public CategoryListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.book_list_dapter, parent, false);
        return new CategoryListHolder(view);
    }

    @Override
    public void onBindViewHolder(final CategoryListHolder holder, final int position) {
        try {
            holder.bookname_tv.setText(OptionHeading.get(position).getVchBookDetailsBookName());
            holder.author_tv.setText(OptionHeading.get(position).getIntBookPublicationId());
            holder.edition_tv.setText("Edition:"+OptionHeading.get(position).getIntBookEditionId());
            holder.accession_tv.setText("Accession No.:"+OptionHeading.get(position).getVchAccessionNo());
            holder.price_tv.setText("Price:"+OptionHeading.get(position).getIntBookPrice());
            holder.book_relative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(holder.book_detail_linear.getVisibility()== View.VISIBLE){
                        OptionHeading.get(position).setOpen(false);
                        notifyDataSetChanged();
                        holder.book_detail_linear.setVisibility(View.GONE);
                    }else {
                        OptionHeading.get(position).setOpen(true);
                        notifyDataSetChanged();
                        holder.book_detail_linear.setVisibility(View.VISIBLE);
                    }

                }
            });
            if(OptionHeading.get(position).getOpen()!=null){
                if(OptionHeading.get(position).getOpen()==true){
                   holder.image.animate().rotation(180).start();
                }else {
                    holder.image.animate().rotation(0).start();
                }
            }else {
                holder.image.animate().rotation(0).start();
            }

        } catch (Exception e) {
            e.getMessage();
        }

    }

    @Override
    public int getItemCount() {
        return OptionHeading.size();
    }


    class CategoryListHolder extends RecyclerView.ViewHolder {
        LinearLayout book_detail_linear;
        RelativeLayout book_relative;
        TextView bookname_tv,author_tv,edition_tv,accession_tv,price_tv;
        ImageView image;

        public CategoryListHolder(@NonNull View itemView) {
            super(itemView);
            book_detail_linear = itemView.findViewById(R.id.book_detail_linear);
            book_relative = itemView.findViewById(R.id.book_relative);
            bookname_tv = itemView.findViewById(R.id.bookname_tv);
            author_tv = itemView.findViewById(R.id.author_tv);
            edition_tv = itemView.findViewById(R.id.edition_tv);
            accession_tv = itemView.findViewById(R.id.accession_tv);
            price_tv = itemView.findViewById(R.id.price_tv);
            image = itemView.findViewById(R.id.image);

        }


    }
}
