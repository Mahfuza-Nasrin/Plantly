package com.example.plantly;

public class PlantModel {


        private String name;
        private int imageResourceId;

        public PlantModel(String name, int imageResourceId) {
            this.name = name;
            this.imageResourceId = imageResourceId;
        }

        public  String getName() {
            return name;
        }

        public int getImageResourceId() {
            return imageResourceId;
        }



}
