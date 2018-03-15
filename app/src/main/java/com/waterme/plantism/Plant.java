package com.waterme.plantism;

import java.util.List;

/**
 * Created by zhuoran on 3/10/18.
 * Plant object
 */

public class Plant {
    private List<SensorData> sensorDataList;
    private String plant_id;
    private String name;
    private SensorData currentData;

    public Plant(List<SensorData> sensorDataList, String p_id, String name, SensorData currentData) {
        this.sensorDataList = sensorDataList;
        this.plant_id = p_id;
        this.name = name;
        this.currentData = currentData;
    }

    public List<SensorData> getSensorDataList() {
        return sensorDataList;
    }

    public String getPlant_id() {
        return plant_id;
    }

    public String getName() {
        return name;
    }

    public void setSensorDataList(List<SensorData> sensorDataList) {
        this.sensorDataList = sensorDataList;
    }

    public void setPlant_id(String id) {
        plant_id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

}
