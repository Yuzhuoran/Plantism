package com.waterme.plantism;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhuoran on 3/9/18.
 */

public class User {

    private String name;
    private String email;
    private List<String> plantsIdList;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
        plantsIdList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<String> getPlantsIdList() {
        return plantsIdList;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) { this.name = name;}

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPlantsIdList(List<String> plantsIdList) {
        this.plantsIdList = plantsIdList;
    }
}
