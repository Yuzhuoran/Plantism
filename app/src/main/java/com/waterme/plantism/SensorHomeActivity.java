package com.waterme.plantism;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.waterme.plantism.R;
import com.waterme.plantism.model.RealTimeData;

public class SensorHomeActivity extends AppCompatActivity {

    private static final String TAG = "sensor activity";
    private RecyclerView mRecycleView;
    private ProgressBar mLoadingIndicator;

    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseRecyclerAdapter<RealTimeData, RealTimeViewHolder> mAdapter;

    private String uid;
    private static final String PLANTID = "PLANTID";
    private static final String CATEGORY = "CATEGORY";
    private static final String USER_CHILD = "userInfo";
    private static final String SENSOR_CHILD = "sensorList";
    private static final String USER_SENSORS_CHILD = "sensors";
    private static final String USER_PLANTS_CHILD = "plants";
    private static final String USER_REALTIME_CHILD = "now";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_home);

        FirebaseRecyclerOptions<RealTimeData> options = new FirebaseRecyclerOptions.Builder<RealTimeData>()
                .setQuery(mFirebaseDatabase.getReference().child(USER_CHILD)
                                .child(uid)
                                .child(USER_REALTIME_CHILD)
                                .orderByChild("order")
                        , RealTimeData.class)
                .build();
    }
    //TODO

}
