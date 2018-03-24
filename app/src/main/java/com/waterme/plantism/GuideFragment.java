package com.waterme.plantism;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by zhuoran on 3/22/18.
 */

public class GuideFragment extends Fragment {
    private static final String TAG = "Guide Fragment";

    private static final String PLANTID = "PLANTID";
    private static final String UID = "UID";
    private static final String USER_CHILD = "userInfo";
    private static final String SENSOR_CHILD = "sensorList";
    private static final String USER_SENSORS_CHILD = "sensors";
    private static final String USER_PLANTS_CHILD = "plants";
    private static final String USER_REALTIME_CHILD = "now";

    private String plantid;
    private String uid;

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
        View view = inflater.inflate(R.layout.content_guide, container, false);
        return view;
    }
}
