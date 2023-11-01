package com.example.mappe2_s364536;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class OpprettVennActivity extends AppCompatActivity {
    EditText navn;
    EditText dato;
    private VennerDataKilde dataKilde;
    private ArrayAdapter<Venner> vennerArrayAdapter;
    private List<Venner> venner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opprett_venner);
        dataKilde = new VennerDataKilde(this);
        dataKilde.open();
        navn = findViewById(R.id.inputVennNavn);
        dato = findViewById(R.id.inputVennDato);
        vennerArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, venner);

        Button leggInn = findViewById(R.id.opprettVenn);
        leggInn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vennNavn = navn.getText().toString();
                String vennDato = dato.getText().toString();
                Venner venn = dataKilde.leggInnVenner(vennNavn, vennDato);
                if(venner != null) vennerArrayAdapter.add(venn);

                finish();
            }
        });
    }
}
