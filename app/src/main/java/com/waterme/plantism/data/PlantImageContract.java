package com.waterme.plantism.data;

import android.provider.BaseColumns;

/**
 * Created by zhuoran on 3/26/18.
 */

public class PlantImageContract {
    public static final class ImageEntry implements BaseColumns {
        public static final String TABLE_NAME = "image";
        public static final String _ID = "id";
        public static final String COLUMN_SPECIES = "species";
        public static final String COLUMN_IMAGE = "name";

    }

}
