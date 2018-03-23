package com.waterme.plantism;


import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.IMarker;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by zhuoran on 3/22/18.
 */

public class StatusFragment extends Fragment {
    private static final String TAG = "StatusFragment";


    private static final String PLANTID = "PLANTID";
    private static final String UID = "UID";
    private static final String USER_CHILD = "userInfo";
    private static final String SENSOR_CHILD = "sensorList";
    private static final String USER_SENSORS_CHILD = "sensors";
    private static final String USER_PLANTS_CHILD = "plants";
    private static final String USER_REALTIME_CHILD = "now";

    private String plantid;
    private String uid;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            plantid = getArguments().getString(PLANTID);
            uid = getArguments().getString(UID);
            Log.d(TAG, "plantid is : "+ plantid);
            Log.d(TAG, "uid is :" + uid);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.content_status, container, false);

        TextView tvOutdoor = (TextView) rootView.findViewById(R.id.tv_outdoor);
        TextView tvWeather = (TextView) rootView.findViewById(R.id.tv_weather);
        TextView tvMyPlantName = (TextView) rootView.findViewById(R.id.tv_status_myname);
        TextView tvPlantName = (TextView) rootView.findViewById(R.id.tv_status_name);

        ImageView imPlant = (ImageView) rootView.findViewById(R.id.im_status);
        ImageView imWeather = (ImageView) rootView.findViewById(R.id.im_weather);
        ImageView imStatusSun = (ImageView) rootView.findViewById(R.id.im_status_sun);
        ImageView imStatusWater = (ImageView) rootView.findViewById(R.id.im_status_water);
        ImageView imStatusTemp = (ImageView) rootView.findViewById(R.id.im_status_temp);

        BarChart ctHumidity = (BarChart) rootView.findViewById(R.id.ct_humidity);
        LineChart ctTemperature = (LineChart) rootView.findViewById(R.id.ct_temperature);

        // get data base reference
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();

        // set plant condition indicator
        // set humidity barchart
        // set temperature linechart


        return rootView;
    }
}
