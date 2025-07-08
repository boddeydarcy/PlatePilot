package uk.edu.le.co2124.frontend_app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import uk.edu.le.co2124.frontend_app.data.MenuItem;
import uk.edu.le.co2124.frontend_app.R;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(MenuItem item);
    }

    private final List<MenuItem> items;
    private final OnItemClickListener listener;

    public MenuAdapter(List<MenuItem> items, OnItemClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MenuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.menu_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuAdapter.ViewHolder holder, int position) {
        MenuItem item = items.get(position);
        holder.nameText.setText(item.getName());
        if (item.getPrice() != null) {
            holder.priceText.setText("£" + item.getPrice());
        } else {
            holder.priceText.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(v -> listener.onItemClick(item));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameText;
        TextView priceText;

        ViewHolder(View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.menuItemName);
            priceText = itemView.findViewById(R.id.menuItemPrice);
        }
    }
}
