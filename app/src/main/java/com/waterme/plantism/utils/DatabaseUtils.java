package com.waterme.plantism.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.waterme.plantism.data.PlantContract;
import com.waterme.plantism.model.Plant;
import com.waterme.plantism.model.PlantIntro;

/**

 * Created by zhuoran on 3/9/18.
 */

/**
 * Utils object for database
 * https://stackoverflow.com/questions/10600670/sqlitedatabase-query-method for query example
 */
public class DatabaseUtils {

    public static long insertSensorData(SQLiteDatabase db, ContentValues contentValues) {
        return db.insert(PlantContract.PlantEntry.TABLE_NAME, null, contentValues);
    }

    public static int bulkInsert(SQLiteDatabase db, ContentValues[] contentValues) {
        db.beginTransaction();
        int rowInserted = 0;
        try {
            for (ContentValues values : contentValues) {
                long _id = insertSensorData(db, values);
                if (_id != -1) {
                    rowInserted++;
                }
            }
        } finally {
            db.endTransaction();
        }
        return rowInserted;
    }

    /**
     * insert one plant intro information into database from a model
     * @param db plant intro database
     * @param plantIntro data model
     */
    public long insertOneRecord(SQLiteDatabase db, PlantIntro plantIntro) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(PlantContract.PlantEntry.COLUMN_FULL_INTRO, plantIntro.getFullIntro());
        contentValues.put(PlantContract.PlantEntry.COLUMN_SHORT_INTRO, plantIntro.getShortIntro());
        contentValues.put(PlantContract.PlantEntry.COLUMN_SPECIES, plantIntro.getSpecies());
        contentValues.put(PlantContract.PlantEntry.COLUMN_GENUES, plantIntro.getGenus());
        contentValues.put(PlantContract.PlantEntry.COLUMN_TIP, plantIntro.getTips());
        contentValues.put(PlantContract.PlantEntry.COLUMN_IMAGE, plantIntro.getImage());
        contentValues.put(PlantContract.PlantEntry.COLUMN_HUMIDITY_THREAD, plantIntro.getHumidityThread());
        contentValues.put(PlantContract.PlantEntry.COLUMN_TEMPERATURE_THREAD, plantIntro.getTemperatureThread());
        /* for testing */
        return db.insert(PlantContract.PlantEntry.TABLE_NAME, null, contentValues);
    }

    /**
     * get one Intro model from database
     * @param db plant intro database
     * @param species the name to get
     * @return the plant model
     */
    public PlantIntro getIntroBySpecies(SQLiteDatabase db, String species) {
        PlantIntro plantIntro = new PlantIntro();
        String selection = PlantContract.PlantEntry.COLUMN_SPECIES + " = ?";
        String[] selectionArgs = {species};
        Cursor cursor = db.query(
                PlantContract.PlantEntry.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        if (cursor.getCount() != 1) {
            throw new IllegalArgumentException("two same species in database!");
        } else {
            cursor.moveToNext();
            plantIntro.setFullIntro(cursor.getString(cursor.getColumnIndex(PlantContract.PlantEntry.COLUMN_FULL_INTRO)));
            plantIntro.setShortIntro(cursor.getString(cursor.getColumnIndex(PlantContract.PlantEntry.COLUMN_SHORT_INTRO)));
            plantIntro.setGenus(cursor.getString(cursor.getColumnIndex(PlantContract.PlantEntry.COLUMN_GENUES)));
            plantIntro.setSpecies(cursor.getString(cursor.getColumnIndex(PlantContract.PlantEntry.COLUMN_SPECIES)));
            plantIntro.setTips(cursor.getString(cursor.getColumnIndex(PlantContract.PlantEntry.COLUMN_TIP)));
            plantIntro.setHumidityThread(cursor.getInt(cursor.getColumnIndex(PlantContract.PlantEntry.COLUMN_HUMIDITY_THREAD)));
            plantIntro.setTemperatureThread(cursor.getInt(cursor.getColumnIndex(PlantContract.PlantEntry.COLUMN_TEMPERATURE_THREAD)));
            return plantIntro;
        }
    }
}
