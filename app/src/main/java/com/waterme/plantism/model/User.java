package com.waterme.plantism.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhuoran on 3/9/18.
 */

public class User {

    private String name;
    private String email;
    private String now;
    private String plants;

    public User(String name, String email, String now, String plants) {
        this.name = name;
        this.email = email;
        this.now = now;
        this.plants = plants;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getNow() {
        return now;
    }

    public String getPlants() {
        return plants;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNow(String now) {
        this.now = now;
    }

    public void setPlants(String plants) {
        this.plants = plants;
    }
}
