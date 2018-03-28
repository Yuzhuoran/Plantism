package com.waterme.plantism.data;

import android.provider.BaseColumns;

/**
 * Created by zhuoran on 3/9/18.
 * Defines the humulity and temperature data for local SQLitedatabase
 */

/**
 * The plant intro database schema
 */
public class PlantContract {


    public static final class PlantEntry implements BaseColumns {
        public static final String TABLE_NAME = "plant";
        public static final String _ID = "id";
        public static final String COLUMN_SPECIES = "species";
        public static final String COLUMN_GENUES = "genus";
        public static final String COLUMN_SHORT_INTRO = "shortIntro";
        public static final String COLUMN_FULL_INTRO = "fullIntro";
        public static final String COLUMN_TIP = "tips";
    }
}

