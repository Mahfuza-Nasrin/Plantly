package com.example.plantly;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomePageActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;
    FirebaseAuth auth;
    FirebaseUser currentUser;
    TextView tvGreeting;
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    public static EditText etSearchBar;
    private AllPlantFragment allPlantFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();

        // Greeting text
        tvGreeting = findViewById(R.id.tvGreeting);
        if (currentUser != null) {
            String userId = currentUser.getUid();
            DocumentReference df = FirebaseFirestore.getInstance().collection("Users").document(userId);
            df.get().addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {
                    String userName = documentSnapshot.getString("name");
                    if (userName != null && !userName.isEmpty()) {
                        tvGreeting.setText("Hello, " + userName + "!");
                    }
                }
            });
        }


        allPlantFragment = new AllPlantFragment();


        tabLayout = findViewById(R.id.tabLayout);
        viewPager2 = findViewById(R.id.viewPager2);
        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        viewPager2.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("All");
                    break;
                case 1:
                    tab.setText("Indoor");
                    break;
                case 2:
                    tab.setText("Outdoor");
                    break;
            }
        }).attach();



        // Bottom navigation setup
        bottomNav = findViewById(R.id.bottomNavBar);
        bottomNav.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.ic_home) {
                return true;
            } else if (item.getItemId() == R.id.ic_wishlist) {
                startActivity(new Intent(HomePageActivity.this, WishlistActivity.class));
            } else if (item.getItemId() == R.id.ic_cart) {
                startActivity(new Intent(HomePageActivity.this, CartActivity.class));
            } else if (item.getItemId() == R.id.ic_account_setting) {
                startActivity(new Intent(HomePageActivity.this, AccountManageActivity.class));
            }
            return true;
        });
    }
}
