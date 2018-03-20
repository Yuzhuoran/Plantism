package com.waterme.plantism;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.SnapshotParser;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The home activity for the app. The activity has a list of the plant for the user.
 * there is an add method to bind a new sensor to a plant
 */
public class HomeActivity extends AppCompatActivity {


    private static final String TAG = "Home activity";

    /** the recycleview to read the condition of plants
     * it includes the real time temperature, humidity and growing condition
     */
    private RecyclerView mRecycleView;
    private ProgressBar mLoadingIndicator;
    private PlantsAdapter mPlantsAdapter;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private ActionBar mActionBar;

    private String uid;
    private static final String USER_CHILD = "userInfo";
    private static final String SENSOR_CHILD = "sensorList";
    private static final String USER_SENSORS_CHILD = "sensors";
    private static final String USER_PLANTS_CHILD = "plants";
    private static final String REALTIME_CHILD = "now";

    //sensor data + sensor data viewholder?
    private FirebaseDatabase mFirebaseDatabase;

    private List<String> sensorList;
    private List<RealTimeData> realTimeDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // get the current username
        SharedPreferences sharedPre = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        uid = sharedPre.getString("uid","");
        // find the views
        mRecycleView = (RecyclerView) findViewById(R.id.ry_plants);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_home);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_home);
        mNavigationView = (NavigationView) findViewById(R.id.nav_home);
        mActionBar = getSupportActionBar();



        if (mActionBar != null) {
            mActionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }

        // navigation
        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        item.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                }
        );


        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecycleView.setLayoutManager(layoutManager);

        showLoadingIndicator();
        // load the data from firebase

        mFirebaseDatabase = FirebaseDatabase.getInstance();

    }


    private void showLoadingIndicator() {
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }

    private void hideLoadingIndicator() {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.drawer_home) {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }


    // add sensor
    private void addSensor(final String sensorId) {
        final DatabaseReference dbRef = mFirebaseDatabase.getReference();

        // check if sensor is in the
        dbRef.child(SENSOR_CHILD).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean exist = false;
                for (DataSnapshot id : dataSnapshot.getChildren()) {
                    if (sensorId.equals((String)id.getValue())) {
                        exist = true;
                        Log.d(TAG, "sensor already exists");
                    }
                }
                if (!exist) {
                    Log.d(TAG, "add new sensor");
                    // add a new sensor into the user-sensor list
                    dbRef.child(USER_CHILD).child(USER_SENSORS_CHILD).push().setValue(sensorId);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void addPlants(final String sensorId, final String plantName) {
        final DatabaseReference dbRef = mFirebaseDatabase.getReference();
        // add a plant to the users'child, with
        dbRef.child(USER_CHILD).child(uid).child(USER_SENSORS_CHILD).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                            // the children is <key - id> form
                            if (sensorId.equals((String)dsp.getValue())) {
                                dbRef.child(USER_CHILD).child(uid).child(USER_PLANTS_CHILD)
                                    .push().setValue(
                                            new Plant(plantName,
                                                    sensorId,
                                                    "now",
                                                    "history",
                                                    null));
                                Log.d(TAG, "update plant information");

                                // update sensor List information
                                Map<String, Object> sensorUpdate = new HashMap<>();
                                sensorUpdate.put(sensorId + "/uid", uid);
                                sensorUpdate.put(sensorId + "/plantName", plantName);
                                dbRef.child(SENSOR_CHILD).updateChildren(sensorUpdate);
                                Log.d(TAG, "update sensor information");
                                return;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
        );
    }

    private void adminAddSensor(String sId) {
        final DatabaseReference dbRef = mFirebaseDatabase.getReference();
        dbRef.child(SENSOR_CHILD).child(sId).setValue(new Sensor("#", "#"));
    }

}
