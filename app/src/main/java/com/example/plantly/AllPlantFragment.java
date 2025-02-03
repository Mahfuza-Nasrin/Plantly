package com.example.plantly;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AllPlantFragment extends Fragment {

    private DatabaseReference reference;
    private ArrayList<Model> arrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private PlantAdapter adapter;

    public AllPlantFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_all_plant, container, false);


        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2)); // Grid layout with 2 columns


        reference = FirebaseDatabase.getInstance().getReference().child("Plant Items");


        adapter = new PlantAdapter(getContext(), arrayList);
        recyclerView.setAdapter(adapter);


        loadAllPlants();

        return view;
    }

    private void loadAllPlants() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Model data = dataSnapshot.getValue(Model.class);
                        if (data != null) {
                            arrayList.add(data);
                        }
                    }
                    adapter.updateList(arrayList); // Update adapter with all plants
                } else {
                    Log.d("AllPlantFragment", "No plants found!");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("AllPlantFragment", "Error: " + error.getMessage());
            }
        });
    }


}
