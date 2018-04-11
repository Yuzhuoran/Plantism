package com.waterme.plantism;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.waterme.plantism.model.Plant;
import com.waterme.plantism.model.RealTimeData;
import com.waterme.plantism.model.Sensor;

/**
 * The home activity for the app. The activity has a list of the plant for the user.
 * there is an add method to bind a new sensor to a plant
 */
public class SensorHomeActivity extends BaseActivity{

    public static String PACKAGE_NAME;
    private static final String TAG = "Sensor home activity";

    /** the recycleview to read the condition of plants
     * it includes the real time temperature, humidity and growing condition
     */

    private RecyclerView mRecycleView;
    private ProgressBar mLoadingIndicator;


    private FirebaseRecyclerAdapter<RealTimeData, RealTimeViewHolder> mAdapter;

    private String uid;
    private static final String SENSORID = "SENSORID";
    private static final String CATEGORY = "CATEGORY";
    private static final String USER_CHILD = "userInfo";
    private static final String SENSOR_CHILD = "sensorList";
    private static final String USER_SENSORS_CHILD = "sensors";
    private static final String USER_PLANTS_CHILD = "plants";
    private static final String USER_REALTIME_CHILD = "now";

    private static final String IMAGE_URL = "image";

    private ImageView btnAddSensor;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseStorage mFirebaseStorage;

    private static final int REAL_TIME_DATA_TYPE = 1;
    private static final int ADD_PLANT_TYPE = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        PACKAGE_NAME = getApplicationContext().getPackageName();
        final FrameLayout contentFramLayout = (FrameLayout) findViewById(R.id.content_frame);

        getLayoutInflater().inflate(R.layout.activity_sensor_home, contentFramLayout);

        // get the current username
        SharedPreferences sharedPre = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        uid = sharedPre.getString("uid","");
        // find the views

        mRecycleView = (RecyclerView) findViewById(R.id.ry_sensors);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb);
        btnAddSensor = (ImageView) contentFramLayout.findViewById(R.id.button_add_sensor);

        btnAddSensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "on click!");
                Intent intent=new Intent(SensorHomeActivity.this,AddSensorActivity.class);
                startActivity(intent);
            }
        });

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseStorage = FirebaseStorage.getInstance();

        showLoadingIndicator();
        // load the data from firebase

        FirebaseRecyclerOptions<RealTimeData> options = new FirebaseRecyclerOptions.Builder<RealTimeData>()
                .setQuery(mFirebaseDatabase.getReference().child(USER_CHILD)
                                .child(uid)
                                .child(USER_REALTIME_CHILD)
                                .orderByChild("order")
                        , RealTimeData.class)
                .build();

        Log.d(TAG, "before adapter");

        /* create adapter */
        mAdapter = new FirebaseRecyclerAdapter<RealTimeData, RealTimeViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull RealTimeViewHolder viewHolder, int position, @NonNull final RealTimeData model) {
                if (getItemViewType(position) == REAL_TIME_DATA_TYPE) {

                    Log.d(TAG, "has plant");
                    /*
                    String imgUrl = model.getImageUrl();
                    String tUrl = model.gettUrl();
                    String hUrl = model.gethUrl();
                    */
                    // set text for ui
                    //TODO finish the UI
                    viewHolder.sensorPlantName.setText(model.getPlantMyname());
                    viewHolder.sensorId.setText(String.valueOf(model.getOrder()));
                    viewHolder.sensorPlantSpecies.setText(model.getPlantCategory());
                } else {
                    //TODO finish the UI
                    viewHolder.sensorId.setText(String.valueOf(model.getOrder() - 9999));
                    viewHolder.sensorPlantName.setText("");
                    viewHolder.sensorPlantSpecies.setText("No plant connected");
                }

                viewHolder.setmClickListener(new RealTimeViewHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Log.d(TAG, "item click " + position);
                        if (getItemViewType(position) == REAL_TIME_DATA_TYPE) {
                            //do nothing
                        } else {
                            /* start an intent to add a plant */
                            //TODO
                            Intent intent = new Intent(SensorHomeActivity.this,SearchActivity.class);
                            intent.putExtra("sensor_id", String.valueOf(model.getOrder()-9999));
                            startActivity(intent);
                            Log.d(TAG, "add a plant!");
                        }
                    }
                });

            }

            @NonNull
            @Override
            public RealTimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                return new RealTimeSensorViewHolder(inflater.inflate(R.layout.item_sensor, parent, false));

            }

            @Override
            public void onDataChanged() {
                super.onDataChanged();
                Log.d(TAG, "firebase data change!");
            }

            /**
             * get the view type by the position and the order of the model;
             * @param position
             * @return
             */
            @Override
            public int getItemViewType(int position) {
                RealTimeData model = getItem(position);
                if (model.getOrder()> 9999) {
                    return ADD_PLANT_TYPE;
                } else {
                    return REAL_TIME_DATA_TYPE;
                }
            }
        };
        Log.d(TAG, "create adapter finish");
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecycleView.setLayoutManager(layoutManager);
        mRecycleView.setAdapter(mAdapter);
        ((SimpleItemAnimator) mRecycleView.getItemAnimator()).setSupportsChangeAnimations(false);
        hideLoadingIndicator();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAdapter.stopListening();
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
        if (id == R.id.home) {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }
}



