package com.waterme.plantism;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.SnapshotParser;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * The home activity for the app. The activity has a list of the plant for the user.
 * there is an add method to bind a new sensor to a plant
 */
public class HomeActivity extends BaseActivity {


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


    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseStorage mFirebaseStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        FrameLayout contentFramLayout = (FrameLayout) findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_home, contentFramLayout);

        // get the current username
        SharedPreferences sharedPre = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        uid = sharedPre.getString("uid","");
        // find the views
        mRecycleView = (RecyclerView) findViewById(R.id.ry_plants);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb);


        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseStorage = FirebaseStorage.getInstance();
        //addTest();
        showLoadingIndicator();
        // load the data from firebase


        DatabaseReference ref = mFirebaseDatabase.getReference();
        ref.child(USER_CHILD).child(uid).child(USER_REALTIME_CHILD).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                            Log.d(TAG, (String) dsp.getKey());
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
        );

        FirebaseRecyclerOptions<RealTimeData> options = new FirebaseRecyclerOptions.Builder<RealTimeData>()
                .setQuery(mFirebaseDatabase.getReference().child(USER_CHILD)
                        .child(uid)
                        .child(USER_REALTIME_CHILD)
                        , RealTimeData.class)
                .build();

        Log.d(TAG, "before adpater");
        mAdapter = new FirebaseRecyclerAdapter<RealTimeData, RealTimeViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull RealTimeViewHolder viewHolder, int position, @NonNull final RealTimeData model) {
                // bind viewholder to view, set the view content
                Log.d(TAG, "bind view holder!");
                String imgUrl = model.getImgaUrl();
                String tUrl = model.gettUrl();
                String hUrl = model.gethUrl();

                // set image for ui
                StorageReference storageReference = mFirebaseStorage.getReference();

                // set text for ui
                viewHolder.plantMyName.setText(model.getPlantMyname());
                viewHolder.plantName.setText(model.getPlantName());
                viewHolder.hmText.setText(String.valueOf(model.getHumidity()).substring(0, 5));
                viewHolder.tpText.setText(String.valueOf(model.getTemperature()).substring(0, 5));
                viewHolder.setmClickListener(new RealTimeViewHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Log.d(TAG, "item click " + position);
                        Intent intent = new Intent(HomeActivity.this, DetailActivity.class);
                        intent.putExtra(PLANTID, model.getPlantMyname());
                        intent.putExtra(CATEGORY, model.getPlantName());
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public RealTimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                Log.d(TAG, "create view holder !");
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                RealTimeViewHolder view = new RealTimeViewHolder(inflater.inflate(R.layout.item_plant, parent, false));


                return view;
            }

            // E
            @Override
            public void onDataChanged() {
                super.onDataChanged();
                Log.d(TAG, "firebase data change!");
            }
        };

        addTest();
        Log.d(TAG, "create adapter finish");
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecycleView.setLayoutManager(layoutManager);
        mRecycleView.setAdapter(mAdapter);
        hideLoadingIndicator();
        Log.d(TAG, "add plants");
        addPlants("绿萝","kkk","ttt");
        Log.d(TAG, "add sensors");
        addSensor("1546", "kkk");
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


    // add sensor
    private void addSensor(final String sensorId, final String plantMyName) {
        final DatabaseReference dbRef = mFirebaseDatabase.getReference()
                .child(SENSOR_CHILD);

        // <sid - <uid, plantMyName>
        // check if sensor is in the
        final Map<String, Object> update = new HashMap<>();
        update.put(sensorId, new Sensor(uid, plantMyName));

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    dbRef.updateChildren(update);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    //use update !
    private void addPlants(String plantName, String plantMyName, String imgUrl){
        final DatabaseReference dbRef = mFirebaseDatabase.getReference()
                .child(USER_CHILD)
                .child(uid)
                .child(USER_PLANTS_CHILD);
        Map<String, Object> update = new HashMap<>();
        update.put(plantMyName, new Plant(plantName, "history", imgUrl));
        dbRef.updateChildren(update);
    }

    private void addTest() {
        Random random = new Random();
        DatabaseReference ref = mFirebaseDatabase.getReference()
                .child(USER_CHILD).child(uid);
        Map<String, Object> update = new HashMap<>();
        update.put(USER_REALTIME_CHILD + "/1645", new RealTimeData(random.nextDouble(),
                random.nextDouble(),
                "1",
                "2",
                "3",
                "4",
                "5"));
        update.put(USER_REALTIME_CHILD + "/1541", new RealTimeData(random.nextDouble(),
                random.nextDouble(),
                "1",
                "2",
                "3",
                "4",
                "5"));
        update.put(USER_REALTIME_CHILD + "/1234", new RealTimeData(random.nextDouble(),
                random.nextDouble(),
                "1",
                "2",
                "3",
                "4",
                "5"));
        update.put(USER_REALTIME_CHILD + "/2333", new RealTimeData(random.nextDouble(),
                random.nextDouble(),
                "1",
                "2",
                "3",
                "4",
                "5"));

        ref.updateChildren(update);


    }
    private void changeTest() {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Random random = new Random();
                Map<String, Object> update = new HashMap<>();
                update.put("1645/humidity", random.nextDouble());
                update.put("1645/temperature", random.nextDouble());
                update.put("1234/humidity", random.nextDouble());
                update.put("1234/temperature", random.nextDouble());
                update.put("2333/humidity", random.nextDouble());
                update.put("2333/temperature", random.nextDouble());
                DatabaseReference ref = mFirebaseDatabase.getReference();
                ref.child(USER_CHILD).child(uid)
                        .child(USER_REALTIME_CHILD)
                        .updateChildren(update);
            }
        };
        timer.schedule(timerTask, 5000, 5000);
    }
}
