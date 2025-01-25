package com.example.plantly;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
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
    public static ImageView searchIcon;
    public static EditText searchEditText;
    public static String searchPlant="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);


        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();



        //textGreeting

        tvGreeting = findViewById(R.id.tvGreeting);
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

                        if (userName != null && !userName.isEmpty()) {
                            tvGreeting.setText("Hello, " + userName + "!");
                        }
                    }
                }
            });
        }


        searchIcon = findViewById(R.id.ic_search);
        searchEditText = findViewById(R.id.searchBar);
       searchIcon.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               searchPlant = searchEditText.getText().toString().trim();
           }
       });



        // find view
        tabLayout = findViewById(R.id.tabLayout);
        viewPager2 = findViewById(R.id.viewPager2);


        // Set up the adapter

        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        viewPager2.setAdapter(adapter);

        // Link TabLayout and ViewPager2
        new TabLayoutMediator(tabLayout, viewPager2,
                (tab, position) -> {
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














        //bottomNavigationLayout
        bottomNav = findViewById(R.id.bottomNavBar);

        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.ic_home) {
                    Intent intent = new Intent(HomePageActivity.this, HomePageActivity.class);
                    startActivity(intent);
                } else if (item.getItemId() == R.id.ic_wishlist) {
                    Intent intent = new Intent(HomePageActivity.this, WishlistActivity.class);
                    startActivity(intent);
                } else if (item.getItemId() == R.id.ic_cart) {
                    Intent intent = new Intent(HomePageActivity.this, CartActivity.class);
                    startActivity(intent);
                } else if (item.getItemId() == R.id.ic_notification) {
                    Intent intent = new Intent(HomePageActivity.this, NotificationActivity.class);
                    startActivity(intent);
                }



                return true;
            }
        });
    }
}