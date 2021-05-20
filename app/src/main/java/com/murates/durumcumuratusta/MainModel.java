package com.murates.durumcumuratusta;

class MainModel {
    private String foodsPhoto;
    private Integer foodsPhoto2;
    private String foodsName;
    private String foodsPrice;
    private String foodRating;

    public MainModel(String foodsPhoto, String foodsName, String foodsPrice, String foodRating) {
        this.foodsPhoto = foodsPhoto;
        this.foodsName = foodsName;
        this.foodsPrice = foodsPrice;
        this.foodRating = foodRating;
    }

    public MainModel(Integer foodsPhoto2,String foodsName) {
        this.foodsName = foodsName;
        this.foodsPhoto2=foodsPhoto2;
    }

    public Integer getFoodsPhoto2() {
        return foodsPhoto2;
    }

    public String getImageLink() {
        return foodsPhoto;
    }

    public String getFoodRating() {
        return foodRating;
    }

    public String getFoodsPrice() {
        return foodsPrice;
    }

    public String getFoodsName() {
        return foodsName;
    }
}