package com.example.mappe2_s364536;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreference;

public class fragmentPreferanser extends PreferenceFragmentCompat {
    private static final int SEND_SMS_PERMISSION_REQUEST_CODE = 1;
    SharedPreferences sharedPreferences;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.fragment_preferanser, rootKey);

        Preference switchPref = (Preference) findPreference("SMS");
        Preference defaultSMS = (Preference) findPreference("Default");
        Preference velgTid = (Preference) findPreference("velgTid");

        //Gir mulighet for å skifte sms som blir sendt
        assert defaultSMS != null;
        defaultSMS.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(@NonNull Preference preference, Object newValue) {
                sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                sharedPreferences.getString("Default", "Husk at vi har en avtale");
                sharedPreferences.edit().putString("Default", newValue.toString()).apply();
                return true;
            }
        });

        //Switch for å ta av og på sms/notifikasjoner
        assert switchPref != null;
        switchPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(@NonNull Preference preference, Object o) {

                boolean isOn = (boolean) o;

                //Hvis den er på går den videre til broadcast
                if (isOn) {
                    if(ContextCompat.checkSelfPermission(getActivity(),
                            android.Manifest.permission.SEND_SMS)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(), new
                                        String[]{android.Manifest.permission.SEND_SMS},
                                SEND_SMS_PERMISSION_REQUEST_CODE);
                    }
                    sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                    sharedPreferences.edit().putBoolean("SMS", true).apply();
                    Intent intent = new Intent();
                    intent.setAction("com.example.service.MITTSIGNAL");
                    getActivity().sendBroadcast(intent);
                }else{
                    sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                    sharedPreferences.edit().putBoolean("SMS", false).apply();
                }

                //* Set true to update the state of the Preference with the new value!
                return true;
            }
        });

        //Kan velge når SMS/notifikasjon skal skje
        assert velgTid != null;
        velgTid.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(@NonNull Preference preference) {
                //Åpner timepickerfragment
                openTimePicker();
                return true;
            }
        });
    }

    public void openTimePicker() {
        TimePickerFragment timePickerFragment = new TimePickerFragment(true);
        timePickerFragment.show(getActivity().getSupportFragmentManager(), "timePicker");
    }
}
