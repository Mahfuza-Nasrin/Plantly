package com.example.plantly;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity {

    private EditText emailEditText, passEditText;
    private String email, pass;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        emailEditText = findViewById(R.id.email);
        passEditText = findViewById(R.id.pass);
        Button btn_signIn = findViewById(R.id.btn_signIn);
        TextView tv_signUp = findViewById(R.id.tv_signUp);
        TextView tv_forgetPassword = findViewById(R.id.tv_forgetPassword);
        progressBar = findViewById(R.id.progressBar);
        auth = FirebaseAuth.getInstance();

        btn_signIn.setOnClickListener(v -> {
            email = emailEditText.getText().toString().trim();
            pass = passEditText.getText().toString();

            if (email.isEmpty()) {
                emailEditText.setError("Empty Email!!");
                emailEditText.requestFocus();
            } else if (pass.isEmpty()) {
                passEditText.setError("Empty Password!!");
                passEditText.requestFocus();
            } else {
                progressBar.setVisibility(View.VISIBLE);

                auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        FirebaseUser user = auth.getCurrentUser();
                        if (task.isSuccessful()) {
                            if (user != null && user.isEmailVerified()) {

                                if ("AdminPass01".equals(pass) && "fardusjannatul2731@gmail.com".equals(email)) {
                                    Toast.makeText(getApplicationContext(), "Admin Login successful!!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), AdminHomePage.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                } else {
                                    Toast.makeText(getApplicationContext(), "Login successful!!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), HomePageActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                }
                                finish();
                            } else {
                                // Email not verified
                                Toast.makeText(getApplicationContext(), "Please verify your email.", Toast.LENGTH_SHORT).show();
                                auth.signOut();
                            }
                        } else {

                            Toast.makeText(getApplicationContext(), "SignIn Failed!!", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }




        });
        tv_forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                if (!email.isEmpty()) {
                    sendPasswordResetEmail(email);
                } else {

                    emailEditText.setError("\"Please enter your email\"");
                    emailEditText.requestFocus();
                }
            }
        });


        tv_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
                finish();
            }
        });

    }
    private void sendPasswordResetEmail(String email) {
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(
                                SignInActivity.this,
                                "Password reset email sent to " + email,
                                Toast.LENGTH_SHORT
                        ).show();
                    } else {
                        if (task.getException() != null) {
                            Toast.makeText(
                                    SignInActivity.this,
                                    "Error: " + task.getException().getMessage(),
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }
                });
    }
}

