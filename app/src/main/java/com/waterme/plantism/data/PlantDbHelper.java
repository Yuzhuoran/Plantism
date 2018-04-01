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
    private static final int DATABASE_VERSION = 4;

    // two tables
    // 1 species name / genus / full intro / short intro / tips
    // 2 species name / image name

    // column names

    private static final String CREATE_TABLE_PLANT = "CREATE TABLE "
            + PlantContract.PlantEntry.TABLE_NAME + " ("
            + PlantContract.PlantEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + PlantContract.PlantEntry.COLUMN_SPECIES + " TEXT NOT NULL UNIQUE, "
            + PlantContract.PlantEntry.COLUMN_GENUES + " TEXT NOT NULL, "
            + PlantContract.PlantEntry.COLUMN_FULL_INTRO + " TEXT NOT NULL, "
            + PlantContract.PlantEntry.COLUMN_SHORT_INTRO + " TEXT NOT NULL, "
            + PlantContract.PlantEntry.COLUMN_TIP + " TEXT NOT NULL, "
            + PlantContract.PlantEntry.COLUMN_HUMIDITY_THREAD + " INTEGER DEFAULT 100, "
            + PlantContract.PlantEntry.COLUMN_TEMPERATURE_THREAD + " INTEGER DEFAULT 100, "
            + PlantContract.PlantEntry.COLUMN_IMAGE + " TEXT NOT NULL"
            + " )";


    private static final String CREATE_TABLE_IMAGE = "CREATE TABLE "
            + PlantImageContract.ImageEntry.TABLE_NAME + " ("
            + PlantImageContract.ImageEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + PlantImageContract.ImageEntry.COLUMN_SPECIES + " TEXT NOT NULL, "
            + PlantImageContract.ImageEntry.COLUMN_IMAGE + " TEXT NOT NULL "
            + " )";

    /**
     * Constructor for db helper
     * @param context
     */
    public PlantDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Create two database for the application
     * @param sqLiteDatabase
     */
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d(TAG, "CREATE database!");
        Log.d(TAG, CREATE_TABLE_IMAGE);
        Log.d(TAG, CREATE_TABLE_PLANT);
        sqLiteDatabase.execSQL(CREATE_TABLE_IMAGE);
        sqLiteDatabase.execSQL(CREATE_TABLE_PLANT);
    }

    /**
     * Upgrade database
     * @param sqLiteDatabase
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PlantContract.PlantEntry.TABLE_NAME);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PlantImageContract.ImageEntry.TABLE_NAME);
        }

    }
}
