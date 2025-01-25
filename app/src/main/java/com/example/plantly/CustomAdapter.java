package com.example.plantly;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import com.google.firebase.database.FirebaseDatabase;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.holder>{

    ArrayList<Model> data;
    Context context;


    public CustomAdapter(ArrayList<Model> data, Context context) {
        this.data = data;
        this.context = context;
    }


    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_plant_admin, parent, false);
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {
        Model model = data.get(position);
        holder.plantNameTextView.setText(model.getPlantName());
        holder.plantTypeTextView.setText(model.getPlantType());
        String plantPrice = String.valueOf(model.getPlantPrice());

        holder.plantPriceTextView.setText(plantPrice);
        Picasso.get().load(model.getImageUrl()).into(holder.plantImageView);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PopupMenu popupMenu = new PopupMenu(holder.itemView.getContext(), v);
                popupMenu.inflate(R.menu.popup_menu);
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId()== R.id.edit) {
                            Intent intent = new Intent(holder.itemView.getContext(), UpdateActivity.class);
                            intent.putExtra("plantName", model.getPlantName());
                            intent.putExtra("plantType", model.getPlantType());
                            intent.putExtra("plantPrice", plantPrice);
                            intent.putExtra("image", model.getImageUrl());
                            intent.putExtra("key", model.getKey());
                            holder.itemView.getContext().startActivity(intent);


                        } else if (item.getItemId()== R.id.delete) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
                                builder.setTitle("Are you sure?").setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        FirebaseDatabase.getInstance().getReference().child("Plant Items").child(model.getKey()).removeValue();
                                        Toast.makeText(holder.itemView.getContext(), "Deleted",Toast.LENGTH_SHORT).show();;
                                    }
                                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(holder.itemView.getContext(), "Cancelled",Toast.LENGTH_SHORT).show();;
                                    }
                                }).show();

                        }
                        return false;
                    }
                });
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class holder extends RecyclerView.ViewHolder{
        ImageView plantImageView;
        TextView plantNameTextView, plantTypeTextView,plantPriceTextView;

        public holder(@NonNull View itemView) {
            super(itemView);
            plantImageView = itemView.findViewById(R.id.plantImageView);
            plantNameTextView = itemView.findViewById(R.id.plantNameTextView);
            plantTypeTextView = itemView.findViewById(R.id.plantTypeTextView);
            plantPriceTextView = itemView.findViewById(R.id.plantPriceTextView);
        }
    }
}
