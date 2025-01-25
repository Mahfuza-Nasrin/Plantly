package com.example.plantly;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    private ImageView btnArrowBackCart, btnDeleteCart;
    private RecyclerView recyclerViewCart;
    private LinearLayout btnReviewAddress;

    private CartAdapter cartAdapter;
    private ArrayList<CartItem> cartItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // Initialize views
        btnArrowBackCart = findViewById(R.id.btnArrowBackCart);
        btnDeleteCart = findViewById(R.id.btnDeleteCart);
        recyclerViewCart = findViewById(R.id.recyclerViewCart);
        btnReviewAddress = findViewById(R.id.btnReviewAddress);

        // Get cart items
        cartItems = (ArrayList<CartItem>) CartManager.getCartItems();

        // Set up RecyclerView
        recyclerViewCart.setLayoutManager(new LinearLayoutManager(this));
        cartAdapter = new CartAdapter(this, cartItems);
        recyclerViewCart.setAdapter(cartAdapter);

        // Back button functionality
        btnArrowBackCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this, HomePageActivity.class);
                startActivity(intent);
            }
        });

        // Delete all selected items
        btnDeleteCart.setOnClickListener(v -> {
            ArrayList<CartItem> selectedItems = cartAdapter.getSelectedItems();
            if (!selectedItems.isEmpty()) {
                cartItems.removeAll(selectedItems);
                cartAdapter.notifyDataSetChanged();
                Toast.makeText(CartActivity.this, "Selected items removed!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(CartActivity.this, "No items selected!", Toast.LENGTH_SHORT).show();
            }
        });

        // Navigate to Review Address
        btnReviewAddress.setOnClickListener(v -> {
            if (!cartItems.isEmpty()) {
                Intent intent = new Intent(CartActivity.this, PlaceOrderActivity.class);
                intent.putExtra("orderItems", cartItems); // Ensure CartItem implements Serializable
                startActivity(intent);

            } else {
                Toast.makeText(CartActivity.this, "Add items to the cart first!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
