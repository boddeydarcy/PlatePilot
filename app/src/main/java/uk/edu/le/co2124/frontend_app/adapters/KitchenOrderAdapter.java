package uk.edu.le.co2124.frontend_app.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import uk.edu.le.co2124.frontend_app.R;
import uk.edu.le.co2124.frontend_app.data.KitchenOrder;
import uk.edu.le.co2124.frontend_app.data.KitchenOrderItem;

public class KitchenOrderAdapter extends RecyclerView.Adapter<KitchenOrderAdapter.ViewHolder> {

    private final List<KitchenOrder> orders;

    public KitchenOrderAdapter(List<KitchenOrder> orders) {
        this.orders = orders;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView orderIdText, statusText, itemCountText;

        public ViewHolder(View view) {
            super(view);
            orderIdText = view.findViewById(R.id.orderIdText);
            statusText = view.findViewById(R.id.statusText);
            itemCountText = view.findViewById(R.id.itemCountText);
        }
    }


    @NonNull
    @Override
    public KitchenOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.kitchen_order_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull KitchenOrderAdapter.ViewHolder holder, int position) {
        KitchenOrder order = orders.get(position);

        holder.orderIdText.setText("Order ID: " + order.getId());
        holder.statusText.setText("Status: " + order.getStatus());

        StringBuilder sb = new StringBuilder();
        if (order.getItems() != null) {
            for (KitchenOrderItem item : order.getItems()) {
                sb.append(item.getName()).append(" x").append(item.getQuantity()).append("\n");
            }
        }
        holder.itemCountText.setText(sb.toString().trim());
    }


    @Override
    public int getItemCount() {
        return orders.size();
    }
}
