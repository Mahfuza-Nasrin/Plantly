package com.example.plantly;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PlantAdapter extends RecyclerView.Adapter<PlantAdapter.PlantViewHolder> {

    private ArrayList<Model> plantList;

    private Context context;

    public PlantAdapter(Context context, ArrayList<Model> plantList) {
        this.context = context;
        this.plantList = new ArrayList<>(plantList);

    }

    @NonNull
    @Override
    public PlantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_plant, parent, false);
        return new PlantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlantViewHolder holder, int position) {
        Model model = plantList.get(position);
        holder.plantNameTextView.setText(model.getPlantName());
        String plantPrice = model.getPlantPrice() + " TK";
        holder.plantPriceTextView.setText(plantPrice);
        Picasso.get().load(model.getImageUrl()).into(holder.plantImageView);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), DescriptionActivity.class);
            intent.putExtra("plantName", model.getPlantName());
            intent.putExtra("plantType", model.getPlantType());
            intent.putExtra("plantPrice", model.getPlantPrice());
            intent.putExtra("image", model.getImageUrl());
            intent.putExtra("key", model.getKey());
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return plantList.size();
    }

    public void updateList(ArrayList<Model> newList) {
        plantList = new ArrayList<>(newList);
        notifyDataSetChanged();
    }



    static class PlantViewHolder extends RecyclerView.ViewHolder {
        ImageView plantImageView;
        TextView plantNameTextView, plantPriceTextView;

        public PlantViewHolder(@NonNull View itemView) {
            super(itemView);
            plantImageView = itemView.findViewById(R.id.plantImageView);
            plantNameTextView = itemView.findViewById(R.id.plantNameTextView);
            plantPriceTextView = itemView.findViewById(R.id.plantPriceTextView);
        }
    }
}
