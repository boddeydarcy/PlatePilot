package uk.edu.le.co2124.frontend_app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import uk.edu.le.co2124.frontend_app.R;
import uk.edu.le.co2124.frontend_app.data.MenuItem;

public class BasketAdapter extends RecyclerView.Adapter<BasketAdapter.ViewHolder> {

    private final List<MenuItem> items;

    public BasketAdapter(List<MenuItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public BasketAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.menu_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BasketAdapter.ViewHolder holder, int position) {
        MenuItem item = items.get(position);
        holder.nameText.setText(item.getName());
        holder.priceText.setText("£" + item.getPrice());
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
