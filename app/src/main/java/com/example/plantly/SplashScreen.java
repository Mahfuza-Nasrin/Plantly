package com.example.plantly;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.cloudinary.android.MediaManager;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

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
            Log.e("Media", "Cloudinary initialization failed: ", e);
        }

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();

        new Handler().postDelayed(() -> {
            if (currentUser != null && currentUser.isEmailVerified()) {
                String userId = currentUser.getUid();

                FirebaseFirestore.getInstance()
                        .collection("Users")
                        .document(userId)
                        .get()
                        .addOnSuccessListener(documentSnapshot -> {
                            if (documentSnapshot.exists()) {
                                String pass = documentSnapshot.getString("pass");
                                String email = documentSnapshot.getString("email");

                                if ("AdminPass01".equals(pass) && "fardusjannatul2731@gmail.com".equals(email)) {

                                    startActivity(new Intent(getApplicationContext(), ReadActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                } else {

                                    startActivity(new Intent(getApplicationContext(), HomePageActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                }
                                finish();
                            }
                        })
                        .addOnFailureListener(e -> Log.e("Firestore", "Error fetching user document", e));
            } else {
                startActivity(new Intent(SplashScreen.this, MainActivity.class));
                finish();
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