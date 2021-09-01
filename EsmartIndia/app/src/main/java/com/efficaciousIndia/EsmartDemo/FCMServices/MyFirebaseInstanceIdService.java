package com.efficaciousIndia.EsmartDemo.FCMServices;


/**
 * Created by haripal on 7/25/17.
 */

public class MyFirebaseInstanceIdService {
//        extends FirebaseInstanceIdService {
//
//    private static final String TAG = "MyFirebaseIIDService";
//
//    private static final String PREFRENCES_NAME = "myprefrences";
//    SharedPreferences settings;
//    String role_id,command,User_id,school_id,Academic_id;
//    @Override
//    public void onTokenRefresh() {
//        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
//        if (refreshedToken != null) {
//            Log.d(TAG, "Refreshed token: " + refreshedToken);
//            settings = getSharedPreferences(PREFRENCES_NAME, Context.MODE_PRIVATE);
//            role_id = settings.getString("TAG_USERTYPEID", "");
//            school_id=settings.getString("TAG_SCHOOL_ID", "");
//            Academic_id= settings.getString("TAG_ACADEMIC_ID", "");
//            if(role_id.contentEquals(""))
//            {
//            }else
//            {
//                sendTokenToServer(refreshedToken);
//            }
//        }
//    }
//
//    public void sendTokenToServer(final String strToken) {
//      String  email = settings.getString("TAG_USEREMAILID", "");
//        if(role_id.contentEquals("1")||role_id.contentEquals("2"))
//        {
//            User_id= settings.getString("TAG_STUDENTID", "");
//        }else {
//            User_id = settings.getString("TAG_USERID", "");
//        }
//        switch (Integer.parseInt(role_id))
//        {
//            case 1:command="FcmTokenStudent";
//                break;
//            case  2:command="FcmTokenStudent";
//                break;
//            case 3:command="FcmTokenTeacher";
//                break;
//            case 4:command="FcmTokenStaff";
//                break;
//            case 5:command="FcmTokenAdmin";
//                break;
//            case 6:command="FcmTokenPrincipal";
//                break;
//            case 7:command="FcmTokenManager";
//                break;
//        }
//        settings.edit().putString("TAG_USERFIREBASETOKEN",strToken).apply();
//        try
//        {
//         FCMTOKENASYNC(command,strToken,email);
//        }catch (Exception ex)
//        {
//        }
//    }
//
//        public void FCMTOKENASYNC(String command,String Firebasetoken,String EMail) {
//            try {
//                DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
//                LoginDetail loginDetail=new LoginDetail(Firebasetoken,EMail, Integer.parseInt(User_id), Integer.parseInt(school_id), Integer.parseInt(Academic_id));
//                Observable<ResponseBody> call = service.FCMTokenUpdate(command,loginDetail);
//                call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ResponseBody>() {
//                    @Override
//                    public void onSubscribe(Disposable disposable) {
//                    }
//
//                    @Override
//                    public void onNext(ResponseBody body) {
//                        try {
//
//                        } catch (Exception ex) {
//                        }
//                    }
//                    @Override
//                    public void onError(Throwable t) {
//                    }
//                    @Override
//                    public void onComplete() {
//                    }
//                });
//            } catch (Exception ex) {
//            }
//    }
}