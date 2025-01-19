package com.example.plantly;

public class PlantModel {


        private static String name;
        private int imageResourceId;

        public PlantModel(String name, int imageResourceId) {
            this.name = name;
            this.imageResourceId = imageResourceId;
        }

        public static String getName() {
            return name;
        }

        public int getImageResourceId() {
            return imageResourceId;
        }



}
