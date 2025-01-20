package com.example.plantly;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PlantAdapter extends RecyclerView.Adapter<PlantAdapter.PlantViewHolder> {

    private List<PlantModel> plantList;

    public PlantAdapter(List<PlantModel> plantList) {
        this.plantList = plantList;
    }

    @NonNull
    @Override
    public PlantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_plant, parent, false);
        return new PlantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlantViewHolder holder, int position) {
        PlantModel plant = plantList.get(position);
        holder.plantNameTextView.setText(plant.getName());
        String plantPrice = Integer.toString(plant.getPlantPrice());
        holder.plantPriceTextView.setText(plantPrice + " TK");
        holder.plantImageView.setImageResource(plant.getImageResourceId()); // Set image
    }

    @Override
    public int getItemCount() {
        return plantList.size();
    }

    static class PlantViewHolder extends RecyclerView.ViewHolder {
        ImageView plantImageView, plantHeart;
        TextView plantNameTextView,plantPriceTextView;

        public PlantViewHolder(@NonNull View itemView) {
            super(itemView);

            plantImageView = itemView.findViewById(R.id.plantImageView);
            plantNameTextView = itemView.findViewById(R.id.plantNameTextView);
            plantPriceTextView = itemView.findViewById(R.id.plantPriceTextView);
        }
    }
}
