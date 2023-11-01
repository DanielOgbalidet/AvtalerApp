package com.example.mappe2_s364536;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHjelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAVN = "avtaler.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABELL_AVTALER = "avtaler";
    public static final String KOLONNE_ID = "id";
    public static final String KOLONNE_AVTALER_NAVN = "navn";
    public static final String KOLONNE_AVTALER_DATO = "dato";
    public static final String KOLONNE_AVTALER_KLOKKESLETT = "klokkeslett";
    public static final String KOLONNE_AVTALER_TREFFSTED = "treffsted";
    public static final String KOLONNE_AVTALER_VENNTLF = "venntlf";
    private static final String CREATE_TABLE_TASKS = "CREATE TABLE " +
            TABELL_AVTALER +
            "(" + KOLONNE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KOLONNE_AVTALER_NAVN + " TEXT NOT NULL, " +
            KOLONNE_AVTALER_DATO + " TEXT NOT NULL, " +
            KOLONNE_AVTALER_KLOKKESLETT + " TEXT NOT NULL, " +
            KOLONNE_AVTALER_TREFFSTED + " TEXT NOT NULL, " +
            KOLONNE_AVTALER_VENNTLF + " TEXT NOT NULL)";

    public static final String TABELL_VENNER = "venner";
    public static final String KOLONNE_VENNER_ID = "vennerId";
    public static final String KOLONNE_VENNER_NAVN = "navn";
    public static final String KOLONNE_VENNER_TLF = "Tlf";

    private static final String CREATE_TABLE_FRIENDS = "CREATE TABLE " +
            TABELL_VENNER +
            "(" + KOLONNE_VENNER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KOLONNE_VENNER_NAVN + " TEXT NOT NULL, " +
            KOLONNE_VENNER_TLF + " TEXT NOT NULL)";
    public DatabaseHjelper(Context context) {
        super(context, DATABASE_NAVN, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_TASKS);
        db.execSQL(CREATE_TABLE_FRIENDS);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    { onCreate(db);
    }
}
