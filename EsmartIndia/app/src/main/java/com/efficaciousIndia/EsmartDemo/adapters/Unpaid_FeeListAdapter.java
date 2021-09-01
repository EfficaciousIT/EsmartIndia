package com.efficaciousIndia.EsmartDemo.adapters;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.efficaciousIndia.EsmartDemo.R;
import com.efficaciousIndia.EsmartDemo.entity.UnPaidFeeListResponse;

import java.util.List;

public class Unpaid_FeeListAdapter extends RecyclerView.Adapter<Unpaid_FeeListAdapter.ViewHolder> {
    Context context;
    FeeDataClick feeDataClick;
    LayoutInflater inflter;
    public List<UnPaidFeeListResponse.MonthWiseFee> mStudentDataList;
    private int row_index = -1;
    String strMonthSelected = "";
    String strRupee = "₹";

    public interface FeeDataClick {
        void onFeeClick(int i, String str);

        void onFeeClick(int i, String str, boolean z);
    }

    public Unpaid_FeeListAdapter(Context context2, List<UnPaidFeeListResponse.MonthWiseFee> StudentDataList, String strMonthSelected2) {
        this.context = context2;
        this.mStudentDataList = StudentDataList;
        this.strMonthSelected = strMonthSelected2;
    }

    public void setOnClickListener(FeeDataClick feeDataClick2) {
        this.feeDataClick = feeDataClick2;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fee_adapter, parent, false));
    }

    public void onBindViewHolder(final ViewHolder viewHolde, final int position) {
        new Handler(this.context.getMainLooper()).post(new Runnable() {
            public void run() {
                final UnPaidFeeListResponse.MonthWiseFee objIncome = Unpaid_FeeListAdapter.this.mStudentDataList.get(position);
                viewHolde.checkBox.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) null);
                if (Unpaid_FeeListAdapter.this.strMonthSelected != null && !TextUtils.isEmpty(Unpaid_FeeListAdapter.this.strMonthSelected) && Unpaid_FeeListAdapter.this.strMonthSelected.toLowerCase().contentEquals(objIncome.getMonth().toLowerCase())) {
                    Unpaid_FeeListAdapter.this.strMonthSelected = "";
                    objIncome.setSelected(true);
                    if (Unpaid_FeeListAdapter.this.feeDataClick != null) {
                        Unpaid_FeeListAdapter.this.feeDataClick.onFeeClick(position, objIncome.getSum(), true);
                    }
                }
                viewHolde.checkBox.setChecked(objIncome.isSelected());
                viewHolde.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        objIncome.setSelected(isChecked);
                        if (Unpaid_FeeListAdapter.this.feeDataClick != null) {
                            Unpaid_FeeListAdapter.this.feeDataClick.onFeeClick(position, objIncome.getSum(), isChecked);
                        }
                    }
                });
                viewHolde.fee_detail_img.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        if (Unpaid_FeeListAdapter.this.feeDataClick != null) {
                            Unpaid_FeeListAdapter.this.feeDataClick.onFeeClick(position, objIncome.getMonth());
                        }
                    }
                });
                viewHolde.monthtv.setText(Unpaid_FeeListAdapter.this.mStudentDataList.get(position).getMonth());
                TextView textView = viewHolde.amount;
                textView.setText(Unpaid_FeeListAdapter.this.strRupee + Unpaid_FeeListAdapter.this.mStudentDataList.get(position).getSum());
            }
        });
    }

    public int getItemCount() {
        return this.mStudentDataList.size();
    }

    public List<UnPaidFeeListResponse.MonthWiseFee> getFeeList() {
        return this.mStudentDataList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView amount;
        CheckBox checkBox;
        ImageView fee_detail_img;
        TextView monthtv;

        public ViewHolder(View itemView) {
            super(itemView);
            this.monthtv = (TextView) itemView.findViewById(R.id.monthtv);
            this.amount = (TextView) itemView.findViewById(R.id.amount);
            this.checkBox = (CheckBox) itemView.findViewById(R.id.checkBox);
            this.fee_detail_img = (ImageView) itemView.findViewById(R.id.fee_detail_img);
        }
    }
}
