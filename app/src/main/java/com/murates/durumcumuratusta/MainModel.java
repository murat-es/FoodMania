package com.murates.durumcumuratusta;

class MainModel {
    Integer foodsPhoto;
    String foodsName;

    public MainModel(Integer foodsPhoto, String foodsName) {
        this.foodsPhoto = foodsPhoto;
        this.foodsName = foodsName;
    }

    public Integer getFoodsPhoto() {
        return foodsPhoto;
    }

    public String getFoodsName() {
        return foodsName;
    }
}
