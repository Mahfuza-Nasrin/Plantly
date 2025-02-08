package com.example.plantly;

import java.util.List;

public class Order {
    private String orderId;
    private String userId;
    private String userName;
    private String email;
    private String deliveryAddress;
    private int totalPrice;
    private List<OrderItem> orderItems;

    public Order() {

    }

    public Order(String orderId, String userId, String userName, String email, String deliveryAddress, int totalPrice, List<OrderItem> orderItems) {
        this.orderId = orderId;
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.deliveryAddress = deliveryAddress;
        this.totalPrice = totalPrice;
        this.orderItems = orderItems;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }
}
