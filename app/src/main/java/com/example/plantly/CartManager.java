package com.example.plantly;

import java.util.ArrayList;

public class CartManager {

    private static ArrayList<CartItem> cartItems = new ArrayList<>();

    public static ArrayList<CartItem> getCartItems() {
        return cartItems;
    }

    public static void clearSelectedItems(ArrayList<CartItem> selectedItems) {
        cartItems.removeAll(selectedItems);
    }

    public static void clearCart() {
        cartItems.clear();
    }
    public static void addToCart(CartItem item) {
        cartItems.add(item);
    }
}
