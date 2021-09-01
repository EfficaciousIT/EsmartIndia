package com.efficaciousIndia.EsmartDemo.adapters;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.efficaciousIndia.EsmartDemo.R;
import com.efficaciousIndia.EsmartDemo.entity.MonthFeeDetailsResponse;

import java.util.List;

public class MonthlyFeesDetailAdapter extends RecyclerView.Adapter<MonthlyFeesDetailAdapter.ViewHolder> {
    Context context;
    LayoutInflater inflter;
    public List<MonthFeeDetailsResponse.MonthFeeDetail> mStudentDataList;
    private int row_index = -1;
    String strRupee = "₹";

    public MonthlyFeesDetailAdapter(Context context2, List<MonthFeeDetailsResponse.MonthFeeDetail> StudentDataList) {
        this.context = context2;
        this.mStudentDataList = StudentDataList;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.monthly_payment_detail_adapter, parent, false));
    }

    public void onBindViewHolder(final ViewHolder viewHolde, final int position) {
        new Handler(this.context.getMainLooper()).post(new Runnable() {
            public void run() {
                if (!TextUtils.isEmpty(MonthlyFeesDetailAdapter.this.mStudentDataList.get(position).getConcessionAmt())) {
                    TextView textView = viewHolde.ConcessionAmt_fee;
                    textView.setText(MonthlyFeesDetailAdapter.this.strRupee + MonthlyFeesDetailAdapter.this.mStudentDataList.get(position).getConcessionAmt());
                } else {
                    viewHolde.ConcessionAmt_fee.setText("0");
                }
                if (!TextUtils.isEmpty(MonthlyFeesDetailAdapter.this.mStudentDataList.get(position).getVchfee())) {
                    TextView textView2 = viewHolde.tution_fee;
                    textView2.setText(MonthlyFeesDetailAdapter.this.strRupee + MonthlyFeesDetailAdapter.this.mStudentDataList.get(position).getVchfee());
                } else {
                    viewHolde.tution_fee.setText("0");
                }
                if (!TextUtils.isEmpty(MonthlyFeesDetailAdapter.this.mStudentDataList.get(position).getFeeName())) {
                    viewHolde.fee_name_txt.setText(MonthlyFeesDetailAdapter.this.mStudentDataList.get(position).getFeeName());
                } else {
                    viewHolde.fee_name_txt.setText("0");
                }
                if (!TextUtils.isEmpty(MonthlyFeesDetailAdapter.this.mStudentDataList.get(position).getLateFee())) {
                    TextView textView3 = viewHolde.late_fee;
                    textView3.setText(MonthlyFeesDetailAdapter.this.strRupee + MonthlyFeesDetailAdapter.this.mStudentDataList.get(position).getLateFee());
                    return;
                }
                viewHolde.late_fee.setText("0");
            }
        });
    }

    public int getItemCount() {
        return this.mStudentDataList.size();
    }

    public List<MonthFeeDetailsResponse.MonthFeeDetail> getFeeList() {
        return this.mStudentDataList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView ConcessionAmt_fee;
        TextView fee_name_txt;
        TextView late_fee;
        TextView tution_fee;

        public ViewHolder(View itemView) {
            super(itemView);
            this.fee_name_txt = (TextView) itemView.findViewById(R.id.fee_name_txt);
            this.tution_fee = (TextView) itemView.findViewById(R.id.tution_fee);
            this.late_fee = (TextView) itemView.findViewById(R.id.late_fee);
            this.ConcessionAmt_fee = (TextView) itemView.findViewById(R.id.ConcessionAmt_fee);
        }
    }
}
