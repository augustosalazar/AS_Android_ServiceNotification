package com.uninorte.myappservice;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class MyService extends Service {
    private static NotificationCompat.Builder mBuilder;

    private Timer mTimer = new Timer();
    private int counter = 0;
    private static NotificationManager mNotificationManager;
    private Notification mNotification;

    private static final String TAG = "xx";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "Service Started.");
        showNotification();
        mTimer.scheduleAtFixedRate(new MyTask(), 0, 100L);
    }

    private class MyTask extends TimerTask {


        @Override
        public void run() {
            Log.i(TAG, "Timer doing work." + counter);
            try {
                counter += 1;
                updateNotification(counter);
            } catch (Throwable t) {
                Log.e("TimerTick", "Failed.", t);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mNotificationManager.cancelAll();
        if (mTimer != null) {
            mTimer.cancel();
        }
        counter = 0;
        Log.i(TAG, "Service Stopped.");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void showNotification() {
        mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Service Test")
                .setContentText("Counting");
        Intent resultIntent = new Intent(this, MainActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mNotificationManager.notify(1, mBuilder.build());
    }

    public static void updateNotification(int counter) {
        mBuilder.setContentText("Counter " + counter);
        mNotificationManager.notify(1, mBuilder.build());
    }

}
