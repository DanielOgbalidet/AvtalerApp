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

import java.util.List;

public class fragmentVenner extends Fragment {
    View view;
    FloatingActionButton opprett;
    FloatingActionButton slett;
    private VennerDataKilde dataKilde;
    private ArrayAdapter<Venner> vennerArrayAdapter;
    private List<Venner> venner;
    Venner item;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_venner, container, false);
        opprett = view.findViewById(R.id.opprettVenn);
        slett = view.findViewById(R.id.slettVenn);
        ListView vennerListView = view.findViewById(R.id.listViewVenner);

        //Skriver ut liste for venner
        dataKilde = new VennerDataKilde(getActivity());
        dataKilde.open();
        venner = dataKilde.finnAlleVenner();
        vennerArrayAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, venner);
        vennerListView.setAdapter(vennerArrayAdapter);


        //Sendes videre til ny side
        opprett.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OpprettVennActivity.class);
                startActivity(intent);
            }
        });

        //Mulighet til å slette venn ved å klikke på han/hun og slett knappen etter
        vennerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                item = (Venner) vennerListView.getItemAtPosition(position);
            }
        });

        slett.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item != null) {
                    dataKilde.slettVenn(String.valueOf(item.getVennerId()));
                    Toast.makeText(getActivity(),"Du slettet " + item.getNavn(),Toast.LENGTH_SHORT).show();
                    item = null;
                } else {
                    Toast.makeText(getActivity(),"Du må trykke på en venn først for å slette den",Toast.LENGTH_SHORT).show();
                }
                vennerArrayAdapter.notifyDataSetChanged();
                onResume();
            }
        });
        return view;
    }

    //Oppdaterer listen
    @Override
    public void onResume() {
        super.onResume();
        dataKilde = new VennerDataKilde(getActivity());
        dataKilde.open();
        venner = dataKilde.finnAlleVenner();
        vennerArrayAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, venner);
        ListView vennerListView = view.findViewById(R.id.listViewVenner);
        vennerListView.setAdapter(vennerArrayAdapter);
    }
}
