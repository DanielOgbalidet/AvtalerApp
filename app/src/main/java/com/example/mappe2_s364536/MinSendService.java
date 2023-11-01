package com.example.mappe2_s364536;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

public class MinSendService extends Service {
    private AvtalerDataKilde dataKilde;
    List<Avtaler> avtaler = new ArrayList<>();
    SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("Minservice", "Service laget");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //Lager notifikasjon og sender SMS
        NotificationManager notificationManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        Intent i = new Intent(this, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_IMMUTABLE);
        Notification notification = new NotificationCompat.Builder(this, "MinKanal")
                .setContentTitle("Husk avtaler!")
                .setContentText("Du har kommende avtaler. Gå til MineAvtaler for å sjekke disse ut!")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pIntent).build();

        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(88, notification);

        sendSMS();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("Minservice", "Service fjernet");
    }

    public void sendSMS() {
        //Finner alle avtaler og sender SMS til hvert nummer
        dataKilde = new AvtalerDataKilde(this);
        dataKilde.open();
        avtaler = dataKilde.finnAlleAvtaler();
        for (int i = 0; i < avtaler.size(); i++){
            Avtaler avtale = avtaler.get(i);
            String number = avtale.getVennTlf();
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            String message = sharedPreferences.getString("Default", "Husk at vi har en avtale");

            if(!number.isEmpty() && !message.isEmpty()) {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(number, null, message, null, null);
            }
        }
    }
}
