package com.waterme.plantism;

/**
 * Created by zhuoran on 3/15/18.
 */

public class RealTimeData {
    private double humidity;
    private double temperature;
    private String hUrl = null;
    private String tUrl = null;

    public RealTimeData(double h, double t, String hUrl, String tUrl) {
        humidity = h;
        temperature = t;
        this.hUrl = hUrl;
        this.tUrl = tUrl;
    }

    public double getHumIdity() {
        return humidity;
    }

    public double getTemperature() {
        return temperature;
    }

    public String gethUrl() {
        return hUrl;
    }

    public String gettUrl() {
        return tUrl;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public void sethUrl(String hUrl) {
        this.hUrl = hUrl;
    }

    public void settUrl(String tUrl) {
        this.tUrl = tUrl;
    }
}
