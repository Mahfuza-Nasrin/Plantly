package com.example.plantly;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    private EditText nameEditText, emailEditText, passEditText, confirmEditText;
    private String name, email, pass, confirmPass;
    private final Pattern namePattern = Pattern.compile("[a-z A-Z._]{3,15}");
    private final Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    private final Pattern passPattern = Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$");

    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        nameEditText = findViewById(R.id.name);
        emailEditText = findViewById(R.id.email);
        passEditText = findViewById(R.id.pass);
        confirmEditText = findViewById(R.id.confirm_pass);
        Button btn_signUp = findViewById(R.id.submit);
        TextView tb_signIn = findViewById(R.id.login);
        progressBar = findViewById(R.id.progressBar);
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        btn_signUp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameEditText.getText().toString().trim();
                email = emailEditText.getText().toString();
                pass = passEditText.getText().toString();
                confirmPass = confirmEditText.getText().toString();

                if (name.isEmpty()){
                    nameEditText.setError("Empty!!");
                    nameEditText.requestFocus();
                } else if (!namePattern.matcher(name).matches()){
                    nameEditText.setError("Name can be only Alphabet");
                    nameEditText.requestFocus();
                } else if (email.isEmpty()){
                    emailEditText.setError("Empty!!");
                    emailEditText.requestFocus();
                }else if (!emailPattern.matcher(email).matches()){
                    emailEditText.setError("Invalid email!");
                    emailEditText.requestFocus();
                }
                else if (pass.isEmpty()){
                    passEditText.setError("Empty!!");
                    passEditText.requestFocus();
                }else if (!passPattern.matcher(pass).matches()) {
                    confirmEditText.setError("Password must be at least 8 characters long\ncontain at least one uppercase letter\none lowercase letter\nand one digit.");
                    passEditText.requestFocus();

                }else if (!pass.equals(confirmPass)) {
                    confirmEditText.setError("Passwords doesn't match");
                    passEditText.requestFocus();
                    passEditText.setText("");
                    confirmEditText.setText("");
                }else {
                    progressBar.setVisibility(View.VISIBLE);
                    auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressBar.setVisibility(View.GONE);
                            if (task.isSuccessful()){
                                FirebaseUser user = auth.getCurrentUser();
                                sendEmailVerification(user);  // Send email verification

                                assert user != null;
                                DocumentReference df = firestore.collection("Users").document(user.getUid());
                                Map<String, String> userInfo = new HashMap<>();
                                userInfo.put("email", user.getEmail());
                                userInfo.put("name", name);
                                userInfo.put("uid", user.getUid());
                                df.set(userInfo);
                                Toast.makeText(getApplicationContext(), "Successfully Registered!!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), SignInActivity.class));
                                finish();
                            } else{
                                if (task.getException() instanceof FirebaseAuthUserCollisionException){
                                    Toast.makeText(getApplicationContext(), "User Already Exist!!", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(getApplicationContext(), "Error: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                }
            }
        });

        tb_signIn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignInActivity.class));
                finish();
            }
        });
    }
    private void sendEmailVerification(FirebaseUser user) {
        if (user != null && !user.isEmailVerified()) {
            user.sendEmailVerification()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(this, "Verification email sent.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Error sending verification email.", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}