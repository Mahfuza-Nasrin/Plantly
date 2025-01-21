package com.example.plantly;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DescriptionActivity extends AppCompatActivity {


        private ImageView btnArrowBack, descriptionPlantImageView;
        private TextView plantNameDescription, tvPlantPrice, tvQuantity, plantDescription;
        private Button btnIncrease, btnDecrease;
        private LinearLayout btnAddToCart;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_description);


            btnArrowBack = findViewById(R.id.btnArrowBack);
            descriptionPlantImageView = findViewById(R.id.descriptionPlantImageView);
            plantNameDescription = findViewById(R.id.plantNameDescription);
            tvPlantPrice = findViewById(R.id.tvPlantPrice);
            tvQuantity = findViewById(R.id.tvQuantity);
            plantDescription = findViewById(R.id.plantDescription);

            btnIncrease = findViewById(R.id.btnIncrease);
            btnDecrease = findViewById(R.id.btnDecrease);


            btnArrowBack.setOnClickListener(v -> {
                startActivity(new Intent(DescriptionActivity.this, HomePageActivity.class));
                finish();
            });



            btnIncrease.setOnClickListener(v -> {

                try {
                    int quantity = Integer.parseInt(tvQuantity.getText().toString());
                    quantity++;
                    tvQuantity.setText(String.valueOf(quantity));
                } catch (NumberFormatException e) {

                    tvQuantity.setText("0");
                }


            });
            btnDecrease.setOnClickListener(v -> {
                try {
                    int quantity = Integer.parseInt(tvQuantity.getText().toString());
                    quantity--;
                    tvQuantity.setText(String.valueOf(quantity));
                } catch (NumberFormatException e) {

                    tvQuantity.setText("0");
                }
            });
        }


}