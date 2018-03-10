package com.waterme.plantism.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.waterme.plantism.data.PlantContract;

/**

 * Created by zhuoran on 3/9/18.
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
}
