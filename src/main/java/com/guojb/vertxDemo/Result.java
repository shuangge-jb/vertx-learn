package com.guojb.vertxDemo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Result {
    private List<Feature> features;

    public Result() {
        this.features = new ArrayList<>(1);
        features.add(new Feature("is_support_restart","description"));
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(List<Feature> features) {
        this.features = features;
    }
}
class Feature{
    private String name;
    private String desc;

    public Feature(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
