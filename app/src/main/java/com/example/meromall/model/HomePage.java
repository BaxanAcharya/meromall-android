package com.example.meromall.model;

import java.util.ArrayList;
import java.util.List;

public class HomePage {
    private int type;
    private List<Slider> sliderList = new ArrayList<>();

    public HomePage(int type, List<Slider> sliderList) {
        this.type = type;
        this.sliderList = sliderList;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<Slider> getSliderList() {
        return sliderList;
    }

    public void setSliderList(List<Slider> sliderList) {
        this.sliderList = sliderList;
    }
}
