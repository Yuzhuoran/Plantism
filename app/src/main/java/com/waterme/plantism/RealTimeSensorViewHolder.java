package com.waterme.plantism;

import android.view.View;

/**
 * Created by zhuoran on 3/28/18.
 */

public class RealTimeSensorViewHolder extends RealTimeViewHolder {

    public RealTimeSensorViewHolder(View view) {
        super(view);
        sensorId = itemView.findViewById(R.id.tv_sensor_id);
        sensorPlantName = itemView.findViewById(R.id.tv_plant_name);
        sensorPlantSpecies = itemView.findViewById(R.id.tv_plant_species);
    }
}
