package uk.edu.le.co2124.frontend_app.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import uk.edu.le.co2124.frontend_app.R;
import uk.edu.le.co2124.frontend_app.data.KitchenOrder;
import uk.edu.le.co2124.frontend_app.data.KitchenOrderItem;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {

    private final List<KitchenOrder> orders;
    private final Set<String> expandedOrders = new HashSet<>(); // Track expanded order IDs

    public OrdersAdapter(List<KitchenOrder> orders) {
        this.orders = orders;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView orderIdText, statusText, itemCountText;
        LinearLayout itemsContainer;

        public ViewHolder(View view) {
            super(view);
            orderIdText = view.findViewById(R.id.orderIdText);
            statusText = view.findViewById(R.id.statusText);
            itemCountText = view.findViewById(R.id.itemCountText);
            itemsContainer = view.findViewById(R.id.itemsContainer);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.kitchen_order_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        KitchenOrder order = orders.get(position);
        holder.orderIdText.setText("Order ID: " + order.getId());
        holder.statusText.setText("Status: " + order.getStatus());

        List<KitchenOrderItem> items = order.getItems();
        holder.itemCountText.setText("Items: " + (items != null ? items.size() : 0));

        // Expand/collapse logic
        boolean isExpanded = expandedOrders.contains(order.getId());
        holder.itemsContainer.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

        // Toggle on click
        holder.itemView.setOnClickListener(v -> {
            if (isExpanded) {
                expandedOrders.remove(order.getId());
            } else {
                expandedOrders.add(order.getId());
            }
            notifyItemChanged(position);
        });

        // Populate item views
        holder.itemsContainer.removeAllViews();
        if (isExpanded && items != null) {
            for (KitchenOrderItem item : items) {
                TextView itemView = new TextView(holder.itemView.getContext());
                itemView.setText("- " + item.getName() + " x" + item.getQuantity());
                itemView.setPadding(16, 4, 16, 4);
                holder.itemsContainer.addView(itemView);
            }
        }
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }
}
