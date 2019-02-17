package com.ambientdigitalgroup.ambchat.notification;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.net.URI;

public class MyFirebaseMessaging extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        String sented= remoteMessage.getData().get("sented");
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser!=null && sented.equals(firebaseUser.getUid())){
            //sendNotification(remoteMessage);
        }
    }

//    private void sendNotification(RemoteMessage remoteMessage) {
//        String user=remoteMessage.getData().get("user");
//        String title=remoteMessage.getData().get("title");
//        String body=remoteMessage.getData().get("body");
//        RemoteMessage.Notification notification = remoteMessage.getNotification();
//        int j= Integer.parseInt(user.replaceAll("[\\D]",""));
//        Intent intent=new Intent(this, Activity.class);
//        Bundle bundle=new Bundle();
//        bundle.putString("userid",user);
//        intent.putExtras(bundle);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent=PendingIntent.getActivities(this,j,intent,PendingIntent.FLAG_ONE_SHOT);
//        //note lại nè
//        URI defaultSound= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        NotificationCompat.Builder builder=new NotificationCompat.Builder(this)
//                .setContentTitle(title)
//                .setContentText(body)
//                .setAutoCancel(true)
//                .setSound(defaultSound)
//                .setContentIntent(pendingIntent);
//        NotificationManager notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
//        int i=0;
//        if(j>0){
//            i=j;
//        }
//        notification.notify(i,builder.build());
//
//    }
}
