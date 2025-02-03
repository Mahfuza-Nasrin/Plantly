package com.example.plantly;

import java.io.Serializable;

public class CartItem implements Serializable {
    private String name;
    private String type;
    private String imageUrl;
    private int price;

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    private int quantity;

    public CartItem(String name, String type, String imageUrl, int price, int quantity) {
        this.name = name;
        this.type = type;
        this.imageUrl = imageUrl;
        this.price = price;
        this.quantity = quantity;
    }


    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}

