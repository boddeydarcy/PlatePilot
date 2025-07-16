package uk.edu.le.co2124.frontend_app.ui.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.edu.le.co2124.frontend_app.R;
import uk.edu.le.co2124.frontend_app.adapters.OrdersAdapter;
import uk.edu.le.co2124.frontend_app.data.KitchenOrder;
import uk.edu.le.co2124.frontend_app.network.ApiClient;

public class ViewOrdersActivity extends AppCompatActivity {

    private OrdersAdapter ordersAdapter;
    private final List<KitchenOrder> orders = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_orders);

        Toolbar toolbar = findViewById(R.id.ordersToolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Kitchen Orders");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        RecyclerView ordersRecyclerView = findViewById(R.id.ordersRecyclerView);
        ordersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ordersAdapter = new OrdersAdapter(orders);
        ordersRecyclerView.setAdapter(ordersAdapter);

        fetchOrders();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish(); // Go back when back button on toolbar is clicked
        return true;
    }

    private void fetchOrders() {
        ApiClient.getApiService().getKitchenOrders().enqueue(new Callback<>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<List<KitchenOrder>> call, @NonNull Response<List<KitchenOrder>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    orders.clear();
                    orders.addAll(response.body());
                    ordersAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(ViewOrdersActivity.this, "Failed to load orders. Server error.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<KitchenOrder>> call, @NonNull Throwable t) {
                Toast.makeText(ViewOrdersActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
