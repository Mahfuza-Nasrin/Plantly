package com.example.plantly;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlaceOrderActivity extends AppCompatActivity {

    private RelativeLayout layoutPlaceOrder;
    private ImageView btnArrowBackCart;
    private TextView userNameTextView, emailTextView, totalPriceTextView, orderItemNameTextView, orderItemPriceTextView;
    private EditText deliveryAddressEditText;
    private LinearLayout btnPlaceOrder, layoutOrderSuccessful;
    private ArrayList<CartItem> orderItems;
    private FirebaseAuth auth;
    private FirebaseUser currentUser;
    private DatabaseReference reference;
    private int totalPrice = 0;
    private String userName, email, userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);


        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Orders");

        // Initialize views
        btnArrowBackCart = findViewById(R.id.btnArrowBackCart);
        userNameTextView = findViewById(R.id.userNameTextView);
        emailTextView = findViewById(R.id.EmailTextView);
        deliveryAddressEditText = findViewById(R.id.deliveryAddressEditText);
        totalPriceTextView = findViewById(R.id.totalPriceTextView);
        btnPlaceOrder = findViewById(R.id.btnPlaceOrder);
        layoutOrderSuccessful = findViewById(R.id.layout_orderSuccessful);
        layoutPlaceOrder = findViewById(R.id.layoutPlaceOrder);
        orderItemNameTextView = findViewById(R.id.orderItemNameTextView);
        orderItemPriceTextView = findViewById(R.id.orderItemPriceTextView);


        if (currentUser != null) {
            userId = currentUser.getUid();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference df = db.collection("Users").document(userId);
            df.get().addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {
                    userName = documentSnapshot.getString("name");
                    email = documentSnapshot.getString("email");

                    userNameTextView.setText("Name: " + userName);
                    emailTextView.setText("Email: " + email);
                }
            });
        }


        Intent intent = getIntent();
        orderItems = (ArrayList<CartItem>) intent.getSerializableExtra("orderItems");

        StringBuilder itemNamesBuilder = new StringBuilder();
        StringBuilder itemPricesBuilder = new StringBuilder();

        if (orderItems != null && !orderItems.isEmpty()) {
            for (CartItem item : orderItems) {
                if (item.getQuantity() > 0) {
                    int itemTotalPrice = item.getPrice() * item.getQuantity();
                    totalPrice += itemTotalPrice;


                    itemNamesBuilder.append(item.getQuantity()).append("x ").append(item.getName()).append("\n");
                    itemPricesBuilder.append(itemTotalPrice).append(" Tk\n");
                }
            }
        }


        orderItemNameTextView.setText(itemNamesBuilder.toString().trim());
        orderItemPriceTextView.setText(itemPricesBuilder.toString().trim());
        totalPriceTextView.setText(totalPrice + " Tk");


        btnArrowBackCart.setOnClickListener(v -> {
            Intent backIntent = new Intent(PlaceOrderActivity.this, HomePageActivity.class);
            startActivity(backIntent);
            finish();
        });


        btnPlaceOrder.setOnClickListener(v -> {
            String deliveryAddress = deliveryAddressEditText.getText().toString().trim();
            if (deliveryAddress.isEmpty()) {
                Toast.makeText(this, "Please enter your delivery address!", Toast.LENGTH_SHORT).show();
                return;
            }
            saveOrderToRealtimeDatabase(deliveryAddress);
        });
    }

    private void saveOrderToRealtimeDatabase(String deliveryAddress) {
        if (currentUser == null) return;


        String orderId = reference.push().getKey();


        Map<String, Object> orderData = new HashMap<>();
        orderData.put("orderId", orderId);
        orderData.put("userId", userId);
        orderData.put("userName", userName);
        orderData.put("email", email);
        orderData.put("deliveryAddress", deliveryAddress);
        orderData.put("totalPrice", totalPrice);


        ArrayList<Map<String, Object>> orderItemsList = new ArrayList<>();
        for (CartItem item : orderItems) {
            if (item.getQuantity() > 0) {
                Map<String, Object> orderItem = new HashMap<>();
                orderItem.put("name", item.getName());
                orderItem.put("quantity", item.getQuantity());
                orderItem.put("price", item.getPrice());
                orderItem.put("imageUrl", item.getImageUrl()); // Store image URL
                orderItemsList.add(orderItem);
            }
        }
        orderData.put("orderItems", orderItemsList);


        if (orderId != null) {
            reference.child(orderId).setValue(orderData)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(this, "Order placed successfully!", Toast.LENGTH_SHORT).show();


                        layoutOrderSuccessful.setVisibility(View.VISIBLE);
                        layoutPlaceOrder.setVisibility(View.GONE);


                        CartManager.clearCart();
                        deliveryAddressEditText.setText("");

                        layoutOrderSuccessful.findViewById(R.id.btnDone).setOnClickListener(v -> {
                            Intent intent = new Intent(PlaceOrderActivity.this, HomePageActivity.class);
                            startActivity(intent);
                            finish();
                        });
                    })
                    .addOnFailureListener(e -> Toast.makeText(this, "Failed to place order!", Toast.LENGTH_SHORT).show());
        }
    }
}