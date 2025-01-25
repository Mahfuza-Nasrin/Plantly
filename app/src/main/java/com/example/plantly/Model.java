package com.example.plantly;

public class Model {
    private String plantName, plantType, imageUrl, key;
    private int plantPrice;

    public Model(){}

    public Model(String plantName, String plantType, int plantPrice, String imageUrl, String key) {
        this.plantName = plantName;
        this.plantType = plantType;
        this.imageUrl = imageUrl;
        this.key = key;
        this.plantPrice = plantPrice;
    }


    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public String getPlantType() {
        return plantType;
    }

    public void setPlantType(String plantType) {
        this.plantType = plantType;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getPlantPrice() {
        return plantPrice;
    }

    public void setPlantPrice(int plantPrice) {
        this.plantPrice = plantPrice;
    }
}
