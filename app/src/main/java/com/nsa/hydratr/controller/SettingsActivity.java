package com.nsa.hydratr.controller;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.nsa.hydratr.R;

/**
 * Has a button that when the user selects it, it generates a notification
 * Created by c1712480 on 04/05/2018.
 */

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String CHANNEL_ID = "com.nsa.hydratr.controller.typell";
    private Button buttonSend;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        buttonSend = findViewById(R.id.buttonSend);
        buttonSend.setOnClickListener(this);
    }

    /**
     * When the send notification button is pressed a notification is created and appears in the phones
     * notification channel. If the phone is api 26 or over, then a constructed notification channel
     * is used.
     * @param view
     */
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch(id){
            case R.id.buttonSend:
                NotificationManager nManager  = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
                if(nManager == null){
                    Toast.makeText(this, "Notification cannot be posted", Toast.LENGTH_SHORT).show();
                }else{
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                        constructNotificationChannel(nManager);
                    }
                    Notification notification = constructNotification();
                    nManager.notify(1, notification);
                }
                break;
            default:
                break;
        }
    }

    /**
     * Sets an intent so that when the user taps on the notification, they are taken to the form activity.
     * It then constructs the notification layout and adds the relevant information to it.
     * @return Notification object
     */
    private Notification constructNotification(){

        Intent appIntent = new Intent(this, WaterFormActivity.class);
        PendingIntent openAppIntent = PendingIntent.getActivity(this, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Reminder")
                .setContentText("Update services availability")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Update the services that are available in Welsh in your pharmacy"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(NotificationCompat.CATEGORY_REMINDER)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setContentIntent(openAppIntent)
                .setAutoCancel(true);

        return mBuilder.build();
    }

    /**
     * Constructs a notification channel for use in phones running api 26 and over
     * @param notificationManager
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void constructNotificationChannel(NotificationManager notificationManager){
        if (notificationManager.getNotificationChannel(CHANNEL_ID) == null) {
            CharSequence name = "channel";
            String description = "Channel for high apis";

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(description);

            notificationManager.createNotificationChannel(channel);
        }
    }
}
