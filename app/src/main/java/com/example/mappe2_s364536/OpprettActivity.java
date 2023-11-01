package com.example.mappe2_s364536;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import java.util.List;

public class OpprettActivity extends AppCompatActivity {

    EditText navn;
    Button dato;
    String datoText;
    Button klokkeslett;
    String klokkeslettText;
    EditText treffsted;
    EditText vennTlf;
    private AvtalerDataKilde dataKilde;
    private ArrayAdapter<Avtaler> avtaleArrayAdapter;
    private List<Avtaler> avtaler;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opprett_avtaler);
        dataKilde = new AvtalerDataKilde(this);
        dataKilde.open();
        navn = findViewById(R.id.inputAvtaleNavn);
        dato = findViewById(R.id.inputAvtaleDato);
        klokkeslett = findViewById(R.id.inputAvtaleKlokkeslett);
        treffsted = findViewById(R.id.inputAvtaleTreffsted);
        vennTlf = findViewById(R.id.inputAvtaleVennTlf);
        avtaleArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, avtaler);

        //Starter date og timepicker
        dato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePicker();
            }
        });

        klokkeslett.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTimePicker();
            }
        });

        //Legger til i databasen
        Button leggInn = findViewById(R.id.opprettAvtale);
        leggInn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String avtaleNavn = navn.getText().toString();
                sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                klokkeslettText = sharedPreferences.getString("klokkeslett", "06:00");
                datoText = sharedPreferences.getString("dato", "01.01.1970");
                String avtaleTreffsted = treffsted.getText().toString();
                String avtaleVennTlf = vennTlf.getText().toString();
                Avtaler avtale = dataKilde.leggInnAvtaler(avtaleNavn, datoText, klokkeslettText, avtaleTreffsted, avtaleVennTlf);
                if(avtaler != null) avtaleArrayAdapter.add(avtale);

                finish();
            }
        });
    }

    public void openTimePicker() {
        TimePickerFragment timePickerFragment = new TimePickerFragment(false);
        timePickerFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void openDatePicker() {
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.show(getSupportFragmentManager(), "datePicker");
    }
}
