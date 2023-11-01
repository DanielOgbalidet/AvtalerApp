package com.example.mappe2_s364536;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class VennerDataKilde {
    private SQLiteDatabase database;
    private DatabaseHjelper dbHelper;

    public VennerDataKilde(Context context) {
        dbHelper = new DatabaseHjelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Venner leggInnVenner(String vennerNavn, String vennerTlf) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHjelper.KOLONNE_VENNER_NAVN, vennerNavn);
        values.put(DatabaseHjelper.KOLONNE_VENNER_TLF, vennerTlf);
        long insertId = database.insert(DatabaseHjelper.TABELL_VENNER, null,
                values);
        Cursor cursor = database.query(DatabaseHjelper.TABELL_VENNER, null,
                DatabaseHjelper.KOLONNE_VENNER_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        Venner nyVenn = cursorTilVenner(cursor);
        cursor.close();
        return nyVenn;
    }

    private Venner cursorTilVenner(Cursor cursor) {
        Venner venn = new Venner();
        venn.setVennerId(cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHjelper.KOLONNE_VENNER_ID)));
        venn.setNavn(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHjelper.KOLONNE_VENNER_NAVN)));
        venn.setTlf(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHjelper.KOLONNE_VENNER_TLF)));
        return venn;
    }

    public List<Venner> finnAlleVenner() {
        List<Venner> venner = new ArrayList<>();
        Cursor cursor = database.query(DatabaseHjelper.TABELL_VENNER, null,
                null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Venner venn = cursorTilVenner(cursor);
            venner.add(venn);
            cursor.moveToNext();
        }
        cursor.close();
        return venner;
    }

    public void slettVenn(String id) {
        database.delete(DatabaseHjelper.TABELL_VENNER,
                DatabaseHjelper.KOLONNE_VENNER_ID + " =? ", new
                        String[]{id});
    }
}
