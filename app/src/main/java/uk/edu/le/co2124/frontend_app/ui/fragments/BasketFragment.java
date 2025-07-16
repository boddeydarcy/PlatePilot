package uk.edu.le.co2124.frontend_app.ui.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.edu.le.co2124.frontend_app.network.ApiClient;
import uk.edu.le.co2124.frontend_app.data.OrderItem;

import java.util.ArrayList;
import java.util.List;

import uk.edu.le.co2124.frontend_app.BasketManager;
import uk.edu.le.co2124.frontend_app.R;
import uk.edu.le.co2124.frontend_app.adapters.BasketAdapter;
import uk.edu.le.co2124.frontend_app.data.MenuItem;

public class BasketFragment extends Fragment implements BasketAdapter.OnBasketChangedListener {

    private TextView totalPriceText;

    private Button confirmButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_basket, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView basketRecyclerView = view.findViewById(R.id.basketRecyclerView);
        totalPriceText = view.findViewById(R.id.totalPriceText);

        List<MenuItem> items = BasketManager.getInstance().getItems();

        BasketAdapter adapter = new BasketAdapter(items, this);
        basketRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        basketRecyclerView.setAdapter(adapter);

        updateTotalPrice();

        confirmButton = view.findViewById(R.id.confirmOrderButton);

        confirmButton.setOnClickListener(v -> {
            List<MenuItem> basketItems = BasketManager.getInstance().getItems();

            if (basketItems.isEmpty()) {
                Toast.makeText(getContext(), "Your tab is empty. Please add items before ordering.", Toast.LENGTH_SHORT).show();
                return; // exit early
            }

            List<OrderItem> orderItems = new ArrayList<>();
            for (MenuItem item : basketItems) {
                orderItems.add(new OrderItem(item.getId(), 1)); // or use a quantity system if you support it
            }

            ApiClient.getApiService().submitOrder(orderItems).enqueue(new Callback<>() {
                @Override
                public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getContext(), "Order sent!", Toast.LENGTH_SHORT).show();
                        BasketManager.getInstance().clear(); // optional
                    } else {
                        Toast.makeText(getContext(), "Failed to send order. Code: " + response.code(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                    Toast.makeText(getContext(), "Network error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });

    }


    @Override
    public void onBasketUpdated() {
        updateTotalPrice();
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    private void updateTotalPrice() {
        double total = BasketManager.getInstance().getTotalPrice();
        totalPriceText.setText("Total: £" + String.format("%.2f", total));

        if (confirmButton != null) {
            boolean hasItems = BasketManager.getInstance().getItemCount() > 0;
            confirmButton.setEnabled(hasItems);
        }
    }

}