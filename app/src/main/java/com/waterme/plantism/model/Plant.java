package com.waterme.plantism.model;

import java.util.List;

/**
 * Created by zhuoran on 3/10/18.
 * Plant object
 */

public class Plant {
    private String category;
    private String history;
    private String imgUrl;

    public Plant(String category, String history, String imgUrl) {
        this.category = category;
        this.history = history;
        this.imgUrl = imgUrl;
    }

    public String getHistory() {
        return history;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
