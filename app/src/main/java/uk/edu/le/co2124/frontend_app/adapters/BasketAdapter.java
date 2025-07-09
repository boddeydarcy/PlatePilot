package uk.edu.le.co2124.frontend_app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import uk.edu.le.co2124.frontend_app.BasketManager;
import uk.edu.le.co2124.frontend_app.R;
import uk.edu.le.co2124.frontend_app.data.MenuItem;

public class BasketAdapter extends RecyclerView.Adapter<BasketAdapter.ViewHolder> {

    private final List<MenuItem> items;
    private final OnBasketChangedListener listener;

    public BasketAdapter(List<MenuItem> items, OnBasketChangedListener listener) {
        this.items = items;
        this.listener = listener;
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, price;
        ImageButton removeButton;

        public ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.itemName);
            price = view.findViewById(R.id.itemPrice);
            removeButton = view.findViewById(R.id.removeButton);
        }
    }

    @NonNull
    @Override
    public BasketAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.basket_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BasketAdapter.ViewHolder holder, int position) {
        MenuItem item = items.get(position);
        holder.name.setText(item.getName());
        holder.price.setText("£" + item.getPrice());

        holder.removeButton.setOnClickListener(v -> {
            BasketManager.getInstance().removeItem(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, items.size());

            if (listener != null) {
                listener.onBasketUpdated();
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public interface OnBasketChangedListener {
        void onBasketUpdated();
    }
}
