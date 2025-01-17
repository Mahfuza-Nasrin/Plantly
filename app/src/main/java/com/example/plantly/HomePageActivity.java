package com.example.plantly;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class HomePageActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);



        bottomNav = findViewById(R.id.bottomNavBar);
        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.ic_home) {
                    Toast.makeText(HomePageActivity.this, "home button", Toast.LENGTH_SHORT).show();
                } else if (item.getItemId() == R.id.ic_wishlist) {
                    Toast.makeText(HomePageActivity.this, "wishlist button", Toast.LENGTH_SHORT).show();
                } else if (item.getItemId() == R.id.ic_cart) {
                    Toast.makeText(HomePageActivity.this, "cart button", Toast.LENGTH_SHORT).show();
                } else if (item.getItemId() == R.id.ic_notification) {
                    Toast.makeText(HomePageActivity.this, "notification button", Toast.LENGTH_SHORT).show();
                }



                return true;
            }
        });
    }
}