package com.waterme.plantism.model;

/**
 * Created by zhuoran on 3/15/18.
 */

public class RealTimeData {
    private String humidity;
    private String temperature;
    private int order;
    private String plantMyname;
    private String plantCategory;
    private String hUrl = null;
    private String tUrl = null;
    private String imageUrl = null;

    public RealTimeData() {

    }
    public RealTimeData(String h, String t, String hUrl, String tUrl,
                        String imageUrl, String plantMyname,
                        String plantCategory, int order) {

        humidity = h;
        temperature = t;
        this.order = order;
        this.hUrl = hUrl;
        this.tUrl = tUrl;
        this.imageUrl = imageUrl;
        this.plantMyname = plantMyname;
        this.plantCategory = plantCategory;
    }

    public int getOrder() {
        return order;
    }

    public String getTemperature() {
        return temperature;
    }

    public String gethUrl() {
        return hUrl;
    }

    public String gettUrl() {
        return tUrl;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getPlantMyname() {
        return plantMyname;
    }

    public String getPlantCategory() {
        return plantCategory;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public void setPlantCategory(String plantCategory) {
        this.plantCategory = plantCategory;
    }

    public void setPlantMyname(String plantMyname) {
        this.plantMyname = plantMyname;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public void sethUrl(String hUrl) {
        this.hUrl = hUrl;
    }

    public void settUrl(String tUrl) {
        this.tUrl = tUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
