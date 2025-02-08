package com.example.plantly;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private List<Order> orderList;

    public OrderAdapter(List<Order> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.userName.setText("Name: " + order.getUserName());
        holder.email.setText("Email: " + order.getEmail());
        holder.totalPrice.setText("Total: " + order.getTotalPrice() + " Tk");
        holder.deliveryAddress.setText("Address: " + order.getDeliveryAddress());

        StringBuilder itemsText = new StringBuilder();
        for (OrderItem item : order.getOrderItems()) {
            itemsText.append(item.getQuantity()).append("x ").append(item.getName()).append("\n");
        }
        holder.orderItems.setText("Items:\n" + itemsText.toString().trim());
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView userName, email, totalPrice, deliveryAddress, orderItems;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.userName);
            email = itemView.findViewById(R.id.email);
            totalPrice = itemView.findViewById(R.id.totalPrice);
            deliveryAddress = itemView.findViewById(R.id.deliveryAddress);
            orderItems = itemView.findViewById(R.id.orderItems);
        }
    }
}

