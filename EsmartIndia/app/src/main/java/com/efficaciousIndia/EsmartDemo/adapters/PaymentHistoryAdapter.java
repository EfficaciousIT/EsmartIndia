package com.efficaciousIndia.EsmartDemo.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.efficaciousIndia.EsmartDemo.R;
import com.efficaciousIndia.EsmartDemo.activity.MainActivity;
import com.efficaciousIndia.EsmartDemo.entity.PaymentHistoryResponse;
import com.efficaciousIndia.EsmartDemo.fragment.Payment_Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class PaymentHistoryAdapter extends RecyclerView.Adapter<PaymentHistoryAdapter.ViewHolder> {
    Context context;
    FeeDataClick feeDataClick;
    LayoutInflater inflter;
    public List<PaymentHistoryResponse.MonthWiseFee> mStudentDataList;
    private int row_index = -1;
    String strPaid = "";
    String strRupee = "₹";

    public interface FeeDataClick {
        void onFeeClick(int i, String str);

        void onFeeClick(int i, String str, boolean z);
    }

    public PaymentHistoryAdapter(Context context2, List<PaymentHistoryResponse.MonthWiseFee> StudentDataList) {
        this.context = context2;
        this.mStudentDataList = StudentDataList;
    }

    public void setOnClickListener(FeeDataClick feeDataClick2) {
        this.feeDataClick = feeDataClick2;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fee_history_adapter, parent, false));
    }


    public void onBindViewHolder(final ViewHolder viewHolde, final int position) {
        new Handler(this.context.getMainLooper()).post(new Runnable() {
            public void run() {
                final String month_name = new SimpleDateFormat("MMM").format(Calendar.getInstance().getTime());
                PaymentHistoryResponse.MonthWiseFee monthWiseFee = PaymentHistoryAdapter.this.mStudentDataList.get(position);

                if(mStudentDataList.get(position).getMonth().substring(0, 1).contentEquals(","))
                {
                    String x = mStudentDataList.get(position).getMonth().substring(1,mStudentDataList.get(position).getMonth().length());
                    viewHolde.monthtv.setText(x);
                }else {
                    viewHolde.monthtv.setText(PaymentHistoryAdapter.this.mStudentDataList.get(position).getMonth());
                }

                TextView textView = viewHolde.amounttv;
                textView.setText(PaymentHistoryAdapter.this.strRupee + PaymentHistoryAdapter.this.mStudentDataList.get(position).getSum());
                if (PaymentHistoryAdapter.this.mStudentDataList.get(position).getStatus().contentEquals("Paid")) {
                    viewHolde.payment_complete_img.setVisibility(View.VISIBLE);
                    viewHolde.report_img.setVisibility(View.VISIBLE);
                    viewHolde.pay_btn.setVisibility(View.GONE);
                    viewHolde.pending_img.setVisibility(View.GONE);
                } else if (PaymentHistoryAdapter.this.mStudentDataList.get(position).getStatus().contentEquals("Unpaid")) {
                    viewHolde.payment_complete_img.setVisibility(View.GONE);
                    viewHolde.report_img.setVisibility(View.GONE);
                    viewHolde.pay_btn.setVisibility(View.VISIBLE);
                    viewHolde.pending_img.setVisibility(View.GONE);
                } else {
                    viewHolde.payment_complete_img.setVisibility(View.GONE);
                    viewHolde.report_img.setVisibility(View.GONE);
                    viewHolde.pay_btn.setVisibility(View.GONE);
                    viewHolde.pending_img.setVisibility(View.VISIBLE);
                }
                if (!PaymentHistoryAdapter.this.mStudentDataList.get(position).getMonth().toLowerCase().contentEquals(month_name.toLowerCase())) {
                    viewHolde.pay_btn.setTextColor(PaymentHistoryAdapter.this.context.getResources().getColor(R.color.grey_500));
                } else if (PaymentHistoryAdapter.this.mStudentDataList.get(position).getStatus().contentEquals("Unpaid")) {
                    viewHolde.pay_btn.setTextColor(PaymentHistoryAdapter.this.context.getResources().getColor(R.color.black));
                } else {
                    viewHolde.pay_btn.setTextColor(PaymentHistoryAdapter.this.context.getResources().getColor(R.color.grey_500));
                }
                viewHolde.report_img.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        if (PaymentHistoryAdapter.this.mStudentDataList.get(position).getReceiptlink() != null) {
                            PaymentHistoryAdapter.this.context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(PaymentHistoryAdapter.this.mStudentDataList.get(position).getReceiptlink().toString())));
                        }
                    }
                });
                viewHolde.pay_btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        if (PaymentHistoryAdapter.this.mStudentDataList.get(position).getMonth().toLowerCase().contentEquals(month_name.toLowerCase())) {
                            Payment_Fragment payment_fragment = new Payment_Fragment();
                            Bundle args = new Bundle();
                            args.putString("MonthSelected", PaymentHistoryAdapter.this.mStudentDataList.get(position).getMonth());
                            payment_fragment.setArguments(args);
                            MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, payment_fragment).commitAllowingStateLoss();
                        }
                    }
                });
            }
        });
    }

    public int getItemCount() {
        return this.mStudentDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView amounttv;
        TextView monthtv;
        Button pay_btn;
        ImageView payment_complete_img;
        ImageView pending_img;
        ImageView report_img;

        public ViewHolder(View itemView) {
            super(itemView);
            this.monthtv = (TextView) itemView.findViewById(R.id.monthtv);
            this.amounttv = (TextView) itemView.findViewById(R.id.amounttv);
            this.report_img = (ImageView) itemView.findViewById(R.id.result_img);
            this.payment_complete_img = (ImageView) itemView.findViewById(R.id.payment_complete_img);
            this.pending_img = (ImageView) itemView.findViewById(R.id.pending_img);
            this.pay_btn = (Button) itemView.findViewById(R.id.pay_btn);
        }
    }
}
