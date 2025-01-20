package com.example.plantly;

public class PlantModel {


        private String name;
        private int imageResourceId;
        private int plantPrice;

        public PlantModel(String name, int imageResourceId,int plantPrice) {
            this.name = name;
            this.imageResourceId = imageResourceId;
            this.plantPrice = plantPrice;

        }

        public  String getName() {
            return name;
        }

        public int getImageResourceId() {
            return imageResourceId;
        }
    public int getPlantPrice() {
        return plantPrice;
    }





}
