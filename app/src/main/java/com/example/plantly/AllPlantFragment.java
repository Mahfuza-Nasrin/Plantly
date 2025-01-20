package com.example.plantly;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;



public class AllPlantFragment extends Fragment {


    public AllPlantFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_plant, container, false);


        // Initialize RecyclerView

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2)); // 2 columns

        // Create sample data
        List<PlantModel> plantList = new ArrayList<>();
        plantList.add(new PlantModel("Rose", R.drawable.rose_image ,100));
        plantList.add(new PlantModel("Tulip", R.drawable.tulip_image,200));
        plantList.add(new PlantModel("Cactus", R.drawable.cactus_image,300));
        plantList.add(new PlantModel("Sunflower", R.drawable.sunflower_image ,400));
        plantList.add(new PlantModel("Jasmine", R.drawable.jasmine_image,500));
        plantList.add(new PlantModel("Lavender", R.drawable.lavender_image,600));

        // Set up adapter
        PlantAdapter adapter = new PlantAdapter(plantList);
        recyclerView.setAdapter(adapter);

        return view;


    }
}