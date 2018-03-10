package com.waterme.plantism;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhuoran on 3/9/18.
 */

public class User {

    private String name;
    private List<String> plantsIdList;

    public User(String name) {
        this.name = name;
        plantsIdList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addPlantId(String plantId) {
        plantsIdList.add(plantId);
    }

    public List<String> getPlantsIdList() {
        return plantsIdList;
    }
}
