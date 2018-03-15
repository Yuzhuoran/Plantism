package com.waterme.plantism;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.SnapshotParser;
import com.firebase.ui.database.FirebaseRecyclerOptions;

/**
 * The home activity for the app. The activity has a list of the plant for the user.
 * there is an add method to bind a new sensor to a plant
 */
public class HomeActivity extends AppCompatActivity {

    public class PlantsAdapterViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {

        TextView plantSummary;

        PlantsAdapterViewHolder(View view) {
            super(view);
            plantSummary = (TextView) view.findViewById(R.id.tv_plant_data);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }

    private static final String TAG = "Home activity";

    /** the recycleview to read the condition of plants
     * it includes the real time temperature, humidity and growing condition
     */
    private RecyclerView mRecycleView;
    private ProgressBar mLoadingIndicator;
    private PlantsAdapter mPlantsAdapter;
    private String uid;
    private final String USER_CHILD = "USER_CHILD/";
    private final String PLANT_CHILD = "PLANT_CHILD";
    private final String DATABASE_URL = "https://arduinotest-c38b4.firebaseio.com/";

    //sensor data + sensor data viewholder?
    private FirebaseRecyclerAdapter<Plant, PlantsAdapterViewHolder> mFirebaseAdapter;
    private FirebaseDatabase mFirebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // get the current username
        SharedPreferences sharedPre = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        uid = sharedPre.getString("uid","");

        mRecycleView = (RecyclerView) findViewById(R.id.ry_plants);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_home);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecycleView.setLayoutManager(layoutManager);

        showLoadingIndicator();
        // load the data from firebase

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        Log.d(TAG, "get the database reference");
        String path = DATABASE_URL + USER_CHILD;
        Log.d(TAG, "path: " + path);
        DatabaseReference ref = mFirebaseDatabase.getReference(path);

    }


    private void showLoadingIndicator() {
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }

    private void hideLoadingIndicator() {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
    }
}
