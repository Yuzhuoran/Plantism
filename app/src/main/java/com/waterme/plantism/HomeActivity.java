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

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * The home activity for the app. The activity has a list of the plant for the user.
 * there is an add method to bind a new sensor to a plant
 */
public class HomeActivity extends BaseActivity{

    public static String PACKAGE_NAME;
    private static final String TAG = "Home activity";

    /** the recycleview to read the condition of plants
     * it includes the real time temperature, humidity and growing condition
     */

    private RecyclerView mRecycleView;
    private ProgressBar mLoadingIndicator;


    private FirebaseRecyclerAdapter<RealTimeData, RealTimeViewHolder> mAdapter;

    private String uid;
    private static final String PLANTID = "PLANTID";
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
        FrameLayout contentFramLayout = (FrameLayout) findViewById(R.id.content_frame);
        //btnAddSensor=(ImageView) findViewById(R.id.button_add_plant);
        //btnAddSensor.setOnClickListener(this);

        getLayoutInflater().inflate(R.layout.activity_home, contentFramLayout);

        // get the current username
        SharedPreferences sharedPre = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        uid = sharedPre.getString("uid","");
        // find the views

        mRecycleView = (RecyclerView) findViewById(R.id.ry_plants);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb);
        btnAddSensor = (ImageView) contentFramLayout.findViewById(R.id.button_add_sensor);

        if (btnAddSensor != null) {
            Log.d(TAG, "this is not a null object");
        }
        btnAddSensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "on click!");
                Intent intent=new Intent(HomeActivity.this,AddSensorActivity.class);
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
                // get the view to bind by the order
                if (getItemViewType(position) == REAL_TIME_DATA_TYPE) {

                    Log.d(TAG, "bind real time data");
                    String imgUrl = model.getImageUrl();
                    String tUrl = model.gettUrl();
                    String hUrl = model.gethUrl();

                    // set image for ui
                    StorageReference storageReference = mFirebaseStorage.getReference();
                    // set text for ui
                    viewHolder.plantMyName.setText(model.getPlantMyname());
                    viewHolder.plantCategory.setText(model.getPlantCategory());
                    if (model.getHumidity() != null) {
                        viewHolder.hmText.setText(model.getHumidity().substring(0, Math.min(4, model.getHumidity().length()))+"%");
                    }
                    if (model.getTemperature() != null) {
                        viewHolder.tpText.setText(model.getTemperature().substring(0, Math.min(4, model.getTemperature().length()))+"°F");
                    }
                } else {
                    //TODO
                    Log.d(TAG, "bind add plant view!");
                }

                viewHolder.setmClickListener(new RealTimeViewHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Log.d(TAG, "item click " + position);
                        if (getItemViewType(position) == REAL_TIME_DATA_TYPE) {
                            Intent intent = new Intent(HomeActivity.this, DetailActivity.class);
                            intent.putExtra(PLANTID, model.getPlantMyname());
                            intent.putExtra(CATEGORY, model.getPlantCategory());
                            startActivity(intent);
                        } else {
                            /* start an intent to add a plant */
                            //TODO
                            Log.d(TAG, "add a plant!");
                        }
                    }
                });
            }

            @NonNull
            @Override
            public RealTimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                Log.d(TAG, "create view holder !");
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                if (viewType == REAL_TIME_DATA_TYPE) {
                    return new RealTimePlantViewHolder(inflater.inflate(R.layout.item_plant, parent, false));
                } else {
                    return new AddPlantViewHolder(inflater.inflate(R.layout.item_add_plant, parent, false));
                }

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
                if (model.getOrder() > 9999) {
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
        addTest();
        changeTest();
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


    // add sensor to the sensor list to let arduino know the uid and plantid
    // then add a sensor view in the Now child of user

    /* put in AddSensorActivity */
    //TODO
    private void addSensor(final String sensorId) {

        final DatabaseReference dbRef = mFirebaseDatabase.getReference()
                .child(SENSOR_CHILD);
        Log.d(TAG, "add sensor to db");
        /* update the sensor child */
        Map<String, Object> update = new HashMap<>();
        update.put(sensorId, new Sensor(uid, "plantName"));
        dbRef.updateChildren(update);

        /* update the user now data */
        final DatabaseReference dbNowRef = mFirebaseDatabase.getReference()
                .child(USER_CHILD)
                .child(uid)
                .child(USER_REALTIME_CHILD);
        update.clear();
        update.put(sensorId, new RealTimeData(
                "humidity",
                "temperature",
                "hUrl",
                "tUrl",
                "ImageUrl",
                "PlantMyname",
                "PlantCategory",
                Integer.valueOf(sensorId) + 9999));
        dbNowRef.updateChildren(update);
        Log.d(TAG, "add sensor to now");
    }
    //use update
    /* put in AddPlantActivity */
    //TODO
    private void addPlants(String plantCategory, String plantMyName, String sensorId){
        Log.d(TAG, "Add plant test");
        final DatabaseReference dbRef = mFirebaseDatabase.getReference()
                .child(USER_CHILD)
                .child(uid).child(USER_PLANTS_CHILD);

        /* update plant child */
        Map<String, Object> update = new HashMap<>();
        update.put(plantMyName, new Plant(plantCategory, "history", "*"));
        dbRef.updateChildren(update);

        /* update the now child */
        DatabaseReference dbNowRef = mFirebaseDatabase.getReference()
                .child(USER_CHILD)
                .child(uid)
                .child(USER_REALTIME_CHILD);
        update.clear();
        update.put(sensorId + "/plantMyname", plantMyName);
        update.put(sensorId + "/plantCategory", plantCategory);
        update.put(sensorId + "/order", Integer.valueOf(sensorId));

        dbNowRef.updateChildren(update);


    }
    private void addTest() {
        Random random = new Random();
        Log.d(TAG, "add sensor test!");

        /* first add sensor */
        addSensor("1234");
        addSensor("1645");
        addSensor("2333");

        /* then add two plant */
        addPlants("panda", "mika", "1234");
        addPlants("Dog", "miba", "1645");

    }
    private void changeTest() {

        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Random random = new Random();
                Map<String, Object> update = new HashMap<>();
                update.put("1645/humidity", String.valueOf(65.0+random.nextDouble()));
                update.put("1645/temperature", String.valueOf(72.0+random.nextDouble()));
                update.put("1234/humidity", String.valueOf(82.0+random.nextDouble()));
                update.put("1234/temperature", String.valueOf(77.0+random.nextDouble()));
                //update.put("1541/humidity", String.valueOf(random.nextDouble()));
                //update.put("1541/temperature", String.valueOf(random.nextDouble()));
                DatabaseReference ref = mFirebaseDatabase.getReference();
                ref.child(USER_CHILD).child(uid)
                        .child(USER_REALTIME_CHILD)
                        .updateChildren(update);
            }
        };
        timer.schedule(timerTask, 2000, 2000);

        /* 10 seconds later add a new plant to a empty sensor */
        TimerTask addPlantTask = new TimerTask() {
            @Override
            public void run() {
                addPlants("panda", "plant1", "2333" );
            }
        };

        timer.schedule(addPlantTask, 10000);
    }

}
