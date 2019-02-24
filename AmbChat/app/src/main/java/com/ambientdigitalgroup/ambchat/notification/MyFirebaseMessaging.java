package com.ambientdigitalgroup.ambchat.notification;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.ambientdigitalgroup.ambchat.R;
import com.ambientdigitalgroup.ambchat.activities.StartActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.net.URI;

public class MyFirebaseMessaging extends FirebaseMessagingService {
    private static final String TAG="MyFirebaseMsgService";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        super.onMessageReceived(remoteMessage);
       /* String sented= remoteMessage.getData().get("sented");
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser!=null && sented.equals(firebaseUser.getUid())){
            //sendNotification(remoteMessage);
        }*/

        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            sendNotification(remoteMessage);
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());


        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.

    }

    private void sendNotification(RemoteMessage remoteMessage) {
        /*String user=remoteMessage.getData().get("user");
        String title=remoteMessage.getData().get("title");
        String body=remoteMessage.getData().get("body");
        RemoteMessage.Notification notification = remoteMessage.getNotification();
        int j= Integer.parseInt(user.replaceAll("[\\D]",""));
        Intent intent=new Intent(this, StartActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString("userid",user);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent=PendingIntent.getActivities(this,j,intent,PendingIntent.FLAG_ONE_SHOT);

        //note lại nè
        URI defaultSound= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(defaultSound)
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        int i=0;
        if(j>0){
            i=j;
        }
        notification.notify(i,builder.build());*/

        Intent i =new Intent(this,StartActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent= PendingIntent.getActivity(this,0 /*request code*/,i,PendingIntent.FLAG_ONE_SHOT);

        //set sound of notification
        Uri notificationSound= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notifiBuider=new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Firebase Cloud Messagin")
                .setContentText("lam j as")
                .setAutoCancel(true)
                .setSound(notificationSound)
                .setContentIntent(pendingIntent);

        NotificationManager notifiManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notifiManager.notify(0 /*id of notificatiom*/,notifiBuider.build());

    }
}
