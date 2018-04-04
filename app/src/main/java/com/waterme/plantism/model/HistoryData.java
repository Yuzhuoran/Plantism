package com.waterme.plantism.model;

import java.util.Date;

/**
 * Created by zhuoran on 4/2/18.
 */

public class HistoryData {
    private String air_h;
    private String air_t;
    private String soil_h;
    private String timestamp;

    public HistoryData(){}

    public HistoryData(String air_h, String air_t, String soil_h, String timestamp) {
        this.air_h = air_h;
        this.air_t = air_t;
        this.soil_h = soil_h;
        this.timestamp = timestamp;
    }

    public String getAir_h() {
        return air_h;
    }

    public String getAir_t() {
        return air_t;
    }

    public String getSoil_h() {
        return soil_h;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setAir_h(String air_h) {
        this.air_h = air_h;
    }

    public void setAir_t(String air_t) {
        this.air_t = air_t;
    }

    public void setSoil_h(String soil_h) {
        this.soil_h = soil_h;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

}
