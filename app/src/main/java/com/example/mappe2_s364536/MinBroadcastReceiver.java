package com.example.mappe2_s364536;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MinBroadcastReceiver extends BroadcastReceiver {
    public MinBroadcastReceiver(){}
    @Override
    public void onReceive(Context context, Intent intent) {
        //Fortsetter videre til min periodisk
        Intent i = new Intent(context, MinPeriodisk.class);
        context.startService(i);
    }
}
