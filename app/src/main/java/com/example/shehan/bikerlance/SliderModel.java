package com.example.shehan.bikerlance;

public class SliderModel {

    private String banner;
    private String backgroundColor;

    public SliderModel (String banner, String backgroundColor){
        this.banner = banner;
        this.backgroundColor = backgroundColor;
    }

    public String getBanner(){
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getBackgroundColor(){
        return backgroundColor;
    }
}
