package com.example.mappe2_s364536;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class AvtalerDataKilde {
    private SQLiteDatabase database;
    private DatabaseHjelper dbHelper;

    public AvtalerDataKilde(Context context) {
        dbHelper = new DatabaseHjelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }


    public Avtaler leggInnAvtaler(String avtaleNavn, String avtaleDato, String avtaleKlokkeslett, String avtaleTreffsted, String avtaleVennTlf) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHjelper.KOLONNE_AVTALER_NAVN, avtaleNavn);
        values.put(DatabaseHjelper.KOLONNE_AVTALER_DATO, avtaleDato);
        values.put(DatabaseHjelper.KOLONNE_AVTALER_KLOKKESLETT, avtaleKlokkeslett);
        values.put(DatabaseHjelper.KOLONNE_AVTALER_TREFFSTED, avtaleTreffsted);
        values.put(DatabaseHjelper.KOLONNE_AVTALER_VENNTLF, avtaleVennTlf);
        long insertId = database.insert(DatabaseHjelper.TABELL_AVTALER, null,
                values);
        Cursor cursor = database.query(DatabaseHjelper.TABELL_AVTALER, null,
                DatabaseHjelper.KOLONNE_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        Avtaler nyAvtale = cursorTilAvtaler(cursor);
        cursor.close();
        return nyAvtale;
    }

    private Avtaler cursorTilAvtaler(Cursor cursor) {
        Avtaler avtale = new Avtaler();
        avtale.setAvtalerId(cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHjelper.KOLONNE_ID)));
        avtale.setNavn(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHjelper.KOLONNE_AVTALER_NAVN)));
        avtale.setDato(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHjelper.KOLONNE_AVTALER_DATO)));
        avtale.setKlokkeslett(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHjelper.KOLONNE_AVTALER_KLOKKESLETT)));
        avtale.setTreffsted(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHjelper.KOLONNE_AVTALER_TREFFSTED)));
        avtale.setVennTlf(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHjelper.KOLONNE_AVTALER_VENNTLF)));
        return avtale;
    }

    public List<Avtaler> finnAlleAvtaler() {
        List<Avtaler> avtaler = new ArrayList<>();
        Cursor cursor = database.query(DatabaseHjelper.TABELL_AVTALER, null,
                null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Avtaler avtale = cursorTilAvtaler(cursor);
            avtaler.add(avtale);
            cursor.moveToNext();
        }
        cursor.close();
        return avtaler;
    }

    public void slettAvtale(String id) {
        database.delete(DatabaseHjelper.TABELL_AVTALER,
                DatabaseHjelper.KOLONNE_ID + " =? ", new
                        String[]{id});
    }
}
