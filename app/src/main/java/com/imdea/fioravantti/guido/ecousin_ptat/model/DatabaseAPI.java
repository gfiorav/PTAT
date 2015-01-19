package com.imdea.fioravantti.guido.ecousin_ptat.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.imdea.fioravantti.guido.ecousin_ptat.Constants;

public class DatabaseAPI extends SQLiteOpenHelper {

    private final static String DB_KEY_ID 			= "id";
    private final static String DB_KEY_TIMESTAMP    = "timestamp";
    private final static String DB_KEY_RATE         = "rate";

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE IF NOT EXISTS " + Constants.tableName + " ( " +

            DB_KEY_ID 			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DB_KEY_TIMESTAMP    + " INTEGER," +
            DB_KEY_RATE         + " INTEGER" +
            " )";


    public DatabaseAPI (Context context) {
        super(context, Constants.databaseName, null, Constants.databaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void add (BenchmarkResult b) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(DB_KEY_TIMESTAMP, b.getTimestamp());
        values.put(DB_KEY_RATE, b.getRate());

        db.insert(Constants.tableName, null, values);

        db.close();
    }
}
