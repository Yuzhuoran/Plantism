package com.waterme.plantism;

import java.util.List;

/**
 * Created by zhuoran on 3/10/18.
 * Plant object github test
 */

public class Plant {
    private List<SensorData> sensorDataList;
    private int plant_id;
    private String name;

    public Plant(List<SensorData> sensorDataList, int p_id, String name) {
        this.sensorDataList = sensorDataList;
        this.plant_id = p_id;
        this.name = name;
    }

    public List<SensorData> getSensorDataList() {
        return sensorDataList;
    }

    public int getPlant_id() {
        return plant_id;
    }

    public String getName() {
        return name;
    }

    public void setSensorDataList(List<SensorData> sensorDataList) {
        this.sensorDataList = sensorDataList;
    }

    public void setPlant_id(int id) {
        plant_id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

}
