package com.efficaciousIndia.EsmartDemo.fragment;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.efficaciousIndia.EsmartDemo.Interface.DataService;
import com.efficaciousIndia.EsmartDemo.R;
import com.efficaciousIndia.EsmartDemo.adapters.PaymentHistoryAdapter;
import com.efficaciousIndia.EsmartDemo.common.ConnectionDetector;
import com.efficaciousIndia.EsmartDemo.entity.PaymentHistoryResponse;
import com.efficaciousIndia.EsmartDemo.webApi.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class PaymentDetailFragment extends Fragment {
    private static final String PREFRENCES_NAME = "myprefrences";
    String Schooli_id;
    String StudentID_Number;
    String Year_id;

    /* renamed from: cd */
    ConnectionDetector f288cd;
    RecyclerView feehistory_recyclerview;
    String intDivision_id;
    String intStandard_id;
    View mView;
    List<PaymentHistoryResponse.MonthWiseFee> monthFeeDetails = new ArrayList();
    PaymentHistoryAdapter paymentHistoryAdapter;
    /* access modifiers changed from: private */
    public ProgressDialog progress;
    String role_id;
    SharedPreferences settings;
    String strRollNo;
    String userid;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fee_payment_history, (ViewGroup) null);
        this.mView = inflate;
        this.feehistory_recyclerview = (RecyclerView) inflate.findViewById(R.id.feehistory_recyclerview);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(PREFRENCES_NAME, 0);
        this.settings = sharedPreferences;
        this.Schooli_id = sharedPreferences.getString("TAG_SCHOOL_ID", "");
        this.role_id = this.settings.getString("TAG_USERTYPEID", "");
        this.userid = this.settings.getString("TAG_USERID", "");
        this.Year_id = this.settings.getString("TAG_ACADEMIC_ID", "");
        this.StudentID_Number = this.settings.getString("TAG_StudentID_Number", "");
        this.strRollNo = this.settings.getString("TAG_RollNO", "");
        try {
            this.intStandard_id = this.settings.getString("TAG_STANDERDID", "");
            this.intDivision_id = this.settings.getString("TAG_DIVISIONID", "");
        } catch (Exception e) {
        }
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        this.progress = progressDialog;
        progressDialog.setCancelable(false);
        this.progress.setCanceledOnTouchOutside(false);
        this.progress.setMessage("loading...");
        this.f288cd = new ConnectionDetector(getContext().getApplicationContext());
        PaymentDetailAsync();
        return this.mView;
    }

    public void PaymentDetailAsync() {
        try {
            ((DataService) RetrofitInstance.getRetrofitInstance().create(DataService.class)).getUnpaidPainFeeList("PaidUnpaidMonth", this.StudentID_Number, this.intStandard_id, this.userid, this.Year_id, this.Schooli_id, this.intDivision_id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<PaymentHistoryResponse>() {
                public void onSubscribe(Disposable disposable) {
                    PaymentDetailFragment.this.progress.show();
                }

                public void onNext(PaymentHistoryResponse body) {
                    try {
                        PaymentDetailFragment.this.monthFeeDetails = body.getMonthWiseFee();
                        PaymentDetailFragment.this.generateEventDetail();
                    } catch (Exception e) {
                        PaymentDetailFragment.this.progress.dismiss();
                        Toast.makeText(PaymentDetailFragment.this.getActivity(), "Response taking time seems Network issue!", 0).show();
                    }
                }

                public void onError(Throwable t) {
                    PaymentDetailFragment.this.progress.dismiss();
                    Toast.makeText(PaymentDetailFragment.this.getActivity(), "Response taking time seems Network issue!", 0).show();
                }

                public void onComplete() {
                    PaymentDetailFragment.this.progress.dismiss();
                }
            });
        } catch (Exception e) {
            this.progress.dismiss();
        }
    }

    public void generateEventDetail() {
        try {
            if (this.monthFeeDetails != null && !this.monthFeeDetails.isEmpty()) {
                this.paymentHistoryAdapter = new PaymentHistoryAdapter(getContext(), this.monthFeeDetails);
                this.feehistory_recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
                this.feehistory_recyclerview.setAdapter(this.paymentHistoryAdapter);
            }
        } catch (Exception e) {
            this.progress.dismiss();
            Toast.makeText(getActivity(), "Response taking time seems Network issue!", Toast.LENGTH_SHORT).show();
        }
    }
}
