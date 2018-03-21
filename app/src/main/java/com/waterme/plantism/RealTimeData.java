package com.waterme.plantism;

/**
 * Created by zhuoran on 3/15/18.
 */

public class RealTimeData {
    private double humidity;
    private double temperature;
    private String plantMyname;
    private String plantName;
    private String hUrl = null;
    private String tUrl = null;
    private String imgaUrl = null;

    public RealTimeData() {

    }
    public RealTimeData(double h, double t, String hUrl, String tUrl,
                        String imgaUrl, String plantMyname, String plantName) {
        humidity = h;
        temperature = t;
        this.hUrl = hUrl;
        this.tUrl = tUrl;
        this.imgaUrl = imgaUrl;
        this.plantMyname = plantMyname;
        this.plantName = plantName;
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

    public double getHumidity() {
        return humidity;
    }

    public String getImgaUrl() {
        return imgaUrl;
    }

    public String getPlantMyname() {
        return plantMyname;
    }

    public String getPlantName() {
        return plantName;
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

    public void setImgaUrl(String imgaUrl) {
        this.imgaUrl = imgaUrl;
    }
}
