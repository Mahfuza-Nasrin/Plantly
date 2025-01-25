package com.example.plantly;

import static com.example.plantly.HomePageActivity.searchEditText;
import static com.example.plantly.HomePageActivity.searchPlant;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;



public class AllPlantFragment extends Fragment {


    public AllPlantFragment() {

    }

    private DatabaseReference reference;
    private ArrayList<Model> arrayList;

    EditText search;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_plant, container, false);


        // Initialize RecyclerView

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2)); // 2 columns




        reference = FirebaseDatabase.getInstance().getReference().child("Plant Items");
        if (!searchPlant.isEmpty()){
            Query query = reference.orderByChild("plantName").equalTo(searchPlant); // Filter by "type" field

            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    arrayList = new ArrayList<>();

                    if (snapshot.exists()) {
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            Model data = snapshot1.getValue(Model.class);
                            if (data != null) {
                                arrayList.add(data);
                            }
                        }



                        PlantAdapter adapter = new PlantAdapter(requireContext(),arrayList);
                        recyclerView.setAdapter(adapter);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle database error here
                }
            });
            searchPlant="";
            searchEditText.setText("");
        } else {


            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    arrayList = new ArrayList<>();

                    if (snapshot.exists()) {
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            Model data = snapshot1.getValue((Model.class));
                            arrayList.add(data);
                        }
                        PlantAdapter adapter = new PlantAdapter(requireContext(), arrayList);
                        recyclerView.setAdapter(adapter);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        return view;

    }


}