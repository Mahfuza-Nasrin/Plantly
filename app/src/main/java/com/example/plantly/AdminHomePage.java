package com.example.plantly;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AdminHomePage extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseUser currentUser;
    TextView tvGreeting;
    ImageView accountImage;
    CardView cvManageItems, cvAllOrders;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_home_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();


        tvGreeting = findViewById(R.id.tvGreeting);
        accountImage = findViewById(R.id.profileIcon);
        cvManageItems= findViewById(R.id.cv_manage_items);
        cvAllOrders = findViewById(R.id.cv_all_orders);

        if (currentUser != null) {
            String userId = currentUser.getUid();
            DocumentReference df = FirebaseFirestore.getInstance().collection("Users").document(userId);
            df.get().addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {
                    String userName = documentSnapshot.getString("name");

                        tvGreeting.setText("Hello, " + userName + "!");

                }
            });
        }

        cvAllOrders.setOnClickListener(v -> {
            Intent intent = new Intent(AdminHomePage.this, OrderListActivity.class);
            startActivity(intent);
        });


        cvManageItems.setOnClickListener(v -> {
            Intent intent = new Intent(AdminHomePage.this, ReadActivity.class);
            startActivity(intent);
        });
        accountImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHomePage.this, AdminAccountManageActivity.class);
                startActivity(intent);
            }
        });
    }
}