package com.waterme.plantism;
import java.util.Date;

/**
 * Created by zhuoran on 3/10/18.
 * Sensor data class, this is a sensor data
 */

public class SensorData {
    private double temperature;
    private double humudity;
    private int plant_id;
    private Date date;

    /**
     * Constructor
     * @param t temperature
     * @param h humudity
     * @param pid id
     * @param now date
     */
    public SensorData(double t, double h, int pid, Date now) {
        temperature = t;
        humudity = h;
        plant_id = pid;
        date = now;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getHumudity() {
        return humudity;
    }

    public Date getDate() {
        return date;
    }

    public int getPlant_id() {
        return plant_id;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public void setHumudity(double humudity) {
        this.humudity = humudity;
    }

    public void setPlant_id(int id) {
        this.plant_id = id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
