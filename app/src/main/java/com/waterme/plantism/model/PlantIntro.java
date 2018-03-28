package com.waterme.plantism.model;

import android.util.Log;

/**
 * Created by zhuoran on 3/27/18.
 */

public class PlantIntro {
    private String species;
    private String genus;
    private String fullIntro;
    private String shortIntro;
    private String tips;
    private int humidityThread;
    private int temperatureThread;

    public PlantIntro(String species, String genus, String fullIntro,
                      String shortIntro, String tips, int humidityThread,
                      int temperatureThread) {
        this.shortIntro = shortIntro;
        this.species = species;
        this.genus = genus;
        this.fullIntro = fullIntro;
        this.tips = tips;
        this.humidityThread = humidityThread;
        this.temperatureThread = temperatureThread;
    }

    public PlantIntro() {}

    public String getFullIntro() {
        return fullIntro;
    }

    public String getGenus() {
        return genus;
    }

    public String getShortIntro() {
        return shortIntro;
    }

    public String getSpecies() {
        return species;
    }

    public String getTips() {
        return tips;
    }

    public int getHumidityThread() {
        return humidityThread;
    }

    public int getTemperatureThread() {
        return temperatureThread;
    }

    public void setFullIntro(String fullIntro) {
        this.fullIntro = fullIntro;
    }

    public void setGenus(String genus) {
        this.genus = genus;
    }

    public void setShortIntro(String shortIntro) {
        this.shortIntro = shortIntro;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public void setHumidityThread(int humidityThread) {
        this.humidityThread = humidityThread;
    }

    public void setTemperatureThread(int temperatureThread) {
        this.temperatureThread = temperatureThread;
    }

    @Override
    public String toString() {
        String res = "species: " + species
                + ", genues: " + genus
                + "，short Intro: " + shortIntro
                + ", full Intro: " + fullIntro
                + ", tips: " + tips;
        return res;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof PlantIntro)) {
            return false;
        }

        PlantIntro temp = (PlantIntro) obj;
        return species.equals(temp.getSpecies()) &&
                genus.equals(temp.getGenus()) &&
                shortIntro.equals(temp.getShortIntro()) &&
                fullIntro.equals(temp.getFullIntro()) &&
                tips.equals(temp.getTips());

    }
}
