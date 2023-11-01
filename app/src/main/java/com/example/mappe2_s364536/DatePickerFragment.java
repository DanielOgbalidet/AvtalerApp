package com.example.mappe2_s364536;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.fragment.app.DialogFragment;
import androidx.preference.PreferenceManager;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    SharedPreferences sharedPreferences;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker.
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it.
        return new DatePickerDialog(requireContext(), this, year, month, day);
    }

    //Lagrer dato som er valgt ved oppretting av avtale
    public void onDateSet(DatePicker view, int year, int month, int day) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String date = day + "." + month + "." + year;
        sharedPreferences.edit().putString("dato", date).apply();
    }
}
