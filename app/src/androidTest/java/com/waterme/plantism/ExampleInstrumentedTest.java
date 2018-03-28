package com.waterme.plantism;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.waterme.plantism.data.PlantDbHelper;
import com.waterme.plantism.data.PlantContract;
import com.waterme.plantism.model.Plant;
import com.waterme.plantism.model.PlantIntro;
import com.waterme.plantism.utils.DatabaseUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Field;
import java.security.spec.ECField;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private static final String TAG = "TEST";
    private final Context mContext = InstrumentationRegistry.getTargetContext();
    private final Class mDbHelperClass = PlantDbHelper.class;

    @Before
    public void setUp() {
        deleteTheDatabase();
    }

    @Test
    public void create_database_test() throws Exception {
        SQLiteOpenHelper dbHelper =
                (SQLiteOpenHelper) mDbHelperClass.getConstructor(Context.class).newInstance(mContext);

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        /* We think the database is open, let's verify that here */
        String databaseIsNotOpen = "The database should be open and isn't";
        assertEquals(databaseIsNotOpen,
                true,
                database.isOpen());

        /* This Cursor will contain the names of each table in our database */
        Cursor tableNameCursor = database.rawQuery(
                "SELECT name FROM sqlite_master WHERE type='table' AND name='" +
                        PlantContract.PlantEntry.TABLE_NAME + "'",
                null);

        /*
         * If tableNameCursor.moveToFirst returns false from this query, it means the database
         * wasn't created properly. In actuality, it means that your database contains no tables.
         */
        String errorInCreatingDatabase =
                "Error: This means that the database has not been created correctly";
        assertTrue(errorInCreatingDatabase,
                tableNameCursor.moveToFirst());

        /* If this fails, it means that your database doesn't contain the expected table(s) */
        assertEquals("Error: Your database was created without the expected tables.",
                PlantContract.PlantEntry.TABLE_NAME, tableNameCursor.getString(0));

        /* Always close a cursor when you are done with it */
        tableNameCursor.close();

    }

    /**
     * This method tests inserting a single record into an empty table from a brand new database.
     * The purpose is to test that the database is working as expected
     * @throws Exception in case the constructor hasn't been implemented yet
     */
    @Test
    public void insert_single_record_test() throws Exception{

        /* Use reflection to try to run the correct constructor whenever implemented */
        SQLiteOpenHelper dbHelper =
                (SQLiteOpenHelper) mDbHelperClass.getConstructor(Context.class).newInstance(mContext);

        /* Use WaitlistDbHelper to get access to a writable database */
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        /* get the utils */
        DatabaseUtils dbUtils = new DatabaseUtils();
        PlantIntro intro = new PlantIntro(
                "Donkey Burros Tails",
                "Succulents",
                "Burro’s tail is a heat and drought tolerant plant well suited for warm to temperate regions. The thick stems appear woven or plaited with leaves. The succulent is green to gray green or even blue green and may have a slight chalky look. ",
                "Sedum morganianum, or Burro’s tail, is a succulent perennial plant native to Mexico. ",
                "prefers bright, indirect sunlight. It will burn in strong, hot sun.\n" +
                        "\n" +
                        "watering once a month should be plenty, as the leaves hold quite a bit of moisture\n" +
                        "\n" +
                        "this plant should ideally be a lovely blue-green.\n"


        );


        /* Insert ContentValues into database and get first row ID back */
        long firstRowId = dbUtils.insertOneRecord(database, intro);
         /* If the insert fails, database.insert returns -1 */
        assertNotEquals("Unable to insert into the database", -1, firstRowId);

        /*
         * Query the database and receive a Cursor. A Cursor is the primary way to interact with
         * a database in Android.
         */
        Cursor wCursor = database.query(
                /* Name of table on which to perform the query */
                PlantContract.PlantEntry.TABLE_NAME,
                /* Columns; leaving this null returns every column in the table */
                null,
                /* Optional specification for columns in the "where" clause above */
                null,
                /* Values for "where" clause */
                null,
                /* Columns to group by */
                null,
                /* Columns to filter by row groups */
                null,
                /* Sort order to return in Cursor */
                null);

        /* Cursor.moveToFirst will return false if there are no records returned from your query */
        String emptyQueryError = "Error: No Records returned from waitlist query";
        assertTrue(emptyQueryError,
                wCursor.moveToFirst());
        PlantIntro readIntro = dbUtils.getIntroBySpecies(database, "Donkey Burros Tails");

        /* test if read the correct data */
        assertTrue(readIntro.equals(intro));


        System.out.println(readIntro.toString());
        Log.d(TAG, readIntro.toString());
        /* Close cursor and database */
        wCursor.close();
        dbHelper.close();
    }

    /**
     * Tests to ensure that inserts into your database results in automatically
     * incrementing row IDs.
     * @throws Exception in case the constructor hasn't been implemented yet
     */
    @Test
    public void autoincrement_test() throws Exception{

        /* First, let's ensure we have some values in our table initially */
        insert_single_record_test();

        /* Use reflection to try to run the correct constructor whenever implemented */
        SQLiteOpenHelper dbHelper =
                (SQLiteOpenHelper) mDbHelperClass.getConstructor(Context.class).newInstance(mContext);

        /* Use WaitlistDbHelper to get access to a writable database */
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        PlantIntro intro = new PlantIntro(
                "1",
                "2",
                "3",
                "4",
                "5"
        );
        DatabaseUtils dbUtils = new DatabaseUtils();


        /* Insert ContentValues into database and get first row ID back */
        long firstRowId = dbUtils.insertOneRecord(database, intro);

        PlantIntro intro2 = new PlantIntro(
                "2",
                "2",
                "3",
                "4",
                "5"
        );
        /* Insert ContentValues into database and get another row ID back */
        long secondRowId = dbUtils.insertOneRecord(database, intro2);

        assertEquals("ID Autoincrement test failed!",
                firstRowId + 1, secondRowId);

    }



    void deleteTheDatabase() {
        try {
            Field f = mDbHelperClass.getDeclaredField("DATABASE_NAME");
            f.setAccessible(true);
            mContext.deleteDatabase((String)f.get(null));
        } catch (NoSuchFieldException ex) {
            fail("No member");
        } catch (Exception ex) {
            fail(ex.getMessage());
        }

    }
}
