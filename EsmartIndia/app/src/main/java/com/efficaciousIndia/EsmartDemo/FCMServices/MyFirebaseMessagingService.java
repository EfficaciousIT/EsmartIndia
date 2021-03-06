package com.efficaciousIndia.EsmartDemo.FCMServices;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.efficaciousIndia.EsmartDemo.R;
import com.efficaciousIndia.EsmartDemo.activity.IndividualChat;
import com.efficaciousIndia.EsmartDemo.activity.Login_activity;
import com.efficaciousIndia.EsmartDemo.activity.MainActivity;
import com.efficaciousIndia.EsmartDemo.activity.MessageCenterActivity;
import com.efficaciousIndia.EsmartDemo.activity.NoticeboardActivity;
import com.efficaciousIndia.EsmartDemo.activity.Notifiacton;
import com.efficaciousIndia.EsmartDemo.adapters.MessageCenterAdapter;
import com.efficaciousIndia.EsmartDemo.database.Databasehelper;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by haripal on 7/25/17.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String PREFRENCES_NAME = "myprefrences";
    private static final String TAG = "MyFirebaseMsgService";
    int status = 0;
    SharedPreferences settings;
Databasehelper mydb;
    MediaPlayer mediaPlayer;
    HashMap<Object, Object> map;
    private ArrayList<HashMap<Object, Object>> dataList;
String subject,issuedate,lastdate,RecieverFCMTOken,ReceivrName,Receiver_userid,sendername,senderFCMToken,MessageDate,message1,UserType_id,GroupName;
String Standard_id,Division_id,title;
    Cursor cursor;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO(developer): Handle FCM messages here.
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        mydb=new Databasehelper(getApplicationContext(),"Notifications",null,1);
        Log.e("dataChat", remoteMessage.getData().toString());
        {
            Map<String, String> params = remoteMessage.getData();
            JSONObject object = new JSONObject(params);
            Log.e("JSON_OBJECT", object.toString());
             title = params.get("title");
            String message = params.get("message");
            MessageDate=params.get("time");
            if(title.contentEquals("Password"))
            {
                onLogout();
            }else if(title.contentEquals("NoticeBoard"))
            {
                subject=params.get("subject");
                issuedate=params.get("issueDate");
                lastdate=params.get("todate");
            }else if(title.contentEquals("Group"))
            {
                dataList = new ArrayList<HashMap<Object, Object>>();
                message1 = params.get("message");
                sendername=params.get("Sendername");
                GroupName=params.get("GroupName");
                Standard_id=params.get("Standard_id");
                Division_id=params.get("Division_id");
                if (!FirebaseChatMainApp.isChatActivityOpen()) {
                    sendNotification(title,
                            message);
                } else {

                    new Thread(new Runnable() {
                        public void run() {
                            try {
                                String SenderMessage="";
                                mydb.query("Insert into EILGroupChat(SenderMessage,ReciverMessage,SenderName,RecieverName,GroupName,MessageDate)values('"+ SenderMessage +"','" + message1 + "','"+SenderMessage+"','"+sendername+"','"+GroupName+"','" + MessageDate + "')");

                                IndividualChatAsync individualChatAsync=new IndividualChatAsync();
                                individualChatAsync.execute();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    }).start();
                }
            }
            else if(title.contentEquals("IndividualChat"))
            {
                dataList = new ArrayList<HashMap<Object, Object>>();
                message1 = params.get("message");
                RecieverFCMTOken=params.get("RecieverFCMTOken");
                ReceivrName=params.get("ReceivrName");
                Receiver_userid=params.get("userid");
                sendername=params.get("Sendername");
                senderFCMToken=params.get("senderFCMToken");
                UserType_id=params.get("UserTypeid");
                if (!FirebaseChatMainApp.isChatActivityOpen()) {
                    sendNotification(title,message);
                } else {
                    new Thread(new Runnable() {
                        public void run() {
                            try {
                                String SenderMessage="";
                                mydb.query("Insert into EILChat(ReciverMessage,ReciverId,SenderMessage,MessageDate,ReceiverUserTypeId)values('"+message1+"','"+Receiver_userid+"','"+SenderMessage+"','"+MessageDate+"','"+UserType_id+"')");
                                IndividualChatAsync individualChatAsync=new IndividualChatAsync();
                                individualChatAsync.execute();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    }).start();
                }
            }
            if(!title.contentEquals("Trafford School") && !title.contentEquals("Group")&& !title.contentEquals("Password")) {
                sendNotification("Message Center", message);
            }else if (!title.contentEquals("IndividualChat") && !title.contentEquals("Group")&& !title.contentEquals("Password"))
            {
                sendNotification(title,
                        message);
            }
           /* if(!title.contentEquals("IndividualChat") && !title.contentEquals("Group"))
            {
                sendNotification(title,
                        message);
            }*/

        }
    }
    public static class Helper {

        public static boolean isAppRunning(final Context context, final String packageName) {
            final ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            final List<ActivityManager.RunningAppProcessInfo> procInfos = activityManager.getRunningAppProcesses();
            if (procInfos != null)
            {
                for (final ActivityManager.RunningAppProcessInfo processInfo : procInfos) {
                    if (processInfo.processName.equals(packageName)) {
                        return true;
                    }
                }
            }
            return false;
        }
    }
    public  void onLogout()
    {
        try {
            settings = getSharedPreferences(PREFRENCES_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor_delete = settings.edit();
            editor_delete.clear().commit();
            this.deleteDatabase("Notifications");
            if (Helper.isAppRunning(getApplicationContext(), "com.efficacious.Esmart")) {
                Intent intent = new Intent(getApplicationContext(), Login_activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } else {
                // App is not running
            }

        } catch (Exception ex) {
        }
    }
    /**
     * Create and show a simple notification containing the received FCM message.
     */
    private void sendNotification(String title,
                                  String message){
        Class c= MainActivity.class;
        String SenderMessage="";
        switch (title)
        {
            case "Group":c= IndividualChat.class;
                mydb.query("Insert into EILGroupChat(SenderMessage,ReciverMessage,SenderName,RecieverName,GroupName,MessageDate)values('"+ SenderMessage +"','" + message + "','"+SenderMessage+"','"+sendername+"','"+GroupName+"','" + MessageDate + "')");
                break;
            case "Message Center":c= MessageCenterActivity.class;
                 mydb.query("Insert into MessageCenter(Message,MessageDate)values('"+message+"','"+MessageDate+"')");
                 break;
            case "Attendance":c= MessageCenterActivity.class;
                mydb.query("Insert into MessageCenter(Message,MessageDate)values('"+message+"','"+MessageDate+"')");
                break;
            case "NoticeBoard":c= NoticeboardActivity.class;
                mydb.query("Insert into NoticeBoard(Subject,Notice,IssueDate,LastDate)values('"+subject+"','"+message+"','"+issuedate+"','"+lastdate+"')");
                break;
            case "Gallery":c= Notifiacton.class;
                break;
            case "HomeWork":c= Notifiacton.class;
                break;
            case "DailyDiary":c= Notifiacton.class;
                break;
            case "Event":c= Notifiacton.class;
                break;
            case "Leave Apply":c= Notifiacton.class;
                break;
            case "Leave Approved":c= Notifiacton.class;
                break;
            case "IndividualChat":c= IndividualChat.class;
                try
                {
                    mydb.query("Insert into EILChat(ReciverMessage,ReciverId,SenderMessage,MessageDate,ReceiverUserTypeId)values('"+message+"','"+Receiver_userid+"','"+SenderMessage+"','"+MessageDate+"','"+UserType_id+"')");
                }catch (Exception ex)
                {
                    ex.printStackTrace();
                }
                break;
        }
        Intent intent = new Intent(this,c);
        if(title.contentEquals("IndividualChat"))
        {
            intent.putExtra("ReceiverName", sendername);
            intent.putExtra("ReceiverId", Receiver_userid);
            intent.putExtra("ReceiverFCMToken", senderFCMToken);
            intent.putExtra("ReceiverUserTypeId",UserType_id);
            title=sendername;
        }
        if(title.contentEquals("Group"))
        {
            intent.putExtra("StandardId",Standard_id);
            intent.putExtra("DivisionId",Division_id);
            intent.putExtra("GroupName",GroupName);
            intent.putExtra("ReceiverFCMToken","Group");
            title=GroupName;
        }
        if(title.contentEquals("Gallery"))
        {
            intent.putExtra("pagename","Gallery");
        }
        if(title.contentEquals("Event"))
        {
            intent.putExtra("pagename","Event");
        }
        if(title.contentEquals("LeaveApply"))
        {
            intent.putExtra("pagename","LeaveApply");
        }
        if(title.contentEquals("Leave Approval"))
        {
            intent.putExtra("pagename","Leave Approval");
        }
        if(title.contentEquals("HomeWork"))
        {
            intent.putExtra("pagename","HomeWork");
        }
        if(title.contentEquals("DailyDiary"))
        {
            intent.putExtra("pagename","DailyDiary");
        }
        if(title.contentEquals("IndividualChat"))
        {
            intent.putExtra("pagename","IndividualChat");
        }
        if(title.contentEquals("Trafford School"))
        {
            intent.putExtra("pagename","Trafford School");
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        int notificationId = 1;
        String channelId = "channel-01";
        String channelName = "Channel Name";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);

            assert notificationManager != null;
            notificationManager.createNotificationChannel(mChannel);
        }
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), channelId)
                .setSmallIcon(R.mipmap.notification)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND)
                .setContentIntent(pendingIntent);
        TaskStackBuilder stackBuilder = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            stackBuilder = TaskStackBuilder.create(getApplicationContext());
        }
        stackBuilder.addNextIntent(intent);
        PendingIntent resultPendingIntent = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            resultPendingIntent = stackBuilder.getPendingIntent(
                    0,
                    PendingIntent.FLAG_UPDATE_CURRENT
            );
        }
        mBuilder.setContentIntent(resultPendingIntent);
        notificationManager.notify((int) System.currentTimeMillis(), mBuilder.build());
    }

    private class IndividualChatAsync extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.message);
            mediaPlayer.start();
        }
        @Override
        protected Void doInBackground(Void... params) {
            dataList.clear();
            try {
                if (title.contentEquals("Group")) {
                    cursor = mydb.querydata("Select SenderName,SenderMessage,ReciverMessage,MessageDate,RecieverName from EILGroupChat where GroupName='" + GroupName + "' order by ID desc");
//                    cursor = mydb.querydata("Select SenderName,SenderMessage,ReciverMessage,MessageDate,RecieverName from EILGroupChat where GroupName='" + GroupName + "'");
                    int count = cursor.getCount();
                }else
                {
                    cursor = mydb.querydata("Select SenderMessage,ReciverMessage,MessageDate from EILChat where ReciverId='" + Receiver_userid + "' and ReceiverUserTypeId='" + UserType_id + "' order by ID desc");
//                    cursor = mydb.querydata("Select SenderMessage,ReciverMessage,MessageDate from EILChat where ReciverId='" + Receiver_userid + "' and ReceiverUserTypeId='" + UserType_id + "'");
                    int count = cursor.getCount();
                }
                cursor.moveToFirst();
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        do {
                            map = new HashMap<Object, Object>();
                            if (title.contentEquals("Group"))
                            {
                                map.put("MessageDate", cursor.getString(cursor.getColumnIndex("MessageDate")));
                                if(!cursor.getString(cursor.getColumnIndex("SenderMessage")).contentEquals(""))
                                {
                                    map.put("SenderMessage", cursor.getString(cursor.getColumnIndex("SenderName"))+"\n"+cursor.getString(cursor.getColumnIndex("SenderMessage")));
                                }else {
                                    map.put("SenderMessage",cursor.getString(cursor.getColumnIndex("SenderMessage")));
                                }
                                if(!cursor.getString(cursor.getColumnIndex("ReciverMessage")).contentEquals("")) {
                                    map.put("Message", cursor.getString(cursor.getColumnIndex("RecieverName"))+"\n"+cursor.getString(cursor.getColumnIndex("ReciverMessage")));
                                }
                                else {
                                    map.put("Message", cursor.getString(cursor.getColumnIndex("ReciverMessage")));
                                }
                            }else
                            {
                                map.put("MessageDate", cursor.getString(cursor.getColumnIndex("MessageDate")));
                                map.put("SenderMessage", cursor.getString(cursor.getColumnIndex("SenderMessage")));
                                map.put("Message", cursor.getString(cursor.getColumnIndex("ReciverMessage")));
                            }
                            dataList.add(map);
                        } while (cursor.moveToNext());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try
            {
                IndividualChat.mrecyclerView.setHasFixedSize(true);
                IndividualChat.madapter=new MessageCenterAdapter(dataList,"IndividualChat");
                IndividualChat.mrecyclerView.setAdapter(IndividualChat.madapter);
                IndividualChat.mrecyclerView.smoothScrollToPosition(dataList.size()-1);
            }catch (Exception ex)
            {

            }

        }
    }
}