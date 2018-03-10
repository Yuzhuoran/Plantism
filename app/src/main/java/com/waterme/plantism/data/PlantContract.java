package com.waterme.plantism.data;

import android.provider.BaseColumns;

/**
 * Created by zhuoran on 3/9/18.
 * Defines the humulity and temperature data for local SQLitedatabase
 */

/**
 * Constructor for the weather contract
 */
public class PlantContract {


    public static final class PlantEntry implements BaseColumns {
        public static final String TABLE_NAME = "plant";
        public static final String COLUMN_DATE = "date_id";
        public static final String COLUMN_PLANT = "plant_id";
        public static final String COLUMN_TEMP = "temperature";
        public static final String COLUMN_HUMIDITY = "humidity";
    }
}

