package com.waterme.plantism.model;

import java.util.List;
import java.util.Map;

/**
 * Created by zhuoran on 3/10/18.
 * Plant object
 */

public class Plant {
    private String category;
    private Map<String, Map<String, HistoryData>> history;
    private String imgUrl;

    public Plant(String category, Map history, String imgUrl) {
        this.category = category;
        this.history = history;
        this.imgUrl = imgUrl;
    }

    public Plant(){}

    public Map<String, Map<String, HistoryData>> getHistory() {
        return history;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setHistory(Map history) {
        this.history = history;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
