package com.example.mappe2_s364536;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceManager;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.example.mappe2_s364536.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    String CHANNEL_ID = "MinKanal";
    ActivityMainBinding binding;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //Starter med avtaler siden
        loadFragment(new fragmentAvtaler());

        //Setter opp mulighet for notifikasjon
        createNotificationChannel();

        //Setter opp broadcast
        BroadcastReceiver myBroadcastReceiver = new MinBroadcastReceiver();
        IntentFilter filter = new IntentFilter("com.example.service.MITTSIGNAL");
        filter.addAction("com.example.service.MITTSIGNAL");
        this.registerReceiver(myBroadcastReceiver, filter);

        //Sjekker om SMS-tjensesten er på eller av
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean SMSCheck = sharedPreferences.getBoolean("SMS", false);

        if (SMSCheck) {
            Intent intent = new Intent();
            intent.setAction("com.example.service.MITTSIGNAL");
            sendBroadcast(intent);
        }

        //Knapper i bottom-navigation sender bruker til nye fragmenter
        binding.bottomnav.setOnItemSelectedListener(item -> {

            int itemId = item.getItemId();
            if (itemId == R.id.page_1) {
                loadFragment(new fragmentAvtaler());
            } else if (itemId == R.id.page_2) {
                loadFragment(new fragmentVenner());
            } else if (itemId == R.id.page_3) {
                loadFragment(new fragmentPreferanser());
            }
            return true;
        });
    }

    //Starter nytt fragment
    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }

    private void createNotificationChannel() {
        CharSequence name = getString(R.string.channel_name);
        String description = getString(R.string.channel_description);
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
}