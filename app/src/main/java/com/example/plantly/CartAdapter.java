package com.example.plantly;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private Context context;
    private ArrayList<CartItem> cartItems;
    private ArrayList<CartItem> selectedItems = new ArrayList<>();

    public CartAdapter(Context context, ArrayList<CartItem> cartItems) {
        this.context = context;
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem item = cartItems.get(position);

        holder.cartItemNameTextView.setText(item.getName());
        holder.cartItemPriceTextView.setText(item.getPrice() + " Tk");
        holder.tvQuantity.setText(String.valueOf(item.getQuantity()));

        Picasso.get().load(item.getImageUrl()).into(holder.cartItemImageView);

        // Checkbox selection
        holder.checkboxCartItem.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                selectedItems.add(item);
            } else {
                selectedItems.remove(item);
            }
        });

        // Increase quantity
        holder.btnIncrease.setOnClickListener(v -> {
            int quantity = item.getQuantity();
            quantity++;
            item.setQuantity(quantity);
            holder.tvQuantity.setText(String.valueOf(quantity));
            updatePrice(holder.cartItemPriceTextView, item);
        });

        // Decrease quantity
        holder.btnDecrease.setOnClickListener(v -> {
            int quantity = item.getQuantity();
            if (quantity > 1) {
                quantity--;
                item.setQuantity(quantity);
                holder.tvQuantity.setText(String.valueOf(quantity));
                updatePrice(holder.cartItemPriceTextView, item);
            } else {
                Toast.makeText(context, "Quantity can't be less than 1!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updatePrice(TextView priceTextView, CartItem item) {
        int totalPrice = item.getPrice() * item.getQuantity();
        priceTextView.setText(totalPrice + " Tk");
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public ArrayList<CartItem> getSelectedItems() {
        return selectedItems;
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkboxCartItem;
        ImageView cartItemImageView;
        TextView cartItemNameTextView, cartItemPriceTextView, tvQuantity;
        Button btnIncrease, btnDecrease;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            checkboxCartItem = itemView.findViewById(R.id.checkboxCartItem);
            cartItemImageView = itemView.findViewById(R.id.cartItemImageView);
            cartItemNameTextView = itemView.findViewById(R.id.cartItemNameTextView);
            cartItemPriceTextView = itemView.findViewById(R.id.cartItemPriceTextView);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            btnIncrease = itemView.findViewById(R.id.btnIncrease);
            btnDecrease = itemView.findViewById(R.id.btnDecrease);
        }
    }
}
