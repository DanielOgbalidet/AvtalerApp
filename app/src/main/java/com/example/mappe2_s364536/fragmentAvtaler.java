package com.example.mappe2_s364536;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class fragmentAvtaler extends Fragment {
    View view;
    FloatingActionButton opprett;
    FloatingActionButton slett;
    private AvtalerDataKilde dataKilde;
    private ArrayAdapter<Avtaler> avtaleArrayAdapter;
    private List<Avtaler> avtaler;
    Avtaler item;

    //Setter opp avtaler siden
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_avtaler, container, false);
        opprett = view.findViewById(R.id.opprett);
        slett = view.findViewById(R.id.slett);
        ListView avtalerListView = view.findViewById(R.id.listView);

        //Skriver ut alle avtalene i en liste
        dataKilde = new AvtalerDataKilde(getActivity());
        dataKilde.open();
        avtaler = dataKilde.finnAlleAvtaler();
        avtaleArrayAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, avtaler);
        avtalerListView.setAdapter(avtaleArrayAdapter);


        //Sender bruker til en ny aktivitet for å opprette avtale
        opprett.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OpprettActivity.class);
                startActivity(intent);
            }
        });

        //Lagrer avtale som bruker klikker på
        avtalerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                item = (Avtaler) avtalerListView.getItemAtPosition(position);
            }
        });

        //Bruker metoden over for å slette den valgte avtalen
        slett.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Hvis en avtale ikke er valgt(ikke trykket på en avtale) så får bruker beskjed om dette
                if (item != null) {
                    dataKilde.slettAvtale(item.getAvtalerId());
                    Toast.makeText(getActivity(),"Du slettet avtale: " + item.getNavn(),Toast.LENGTH_SHORT).show();
                    item = null;
                } else {
                    Toast.makeText(getActivity(),"Du må trykke på en avtale først for å slette den",Toast.LENGTH_SHORT).show();
                }
                avtaleArrayAdapter.notifyDataSetChanged();
                onResume();
            }
        });
        return view;
    }

    //Oppdaterer listen når ny avtale er satt inn
    @Override
    public void onResume() {
        super.onResume();
        dataKilde = new AvtalerDataKilde(getActivity());
        dataKilde.open();
        avtaler = dataKilde.finnAlleAvtaler();
        avtaleArrayAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, avtaler);
        ListView avtalerListView = view.findViewById(R.id.listView);
        avtalerListView.setAdapter(avtaleArrayAdapter);
    }
}
