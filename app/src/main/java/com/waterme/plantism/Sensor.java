package com.waterme.plantism;

/**
 * Created by zhuoran on 3/20/18.
 */

public class Sensor {
    private String uid;
    private String plantId;

    public Sensor(String uid, String plantId) {
        this.uid = uid;
        this.plantId = plantId;
    }

    public String getPlantId() {
        return plantId;
    }

    public String getUid() {
        return uid;
    }

    public void setPlantId(String plantId) {
        this.plantId = plantId;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
