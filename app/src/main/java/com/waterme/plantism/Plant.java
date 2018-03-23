package com.waterme.plantism;

import java.util.List;

/**
 * Created by zhuoran on 3/10/18.
 * Plant object
 */

public class Plant {
    private String name;
    private String history;
    private String imgUrl;

    public Plant(String name, String history, String imgUrl) {
        this.name = name;
        this.history = history;
        this.imgUrl = imgUrl;
    }

    public String getHistory() {
        return history;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setName(String name) {
        this.name = name;
    }
}
