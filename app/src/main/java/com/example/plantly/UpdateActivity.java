package com.example.plantly;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class UpdateActivity extends AppCompatActivity {
    private ImageView plantImageView,iv_back;
    private static final int IMAGE_REQ = 1;
    private EditText et_plant_name;
    private EditText et_plant_type;
    private EditText et_plant_price;

    ;
    private String plantName, plantType, image,plantPrice;
    private int plantPriceInt;

    Button button;
    private Uri imagePath;
    private DatabaseReference reference;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        plantImageView = findViewById(R.id.plantImageView);
        iv_back = findViewById(R.id.iv_back_edit_item);
        et_plant_name = findViewById(R.id.et_plant_name);
        et_plant_type = findViewById(R.id.et_plant_type);
        et_plant_price = findViewById(R.id.et_plant_price);


        button = findViewById(R.id.add);

        progressBar = findViewById(R.id.progressBar);
        reference = FirebaseDatabase.getInstance().getReference().child("Plant Items");

        plantName = getIntent().getStringExtra("plantName");
        plantType = getIntent().getStringExtra("plantType");
        plantPrice = getIntent().getStringExtra("plantPrice");
        image = getIntent().getStringExtra("image");

        Picasso.get().load(image).into(plantImageView);
        et_plant_name.setText(plantName);
        et_plant_type.setText(plantType);
        et_plant_price.setText(plantPrice);


        plantImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(UpdateActivity.this, android.Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent, IMAGE_REQ);
                } else {
                    ActivityCompat.requestPermissions(UpdateActivity.this, new String[]{
                            Manifest.permission.READ_MEDIA_IMAGES
                    }, IMAGE_REQ);
                }
                }

        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plantName = et_plant_name.getText().toString().trim();
                plantType = et_plant_type.getText().toString().trim();
                plantPrice = et_plant_price.getText().toString().trim();
                if (imagePath == null){
                    Toast.makeText(UpdateActivity.this, "Please Select an Image", Toast.LENGTH_SHORT).show();
                } else if(plantName.isEmpty()){
                    et_plant_name.setError("Empty");
                    et_plant_name.requestFocus();
                } else if(plantType.isEmpty()){
                    et_plant_type.setError("Empty");
                    et_plant_type.requestFocus();
                } else if (plantPrice.isEmpty()) {
                    et_plant_price.setError("Empty");
                    et_plant_price.requestFocus();
                }
                else {
                    progressBar.setVisibility(View.VISIBLE);
                    uploadImage();
                }
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateActivity.this,ReadActivity.class);
                startActivity(intent);
            }
        });


    }

    private void uploadImage() {
        MediaManager.get().upload(imagePath).callback(new UploadCallback() {
            @Override
            public void onStart(String requestId) {

            }

            @Override
            public void onProgress(String requestId, long bytes, long totalBytes) {

            }

            @Override
            public void onSuccess(String requestId, Map resultData) {
                image = (String) resultData.get("secure_url");
                uploadData(image);
            }

            @Override
            public void onError(String requestId, ErrorInfo error) {

            }

            @Override
            public void onReschedule(String requestId, ErrorInfo error) {

            }
        }).dispatch();
    }


    private void uploadData(String image) {
        String key = getIntent().getStringExtra("key");
        plantPriceInt = Integer.parseInt(plantPrice);
        HashMap<String, Object> data = new HashMap<>();
        data.put("plantName", plantName);
        data.put("plantType", plantType);
        data.put("plantPrice", plantPriceInt);
        data.put("imageUrl", image);
        data.put("key", key);
        progressBar.setVisibility(View.GONE);
        reference.child(key).updateChildren(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                et_plant_name.setText("");
                et_plant_type.setText("");
                et_plant_price.setText("");
                imagePath = null;
                plantImageView.setImageResource(R.drawable.ic_add_photo);
                Toast.makeText(getApplicationContext(), "Updated Successfully!!", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Failed!!" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQ && resultCode == RESULT_OK && data != null){
            imagePath = data.getData();
            Picasso.get().load(imagePath).into(plantImageView);
        }
    }
}