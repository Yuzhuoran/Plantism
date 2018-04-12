package com.waterme.plantism;

import android.view.View;

import com.waterme.plantism.model.MyTextView;

/**
 * Created by zhuoran on 3/28/18.
 */

public class RealTimeSensorViewHolder extends RealTimeViewHolder {

    public RealTimeSensorViewHolder(View view) {
        super(view);
        sensorId = itemView.findViewById(R.id.tv_sensor_id);
        sensorPlantName = (MyTextView)itemView.findViewById(R.id.tv_plant_name);
        sensorPlantSpecies = (MyTextView) itemView.findViewById(R.id.tv_plant_species);
        sensorPlantSpecies.setStyle("light");
        sensorPlantName.setStyle("light");
    }
}
