package com.example.plantly;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.buttonStart);
        button.setOnClickListener(v -> {
            //Toast.makeText(MainActivity.this, "Button clicked!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(MainActivity.this, SignInActivity.class);
            startActivity(intent);
        });
    }
}