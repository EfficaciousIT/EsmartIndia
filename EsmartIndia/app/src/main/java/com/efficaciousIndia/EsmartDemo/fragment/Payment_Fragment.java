package com.efficaciousIndia.EsmartDemo.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.atom.mpsdklibrary.PayActivity;
import com.efficaciousIndia.EsmartDemo.Interface.DataService;
import com.efficaciousIndia.EsmartDemo.R;
import com.efficaciousIndia.EsmartDemo.Tab.Payment_tab;
import com.efficaciousIndia.EsmartDemo.activity.MainActivity;
import com.efficaciousIndia.EsmartDemo.adapters.MonthlyFeesDetailAdapter;
import com.efficaciousIndia.EsmartDemo.adapters.Unpaid_FeeListAdapter;
import com.efficaciousIndia.EsmartDemo.common.ConnectionDetector;
import com.efficaciousIndia.EsmartDemo.entity.MonthFeeDetailsResponse;
import com.efficaciousIndia.EsmartDemo.entity.PaymentSuccessResponse;
import com.efficaciousIndia.EsmartDemo.entity.SupportDetailResponse;
import com.efficaciousIndia.EsmartDemo.entity.UnPaidFeeListResponse;
import com.efficaciousIndia.EsmartDemo.webApi.RetrofitInstance;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class Payment_Fragment extends Fragment {
    private static final String PREFRENCES_NAME = "myprefrences";
    String CurrDateTime;
    List<UnPaidFeeListResponse.MonthWiseFee> MonthFeeList = new ArrayList();
    String Schooli_id;
    String StudentID_Number;
    String Year_id;
    AlertDialog alertDialog;
    TextView btnSubmit;

    /* renamed from: cd */
    ConnectionDetector f289cd;
    String intDivision_id;
    String intStandard_id;
    TextView mail_tv;
    String message = "";
    List<MonthFeeDetailsResponse.MonthFeeDetail> monthFeeDetails = new ArrayList();
    View mview;
    TextView phoneTv;
    /* access modifiers changed from: private */
    public ProgressDialog progress;
    RecyclerView recyclerview;
    String role_id;
    SharedPreferences settings;
    String strFeeTotal = "0";
    String strMonth = "";
    String strMonthSelected = "";
    String strRollNo = "";
    String strRupee = "₹";
    List<UnPaidFeeListResponse.MonthWiseFee> taskListDataList = new ArrayList();
    TextView timingtv;
    TextView ttamount;
    Unpaid_FeeListAdapter unpaid_feeListAdapter;
    String userid;
    String strTransactionId="";

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_payment, (ViewGroup) null);
       mview = inflate;
        recyclerview = (RecyclerView) inflate.findViewById(R.id.recyclerview);
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
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progress = progressDialog;
        progressDialog.setCancelable(false);
        progress.setCanceledOnTouchOutside(false);
        progress.setMessage("loading...");
        try {
            if (getArguments() != null) {
                strMonthSelected = getArguments().getString("MonthSelected");
            } else {
                strMonthSelected = "";
            }
        } catch (Exception e) {
            strMonthSelected = "";
        }
        f289cd = new ConnectionDetector(getContext().getApplicationContext());
        if (role_id.contentEquals("2") || role_id.contentEquals("1")) {
            if (!f289cd.isConnectingToInternet()) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setMessage((CharSequence) "No Internet Connection");
                alert.setPositiveButton((CharSequence) "OK", (DialogInterface.OnClickListener) null);
                alert.show();
            } else {
                try {
                    intStandard_id = settings.getString("TAG_STANDERDID", "");
                    intDivision_id = settings.getString("TAG_DIVISIONID", "");
                } catch (Exception e2) {
                }
            }
        }
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                for (int i = 0; i < MonthFeeList.size(); i++) {
                    if (MonthFeeList.get(i).isSelected()) {
                        if (strMonth.contentEquals("")) {
                        strMonth = MonthFeeList.get(i).getMonth();
                        } else {

                           strMonth = strMonth + "," + MonthFeeList.get(i).getMonth();
                        }
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
            ((DataService) RetrofitInstance.getRetrofitInstance().create(DataService.class)).getUnpaidFeeList("MonthWiseTotalFee", StudentID_Number, intStandard_id, userid, Year_id, Schooli_id).
                    subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<UnPaidFeeListResponse>() {
                public void onSubscribe(Disposable disposable) {
                    progress.show();
                }

                public void onNext(UnPaidFeeListResponse body) {
                    try {
                        MonthFeeList = body.getMonthWiseFee();
                        taskListDataList = body.getMonthWiseFee();
                        generateEventDetail();
                    } catch (Exception e) {
                        progress.dismiss();
                        Toast.makeText(getActivity(), "Response taking time seems Network issue!", Toast.LENGTH_SHORT).show();
                    }
                }

                public void onError(Throwable t) {
                    progress.dismiss();
                    Toast.makeText(getActivity(), "Response taking time seems Network issue!", Toast.LENGTH_SHORT).show();
                }

                public void onComplete() {
                    progress.dismiss();
                }
            });
        } catch (Exception e) {
            progress.dismiss();
        }
    }

    public void generateEventDetail() {
        try {
            if (taskListDataList != null && !taskListDataList.isEmpty()) {
                unpaid_feeListAdapter = new Unpaid_FeeListAdapter(getContext(), taskListDataList, strMonthSelected);
                recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerview.setAdapter(unpaid_feeListAdapter);
                unpaid_feeListAdapter.setOnClickListener(new Unpaid_FeeListAdapter.FeeDataClick() {
                    public void onFeeClick(int position, String amount, boolean isChecked) {
                        Log.e("SelectedMonth", taskListDataList.get(position).getMonth());
                        setFeeTotalCalc(position, amount, isChecked);
                    }

                    public void onFeeClick(int position, String month) {
                        getMonthyFeeDetails(taskListDataList.get(position).getMonth());
                    }
                });
            }
        } catch (Exception e) {
            progress.dismiss();
            Toast.makeText(getActivity(), "Response taking time seems Network issue!", Toast.LENGTH_SHORT).show();
        }
    }

    /* access modifiers changed from: private */
    public void onFeeDetailDialog(List<MonthFeeDetailsResponse.MonthFeeDetail> monthFeeDetails2) {
        List<MonthFeeDetailsResponse.MonthFeeDetail> list = monthFeeDetails2;
        try {
            AlertDialog.Builder dilaog = new AlertDialog.Builder(getContext(), R.style.CustomDialogs);
            View view = LayoutInflater.from(getContext()).inflate(R.layout.monthly_feedetail, (ViewGroup) null);
            dilaog.setView(view);
            alertDialog = dilaog.create();
            TextView ttamount_tv = (TextView) view.findViewById(R.id.ttamount_tv);
            RecyclerView recyclerview2 = (RecyclerView) view.findViewById(R.id.recyclerview);
            MonthlyFeesDetailAdapter monthlyFeesDetailAdapter = new MonthlyFeesDetailAdapter(getContext(), list);
            recyclerview2.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerview2.setAdapter(monthlyFeesDetailAdapter);
            ImageView close_img = (ImageView) view.findViewById(R.id.close_img);
            ((TextView) view.findViewById(R.id.title)).setText("Fee Details");
            double TotalAmount = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
            for (int i = 0; i < monthFeeDetails2.size(); i++) {
                TotalAmount += Double.parseDouble(list.get(i).getNetAmt());
            }
            ttamount_tv.setText("₹" + String.format("%.2f", new Object[]{Double.valueOf(TotalAmount)}));
            close_img.setOnClickListener(new View.OnClickListener() {
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
            ((DataService) RetrofitInstance.getRetrofitInstance().create(DataService.class)).getMonthlyDetailFeeList("MonthFeeDetailsStatus", StudentID_Number, intStandard_id, userid, Year_id, Schooli_id, intDivision_id, month).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<MonthFeeDetailsResponse>() {
                public void onSubscribe(Disposable disposable) {
                    progress.show();
                }

                public void onNext(MonthFeeDetailsResponse body) {
                    try {
                        monthFeeDetails = body.getMonthFeeDetails();
                        if (monthFeeDetails.size() > 0) {
                            onFeeDetailDialog(monthFeeDetails);
                        }
                    } catch (Exception e) {
                        progress.dismiss();
                        Toast.makeText(getActivity(), "Response taking time seems Network issue!", Toast.LENGTH_SHORT).show();
                    }
                }

                public void onError(Throwable t) {
                    progress.dismiss();
                    Toast.makeText(getActivity(), "Response taking time seems Network issue!", Toast.LENGTH_SHORT).show();
                }

                public void onComplete() {
                    progress.dismiss();
                    monthFeeDetails.size();
                }
            });
        } catch (Exception e) {
            progress.dismiss();
        }
    }

    /* access modifiers changed from: private */
    public void setFeeTotalCalc(int position, String amount, boolean isChecked) {
        MonthFeeList.get(position).setSelected(isChecked);
        if (strFeeTotal.contentEquals("0")) {
            if (isChecked) {
                strFeeTotal = String.valueOf(roundTwoDecimals(Double.parseDouble(amount)));
            }
        } else if (isChecked) {
            strFeeTotal = String.valueOf(roundTwoDecimals(Double.parseDouble(strFeeTotal)) + roundTwoDecimals(Double.parseDouble(amount)));
        } else {
            strFeeTotal = String.valueOf(roundTwoDecimals(Double.parseDouble(strFeeTotal)) - roundTwoDecimals(Double.parseDouble(amount)));
        }
        TextView textView = ttamount;
        textView.setText(strRupee + strFeeTotal);
    }

    public double roundTwoDecimals(double d) {
        return Double.valueOf(new DecimalFormat("#.##").format(d)).doubleValue();
    }

    public void getSupportDetails() {
        try {
            ((DataService) RetrofitInstance.getRetrofitInstance().create(DataService.class)).getSupport("select", Schooli_id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<SupportDetailResponse>() {
                public void onSubscribe(Disposable disposable) {
                    progress.show();
                }

                public void onNext(SupportDetailResponse body) {
                    try {
                        phoneTv.setText(body.getAPKVersion().get(0).getPhoneNumber());
                        mail_tv.setText(body.getAPKVersion().get(0).getEmailID());
                        TextView textView = timingtv;
                        textView.setText("TIMING: " + body.getAPKVersion().get(0).getFromTime() + " To " + body.getAPKVersion().get(0).getToTime());
                    } catch (Exception e) {
                        progress.dismiss();
                        Toast.makeText(getActivity(), "Response taking time seems Network issue!", Toast.LENGTH_SHORT).show();
                    }
                }

                public void onError(Throwable t) {
                    progress.dismiss();
                    Toast.makeText(getActivity(), "Response taking time seems Network issue!", Toast.LENGTH_SHORT).show();
                }

                public void onComplete() {
                    progress.dismiss();
                    monthFeeDetails.size();
                }
            });
        } catch (Exception e) {
            progress.dismiss();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && data != null) {
            message = data.getStringExtra("status");
            String[] resKey = data.getStringArrayExtra("responseKeyArray");
            String[] resValue = data.getStringArrayExtra("responseValueArray");
            if (!(resKey == null || resValue == null)) {
                for (int i = 0; i < resKey.length; i++) {
                    PrintStream printStream = System.out;
                    printStream.println(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + i + " resKey : " + resKey[i] + " resValue : " + resValue[i]);
                    if(resKey[i].contentEquals("ipg_txn_id")){
                        strTransactionId=resValue[i];
                    }
                }
            }
        }
        Transactiondata();
    }

    public void Transactiondata() {
        if (message.contentEquals("Transaction Successful!")) {
            InsertPaymentDetails();
            return;
        }
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setMessage((CharSequence) message);
        alert.setPositiveButton((CharSequence) "OK", (DialogInterface.OnClickListener) null);
        alert.show();
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
            final String[] strMessage = {""};
            String command = "UpdateTransactionID";

            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            Observable<PaymentSuccessResponse> call = service.insertPayentDetails(command,feedid,userid,strTransactionId);
            call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<PaymentSuccessResponse>() {
                @Override
                public void onSubscribe(Disposable disposable) {
                    progress.show();
                }

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
