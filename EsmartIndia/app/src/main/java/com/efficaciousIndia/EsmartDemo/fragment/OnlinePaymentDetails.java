package com.efficaciousIndia.EsmartDemo.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.atom.mpsdklibrary.PayActivity;
import com.efficaciousIndia.EsmartDemo.Interface.DataService;
import com.efficaciousIndia.EsmartDemo.R;
import com.efficaciousIndia.EsmartDemo.Tab.Payment_tab;
import com.efficaciousIndia.EsmartDemo.activity.MainActivity;
import com.efficaciousIndia.EsmartDemo.adapters.Unpaid_FeeListAdapter;
import com.efficaciousIndia.EsmartDemo.common.ConnectionDetector;
import com.efficaciousIndia.EsmartDemo.entity.MonthFeeDetailsResponse;
import com.efficaciousIndia.EsmartDemo.entity.PaymentSuccessResponse;
import com.efficaciousIndia.EsmartDemo.entity.SupportDetailResponse;
import com.efficaciousIndia.EsmartDemo.entity.UnPaidFeeListResponse;
import com.efficaciousIndia.EsmartDemo.webApi.RetrofitInstance;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Rahul on 15,September,2020
 */
public class OnlinePaymentDetails extends Fragment {
    View mview;
    RecyclerView recyclerview;
    TextView ttamount;
    TextView btnSubmit;
    TextView phoneTv;
    TextView mail_tv;
    TextView timingtv;
    ConnectionDetector cd;
    private static final String PREFRENCES_NAME = "myprefrences";
    SharedPreferences settings;
    String Year_id, Schooli_id;
    String userid, role_id, StudentID_Number, intStandard_id, intDivision_id;
    private ProgressDialog progress;
    Unpaid_FeeListAdapter unpaid_feeListAdapter;
    List<UnPaidFeeListResponse.MonthWiseFee> taskListDataList = new ArrayList<>();
    List<UnPaidFeeListResponse.MonthWiseFee> MonthFeeList = new ArrayList<>();
    List<MonthFeeDetailsResponse.MonthFeeDetail> monthFeeDetails = new ArrayList<>();
    String strFeeTotal = "0", strRupee = "₹";
    androidx.appcompat.app.AlertDialog alertDialog;
    String CurrDateTime;
    String message = "";
    String strMonth = "";
    String strRollNo = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mview = inflater.inflate(R.layout.fragment_payment, null);
        recyclerview = (RecyclerView) mview.findViewById(R.id.recyclerview);
        ttamount = (TextView) mview.findViewById(R.id.ttamount);
        btnSubmit = (TextView) mview.findViewById(R.id.btnSubmit);
        phoneTv = (TextView) mview.findViewById(R.id.phoneTv);
        mail_tv = (TextView) mview.findViewById(R.id.mail_tv);
        timingtv = (TextView) mview.findViewById(R.id.timingtv);
        settings = getActivity().getSharedPreferences(PREFRENCES_NAME, Context.MODE_PRIVATE);
        Schooli_id = settings.getString("TAG_SCHOOL_ID", "");
        role_id = settings.getString("TAG_USERTYPEID", "");
        userid = settings.getString("TAG_USERID", "");
        Year_id = settings.getString("TAG_ACADEMIC_ID", "");
        StudentID_Number = settings.getString("TAG_StudentID_Number", "");
        strRollNo = settings.getString("TAG_RollNO", "");
        progress = new ProgressDialog(getActivity());
        progress.setCancelable(false);
        progress.setCanceledOnTouchOutside(false);
        progress.setMessage("loading...");
        cd = new ConnectionDetector(getContext().getApplicationContext());
        if (role_id.contentEquals("2") || role_id.contentEquals("1")) {
            if (!cd.isConnectingToInternet()) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setMessage("No Internet Connection");
                alert.setPositiveButton("OK", null);
                alert.show();
            } else {
                try {
                    intStandard_id = settings.getString("TAG_STANDERDID", "");
                    intDivision_id = settings.getString("TAG_DIVISIONID", "");
                } catch (Exception ex) {

                }

            }
        }
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < MonthFeeList.size(); i++) {
                    if (MonthFeeList.get(i).isSelected()) {
                        if (strMonth.contentEquals("")) {
                            strMonth = MonthFeeList.get(i).getMonth();
                        } else {
                            strMonth = strMonth + "," + MonthFeeList.get(i).getMonth();
                        }
                    } else {

                    }
                }
                if (!strMonth.contentEquals("")) {
                    onPay();
                }
            }
        });
        EventAsync();
        getSupportDetails();
        return mview;
    }

    public void EventAsync() {
        try {
            String command = "MonthWiseTotalFee";

            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            Observable<UnPaidFeeListResponse> call = service.getUnpaidFeeList(command, StudentID_Number, intStandard_id, userid, Year_id, Schooli_id);
            // Observable<UnPaidFeeListResponse> call = service.getUnpaidFeeList(command,"20200307","3","1808","2","1");
            call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<UnPaidFeeListResponse>() {
                @Override
                public void onSubscribe(Disposable disposable) {
                    progress.show();
                }

                @Override
                public void onNext(UnPaidFeeListResponse body) {
                    try {
                        MonthFeeList = body.getMonthWiseFee();
                        taskListDataList = body.getMonthWiseFee();
                        generateEventDetail();
                    } catch (Exception ex) {
                        progress.dismiss();
                        Toast.makeText(getActivity(), "Response taking time seems Network issue!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(Throwable t) {
                    progress.dismiss();
                    Toast.makeText(getActivity(), "Response taking time seems Network issue!", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onComplete() {
                    progress.dismiss();
                }
            });
        } catch (Exception ex) {
            progress.dismiss();
        }
    }

    public void generateEventDetail() {
        try {
            if ((taskListDataList != null && !taskListDataList.isEmpty())) {
//                unpaid_feeListAdapter = new Unpaid_FeeListAdapter(getContext(), taskListDataList);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                recyclerview.setLayoutManager(layoutManager);
                recyclerview.setAdapter(unpaid_feeListAdapter);
                unpaid_feeListAdapter.setOnClickListener(new Unpaid_FeeListAdapter.FeeDataClick() {
                    @Override
                    public void onFeeClick(int position, String amount, boolean isChecked) {
                        Log.e("SelectedMonth", taskListDataList.get(position).getMonth());
                        setFeeTotalCalc(position, amount, isChecked);
                    }

                    @Override
                    public void onFeeClick(int position, String month) {
                        getMonthyFeeDetails(taskListDataList.get(position).getMonth());

                    }
                });
            } else {

            }

        } catch (Exception ex) {
            progress.dismiss();
            Toast.makeText(getActivity(), "Response taking time seems Network issue!", Toast.LENGTH_SHORT).show();
        }
    }

    private void onFeeDetailDialog(List<MonthFeeDetailsResponse.MonthFeeDetail> monthFeeDetails) {
        try {
            final androidx.appcompat.app.AlertDialog.Builder dilaog =
                    new androidx.appcompat.app.AlertDialog.Builder(getContext(), R.style.CustomDialogs);
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            final View view = layoutInflater.inflate(R.layout.monthly_feedetail, null);
            dilaog.setView(view);
            alertDialog = dilaog.create();
            TextView name_tv = (TextView) view.findViewById(R.id.title);
            TextView ttamount_tv = (TextView) view.findViewById(R.id.ttamount_tv);
            TextView tution_fee = (TextView) view.findViewById(R.id.tution_fee);
            TextView late_fee = (TextView) view.findViewById(R.id.late_fee);
            TextView ConcessionAmt_fee = (TextView) view.findViewById(R.id.ConcessionAmt_fee);
            ImageView close_img = (ImageView) view.findViewById(R.id.close_img);
            name_tv.setText("Fee Details");
            ttamount_tv.setText("₹"+monthFeeDetails.get(0).getNetAmt().toString());
            tution_fee.setText("₹"+monthFeeDetails.get(0).getVchfee().toString());
            late_fee.setText("₹"+monthFeeDetails.get(0).getLateFee().toString());
            ConcessionAmt_fee.setText("₹"+monthFeeDetails.get(0).getConcessionAmt().toString());
            close_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });
            dilaog.setCancelable(true);
            alertDialog.setCancelable(true);
            alertDialog.show();
        } catch (Exception e) {
            e.getMessage();
        }

    }

    public void getMonthyFeeDetails(String month) {
        try {
            String command = "MonthFeeDetailsStatus";

            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            Observable<MonthFeeDetailsResponse> call = service.getMonthlyDetailFeeList(command, StudentID_Number, intStandard_id, userid, Year_id, Schooli_id, intDivision_id, month);
            // Observable<UnPaidFeeListResponse> call = service.getUnpaidFeeList(command,"20200307","3","1808","2","1");
            call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<MonthFeeDetailsResponse>() {
                @Override
                public void onSubscribe(Disposable disposable) {
                    progress.show();
                }

                @Override
                public void onNext(MonthFeeDetailsResponse body) {
                    try {
                        monthFeeDetails = body.getMonthFeeDetails();
                        if (monthFeeDetails.size() > 0) {
                            onFeeDetailDialog(monthFeeDetails);
                        }

                    } catch (Exception ex) {
                        progress.dismiss();
                        Toast.makeText(getActivity(), "Response taking time seems Network issue!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(Throwable t) {
                    progress.dismiss();
                    Toast.makeText(getActivity(), "Response taking time seems Network issue!", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onComplete() {
                    progress.dismiss();
                    if (monthFeeDetails.size() > 0) {

                    } else {
                        // Toast.makeText(getActivity(), "No data available", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception ex) {
            progress.dismiss();
        }
    }

    private void setFeeTotalCalc(int position, String amount, boolean isChecked) {
        double Mamount;
        MonthFeeList.get(position).setSelected(isChecked);
        if (strFeeTotal.contentEquals("0")) {
            if (isChecked == true) {
                Mamount = roundTwoDecimals(Double.parseDouble(amount));
                strFeeTotal = String.valueOf(Mamount);
            }

        } else {
            if (isChecked == true) {
                Mamount = roundTwoDecimals(Double.parseDouble(strFeeTotal)) + roundTwoDecimals(Double.parseDouble(amount));
                strFeeTotal = String.valueOf(Mamount);
            } else {
                Mamount = roundTwoDecimals(Double.parseDouble(strFeeTotal)) - roundTwoDecimals(Double.parseDouble(amount));
                strFeeTotal = String.valueOf(Mamount);
            }

        }
        ttamount.setText(strRupee + strFeeTotal);
    }

    public double roundTwoDecimals(double d) {
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        return Double.valueOf(twoDForm.format(d));
    }

    public void getSupportDetails() {
        try {
            String command = "select";

            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            Observable<SupportDetailResponse> call = service.getSupport(command, Schooli_id);
            call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<SupportDetailResponse>() {
                @Override
                public void onSubscribe(Disposable disposable) {
                    progress.show();
                }

                @Override
                public void onNext(SupportDetailResponse body) {
                    try {
                        phoneTv.setText(body.getAPKVersion().get(0).getPhoneNumber());
                        mail_tv.setText(body.getAPKVersion().get(0).getEmailID());
                        timingtv.setText("TIMING: " + body.getAPKVersion().get(0).getFromTime() + " To " + body.getAPKVersion().get(0).getToTime());

                    } catch (Exception ex) {
                        progress.dismiss();
                        Toast.makeText(getActivity(), "Response taking time seems Network issue!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(Throwable t) {
                    progress.dismiss();
                    Toast.makeText(getActivity(), "Response taking time seems Network issue!", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onComplete() {
                    progress.dismiss();
                    if (monthFeeDetails.size() > 0) {

                    } else {
                        //Toast.makeText(getActivity(), "No data available", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception ex) {
            progress.dismiss();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (data != null) {
                message = data.getStringExtra("status");
                String[] resKey = data.getStringArrayExtra("responseKeyArray");
                String[] resValue = data.getStringArrayExtra("responseValueArray");
                if (resKey != null && resValue != null) {
                    for (int i = 0; i < resKey.length; i++)
                        System.out.println(" " + i + " resKey : " + resKey[i] + " resValue : " + resValue[i]);
                }
            }
        }
        Transactiondata();
    }

    public void Transactiondata() {
        if (message.contentEquals("Transaction Successful!")) {
            InsertPaymentDetails();
        } else {
            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
            alert.setMessage(message);
            alert.setPositiveButton("OK", null);
            alert.show();
        }
    }

    public void onPay() {
        String vchAccountholder_name = settings.getString("TAG_vchAccountholder_name", "");
        String vchBankAc_no = settings.getString("TAG_vchBankAc_no", "");
        Intent newPayIntent = new Intent(getActivity(), PayActivity.class);
        newPayIntent.putExtra("merchantId", "197");
        newPayIntent.putExtra("txnscamt", "0");
        newPayIntent.putExtra("loginid", "197");
        newPayIntent.putExtra("password", "Test@123");
        newPayIntent.putExtra("prodid", "NSE");
        newPayIntent.putExtra("txncurr", "INR");
        newPayIntent.putExtra("clientcode", "007");
        if(vchBankAc_no!=null){
            newPayIntent.putExtra("custacc", vchBankAc_no);
        }else {
            newPayIntent.putExtra("custacc", "100000036600");
        }

        newPayIntent.putExtra("channelid", "INT");
        newPayIntent.putExtra("amt", strFeeTotal);
        newPayIntent.putExtra("txnid", "013");
        newPayIntent.putExtra("date", "01/10/2019 18:31:00");
        newPayIntent.putExtra("signature_request", "KEY123657234");
        newPayIntent.putExtra("signature_response", "KEYRESP123657234");
        newPayIntent.putExtra("discriminator", "All");
        newPayIntent.putExtra("isLive", false);
        if(vchAccountholder_name!=null){
            newPayIntent.putExtra("customerName", vchAccountholder_name);
        }else {
            newPayIntent.putExtra("customerName", "LMN PQR");
        }

        newPayIntent.putExtra("customerEmailID", "Test@gmail.com");
        newPayIntent.putExtra("customerMobileNo", "8087911057");
        newPayIntent.putExtra("billingAddress", "Pune");
        startActivityForResult(newPayIntent, 1);


    }

    public void InsertPaymentDetails() {
        try {
            final String[] strMessage = {""};
            String command = "PayFeeMonthWise";

            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            Observable<PaymentSuccessResponse> call = service.insertPayentDetails(command, StudentID_Number, intStandard_id, userid, Year_id, Schooli_id, strMonth, strRollNo, intDivision_id);
            call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<PaymentSuccessResponse>() {
                @Override
                public void onSubscribe(Disposable disposable) {
                    progress.show();
                }

                @Override
                public void onNext(PaymentSuccessResponse body) {
                    try {
                        strMessage[0] = body.getPayFeeDetails().get(0).getStatus().toString();
                        InserttDetails(body.getPayFeeDetails().get(0).getFeeId().toString());
                    } catch (Exception ex) {
                        progress.dismiss();
                        Toast.makeText(getActivity(), "Response taking time seems Network issue!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(Throwable t) {
                    progress.dismiss();
                    Toast.makeText(getActivity(), "Response taking time seems Network issue!", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onComplete() {
                    progress.dismiss();
                    if (strMessage[0].contentEquals("Sussess")) {
                        //  Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                    }

                }
            });
        } catch (Exception ex) {
            progress.dismiss();
        }
    }
    public void InserttDetails(String feedid) {
        try {
            String id="";
            final String[] strMessage = {""};
            String command = "UpdateTransactionID";
            if(StudentID_Number!=null){
                id=StudentID_Number;
            }else {
                id=userid;
            }
            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            Observable<PaymentSuccessResponse> call = service.insertPayentDetails(command,feedid,feedid,feedid);
            call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<PaymentSuccessResponse>() {
                @Override
                public void onSubscribe(Disposable disposable) {
                    progress.show();
                }

                @Override
                public void onNext(PaymentSuccessResponse body) {
                    try {
                        strMessage[0] = body.getPayFeeDetails().get(0).getStatus().toString();

                    } catch (Exception ex) {
                        progress.dismiss();
                        Toast.makeText(getActivity(), "Response taking time seems Network issue!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(Throwable t) {
                    progress.dismiss();
                    Toast.makeText(getActivity(), "Response taking time seems Network issue!", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onComplete() {
                    progress.dismiss();
                    if (strMessage[0].contentEquals("Sussess")) {
                        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                        Payment_tab payment_tab = new Payment_tab();
                        MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, payment_tab).commitAllowingStateLoss();


                    } else {
                        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                    }

                }
            });
        } catch (Exception ex) {
            progress.dismiss();
        }
    }
}
