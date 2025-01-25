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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class PlaceOrderActivity extends AppCompatActivity {

    RelativeLayout layoutPlaceOrder;
    private ImageView btnArrowBackCart;
    private TextView userNameTextView, emailTextView, totalPriceTextView, orderItemNameTextView,orderItemPriceTextView;
    private EditText deliveryAddressEditText;
    private LinearLayout btnPlaceOrder, layoutOrderSuccessful;
    private ArrayList<CartItem> orderItems;
    FirebaseAuth auth;
    FirebaseUser currentUser;
    private int totalPrice = 0,quantity,itemPrice;
    String plantName,plantType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);

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

        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();

        if (currentUser != null) {
            String userId = currentUser.getUid();

            DocumentReference df = FirebaseFirestore.getInstance()
                    .collection("Users")
                    .document(userId);

            df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        String userName = documentSnapshot.getString("name");
                        String email = documentSnapshot.getString("email");

                        userNameTextView.setText("Name:" + userName );
                        emailTextView.setText("Email: "+email);

                    }
                }
            });
        }




        // Get order details (passed from CartActivity)
        Intent intent = getIntent();
        orderItems = (ArrayList<CartItem>) intent.getSerializableExtra("orderItems");
        if (orderItems != null) {
            for (CartItem item : orderItems) {
                totalPrice += item.getPrice() * item.getQuantity();
                plantName = item.getName();
                quantity = item.getQuantity();
                itemPrice= item.getPrice();


            }
        }else {
            plantName = getIntent().getStringExtra("plantName");
            String quantityStr2 = getIntent().getStringExtra("quantity");
            String itemPrice2 = getIntent().getStringExtra("plantPrice");
            quantity = Integer.parseInt(quantityStr2);
            itemPrice = Integer.parseInt(itemPrice2);
            totalPrice+=quantity*itemPrice;

        }
        String strQuantity = String.valueOf(quantity);
        String strPrice = String.valueOf(itemPrice);
        totalPriceTextView.setText(totalPrice + " Tk");
        orderItemNameTextView.setText(strQuantity+"x "+plantName);
        orderItemPriceTextView.setText(strPrice + " Tk");




        // Back button functionality
        btnArrowBackCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlaceOrderActivity.this, HomePageActivity.class);
                startActivity(intent);
            }
        });

        // Place Order Button Click
        btnPlaceOrder.setOnClickListener(v -> {
            String deliveryAddress = deliveryAddressEditText.getText().toString().trim();
            if (deliveryAddress.isEmpty()) {
                Toast.makeText(this, "Please enter your delivery address!", Toast.LENGTH_SHORT).show();
                return;
            }



            placeOrder(deliveryAddress);
        });
    }


    private void placeOrder(String deliveryAddress) {
        // Simulate saving the order to a database or API call here

        Toast.makeText(this, "Order placed successfully!", Toast.LENGTH_SHORT).show();



        // Display success layout
        layoutOrderSuccessful.setVisibility(View.VISIBLE);
        layoutPlaceOrder.setVisibility(View.GONE);
        // Clear the cart and reset fields
        CartManager.clearCart();
        deliveryAddressEditText.setText("");

        // Optionally, navigate back to the home screen after order placement
        layoutOrderSuccessful.findViewById(R.id.btnDone).setOnClickListener(v -> {
            Intent intent = new Intent(PlaceOrderActivity.this, HomePageActivity.class);
            startActivity(intent);
            finish();
        });
    }
}





