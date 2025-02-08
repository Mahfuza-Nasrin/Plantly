package com.example.plantly;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class DescriptionActivity extends AppCompatActivity {

    private ImageView btnArrowBack, descriptionPlantImageView;
    private TextView plantNameDescription, tvPlantPrice, tvQuantity, plantTypeTextView;
    private Button btnIncrease, btnDecrease;
    private LinearLayout btnAddToCart, btnBuyNow;
    private String plantName, plantType, image, plantPrice;
    private int plantPriceInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);


        btnArrowBack = findViewById(R.id.btnArrowBack);
        descriptionPlantImageView = findViewById(R.id.descriptionPlantImageView);
        plantNameDescription = findViewById(R.id.plantNameDescription);
        tvPlantPrice = findViewById(R.id.tvPlantPrice);
        tvQuantity = findViewById(R.id.tvQuantity);
        plantTypeTextView = findViewById(R.id.plantTypeTextView);
        btnIncrease = findViewById(R.id.btnIncrease);
        btnDecrease = findViewById(R.id.btnDecrease);
        btnAddToCart = findViewById(R.id.btnAddToCart);



        plantName = getIntent().getStringExtra("plantName");
        plantType = getIntent().getStringExtra("plantType");
        plantPrice = getIntent().getStringExtra("plantPrice");
        image = getIntent().getStringExtra("image");


        plantNameDescription.setText(plantName);
        plantTypeTextView.setText(plantType);
        tvPlantPrice.setText(plantPrice + " TK");
        Picasso.get().load(image).into(descriptionPlantImageView);


        btnArrowBack.setOnClickListener(v -> finish());

        // Increase quantity
        btnIncrease.setOnClickListener(v -> {
            try {
                int quantity = Integer.parseInt(tvQuantity.getText().toString());
                quantity++;
                tvQuantity.setText(String.valueOf(quantity));
            } catch (NumberFormatException e) {
                tvQuantity.setText("1");
            }
        });


        btnDecrease.setOnClickListener(v -> {
            try {
                int quantity = Integer.parseInt(tvQuantity.getText().toString());
                if (quantity > 0) {
                    quantity--;
                }
                tvQuantity.setText(String.valueOf(quantity));
            } catch (NumberFormatException e) {
                tvQuantity.setText("1");
            }
        });


        btnAddToCart.setOnClickListener(v -> {
            String quantityStr = tvQuantity.getText().toString();
            int quantity = quantityStr.isEmpty() ? 1 : Integer.parseInt(quantityStr);
            if (quantity > 0) {
                CartManager.addToCart(new CartItem(plantName, plantType, image, Integer.parseInt(plantPrice), quantity));
                Toast.makeText(this, plantName + " added to cart!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Please select a valid quantity", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
