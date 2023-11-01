package com.example.mappe2_s364536;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.preference.PreferenceManager;

import java.util.Calendar;
import java.util.Objects;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    SharedPreferences sharedPreferences;
    boolean SMSCheck;

    public TimePickerFragment(boolean SMS) {
        this.SMSCheck = SMS;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker.
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it.
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String time = hourOfDay + " : " + minute;
        //Sjekker om det blir brukt til avtale eller SMS-tjensten
        if(SMSCheck) {
            sharedPreferences.edit().putInt("hourOfDay", hourOfDay).apply();
            sharedPreferences.edit().putInt("minute", minute).apply();

            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            boolean SMS = sharedPreferences.getBoolean("SMS", false);

            if (SMS) {
                Intent intent = new Intent();
                intent.setAction("com.example.service.MITTSIGNAL");
                getActivity().sendBroadcast(intent);
            }
        } else sharedPreferences.edit().putString("klokkeslett", time).apply();
    }
}

