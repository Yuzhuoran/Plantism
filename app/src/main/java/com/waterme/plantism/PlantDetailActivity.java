package com.waterme.plantism;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarEntry;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class PlantDetailActivity extends AppCompatActivity {

    BarChart mTchart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_detail);

        mTchart = (BarChart) findViewById(R.id.ct_temperature);

    }

    private void plotBarChart(List<SensorData> data) {
        List<BarEntry> entries = new ArrayList<>();

    }



}
