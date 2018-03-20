package com.waterme.plantism;

import java.util.List;

/**
 * Created by zhuoran on 3/10/18.
 * Plant object
 */

public class Plant {
    private String name;
    private String sensor_id;
    private String history;
    private String now;
    private String imgUrl;

    public Plant(String name, String sensor_id, String histroy, String now, String imgUrl) {
        this.name = name;
        this.sensor_id = sensor_id;
        this.history = histroy;
        this.now = now;
        this.imgUrl = imgUrl;
    }

}
