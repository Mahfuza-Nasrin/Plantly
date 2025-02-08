package com.example.plantly;

public class OrderItem {
    private String name;
    private int quantity;
    private int price;
    private String imageUrl;

    public OrderItem() {

    }

    public OrderItem(String name, int quantity, int price, String imageUrl) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
