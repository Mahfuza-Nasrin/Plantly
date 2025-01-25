package com.example.plantly;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.cloudinary.android.MediaManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.HashMap;
import java.util.Map;

@SuppressLint("CustomSplashScreen")
public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash_screen);
        try {
            initConfig();
        } catch (Exception e) {
            Log.d("Media", String.valueOf(e));
        }

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(currentUser!= null && currentUser.isEmailVerified()){ // Check if the user is logged in
                    startActivity(new Intent(SplashScreen.this, HomePageActivity.class));
                    finish();
                }
                else{
                    startActivity(new Intent(SplashScreen.this, MainActivity.class));
                    finish();
                }
            }
        }, 3000);
    }

    private void initConfig() {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", "dmt46v0wh");
        config.put("api_key", "966486171758756");
        config.put("api_secret", "hGKCMCn9kd-F5kqmXPbtCMSYnDw");
//        config.put("secure", true);
        MediaManager.init(this, config);
    }
}