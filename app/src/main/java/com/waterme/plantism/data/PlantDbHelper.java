package com.waterme.plantism.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by zhuoran on 3/9/18.
 */

public class PlantDbHelper extends SQLiteOpenHelper {

    public static final String TAG = "DatabaseHelper";
    public static final String DATABASE_NAME = "plant.db";
    private static final int DATABASE_VERSION = 1;

    /**
     * Constructor for db helper
     * @param context
     */
    public PlantDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * create table sql
     * @param sqLiteDatabase
     */
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d(TAG, "CREATE database!");
        final String SQL_CREATE_PLANT_TABLE = "CREATE TABLE "
                + PlantContract.PlantEntry.TABLE_NAME + " ("
                + PlantContract.PlantEntry.COLUMN_DATE + " REAL NOT NULL, "
                + PlantContract.PlantEntry.COLUMN_TEMP + " REAL NOT NULL, "
                + PlantContract.PlantEntry.COLUMN_HUMIDITY + " REAL NOT NULL, "
                + PlantContract.PlantEntry.COLUMN_PLANT + "INTEGER NOT NULL, "
                + "PRIMARY KEY (" + PlantContract.PlantEntry.COLUMN_PLANT
                + ", " + PlantContract.PlantEntry.COLUMN_DATE + "));";

        sqLiteDatabase.execSQL(SQL_CREATE_PLANT_TABLE);
    }

    /**
     * Upgrade database
     * @param sqLiteDatabase
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PlantContract.PlantEntry.TABLE_NAME);
    }
}
