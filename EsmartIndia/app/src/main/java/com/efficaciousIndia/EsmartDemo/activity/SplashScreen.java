package com.efficaciousIndia.EsmartDemo.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.efficaciousIndia.EsmartDemo.Interface.DataService;
import com.efficaciousIndia.EsmartDemo.R;
import com.efficaciousIndia.EsmartDemo.common.ConnectionDetector;
import com.efficaciousIndia.EsmartDemo.entity.VersionDetailPojo;
import com.efficaciousIndia.EsmartDemo.webApi.RetrofitInstance;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Rahul on 25,May,2020
 */
public class SplashScreen extends AppCompatActivity {

    ConnectionDetector cd;
    private static final String PREFRENCES_NAME = "myprefrences";
    SharedPreferences settings;
    String Session_usertype_id, session_username, session_password, session_fcmtoken, session_emailid;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        cd = new ConnectionDetector(getApplicationContext());
        settings = getSharedPreferences(PREFRENCES_NAME, Context.MODE_PRIVATE);
        session_emailid = settings.getString("TAG_USEREMAILID", "");
        session_fcmtoken = settings.getString("TAG_USERFIREBASETOKEN", "");
        Session_usertype_id = settings.getString("TAG_USERTYPEID", "");
        session_username = settings.getString("TAG_USERNAME", "");
        session_password = settings.getString("TAG_PASSWORD", "");
        VersionCheckAsync();
    }

    private void checkForUpdate(String latestAppVersion) {
        if (Integer.parseInt(latestAppVersion)!=getCurrentVersionCode()) {
            new AlertDialog.Builder(this).setTitle("Please Update the App")
                    .setMessage("A new version of this app is available. Please update it").setPositiveButton(
                    "OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                            try {

                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.mobi.efficacious.TraffordSchool&hl=en")));
                                finish();
                            } catch (android.content.ActivityNotFoundException anfe) {

                            }
                        }
                    }).setCancelable(false).show();
        } else {
            if (!Session_usertype_id.contentEquals("") && !session_username.contentEquals("") && !session_password.contentEquals("")) {
            /*&&
        } !session_emailid.contentEquals("")) {*/
                if (!cd.isConnectingToInternet()) {

                    AlertDialog.Builder alert = new AlertDialog.Builder(SplashScreen.this);
                    alert.setMessage("No Internet Connection");
                    alert.setPositiveButton("OK", null);
                    alert.show();

                } else {
//                Intent HomeScreenIntent = new Intent(Login_activity.this, MainActivity.class);
                    Intent HomeScreenIntent = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(HomeScreenIntent);
                    finish();
                }
            }else {
                Intent HomeScreenIntent = new Intent(SplashScreen.this, Login_activity.class);
                startActivity(HomeScreenIntent);
                finish();
            }
            Toast.makeText(this,"This app is already upto date", Toast.LENGTH_SHORT).show();
        }
    }
    private int getCurrentVersionCode() {
        try {
            return getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }
    public void VersionCheckAsync() {
        try {
            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            Observable<VersionDetailPojo> call = service.getVersionDetails("SelectVersion","1");
            call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<VersionDetailPojo>() {
                @Override
                public void onSubscribe(Disposable disposable) {

                }
                @Override
                public void onNext(VersionDetailPojo body) {
                    try {
                        checkForUpdate(body.getAPKVersion().get(body.getAPKVersion().size()-1).getVchVersionName());
                    } catch (Exception ex) {

                        Toast.makeText(SplashScreen.this, "Response taking time seems Network issue!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(Throwable t) {
                    Intent HomeScreenIntent = new Intent(SplashScreen.this, Login_activity.class);
                    startActivity(HomeScreenIntent);
                    finish();

                }

                @Override
                public void onComplete() {

                }
            });
        } catch (Exception ex) {

        }
    }
}
