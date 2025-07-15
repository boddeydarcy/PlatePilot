package uk.edu.le.co2124.frontend_app.ui.fragments;

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
import uk.edu.le.co2124.frontend_app.network.OrderService;
import uk.edu.le.co2124.frontend_app.data.OrderItem;

import java.util.ArrayList;
import java.util.List;

import uk.edu.le.co2124.frontend_app.BasketManager;
import uk.edu.le.co2124.frontend_app.R;
import uk.edu.le.co2124.frontend_app.adapters.BasketAdapter;
import uk.edu.le.co2124.frontend_app.data.MenuItem;

public class BasketFragment extends Fragment implements BasketAdapter.OnBasketChangedListener {

    private RecyclerView basketRecyclerView;
    private TextView totalPriceText;
    private BasketAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_basket, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        basketRecyclerView = view.findViewById(R.id.basketRecyclerView);
        totalPriceText = view.findViewById(R.id.totalPriceText);

        List<MenuItem> items = BasketManager.getInstance().getItems();

        adapter = new BasketAdapter(items, this);
        basketRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        basketRecyclerView.setAdapter(adapter);

        updateTotalPrice();

        Button confirmButton = view.findViewById(R.id.confirmOrderButton);

        confirmButton.setOnClickListener(v -> {
            List<OrderItem> orderItems = BasketManager.getInstance().getOrderItems();

            ApiClient.getApiService().submitOrder(orderItems).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(requireContext(), "Order sent!", Toast.LENGTH_SHORT).show();
                        BasketManager.getInstance().clear(); // Optional: clear basket after sending
                        adapter.notifyDataSetChanged(); // Update UI
                        updateTotalPrice();
                    } else {
                        Toast.makeText(requireContext(), "Failed to send order. Code: " + response.code(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(requireContext(), "Network error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });
    }


    @Override
    public void onBasketUpdated() {
        updateTotalPrice();
    }

    private void updateTotalPrice() {
        double total = BasketManager.getInstance().getTotalPrice();
        totalPriceText.setText("Total: £" + String.format("%.2f", total));
    }
}