package uk.edu.le.co2124.frontend_app.ui.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.*;
import android.widget.Toast;
import androidx.annotation.*;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.*;
import java.util.*;
import retrofit2.*;
import uk.edu.le.co2124.frontend_app.*;
import uk.edu.le.co2124.frontend_app.adapters.KitchenOrderAdapter;
import uk.edu.le.co2124.frontend_app.data.*;
import uk.edu.le.co2124.frontend_app.network.ApiClient;

public class KitchenOrdersFragment extends Fragment {

    private KitchenOrderAdapter adapter;
    private final List<KitchenOrder> orders = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_kitchen_orders, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView = view.findViewById(R.id.kitchenOrdersRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new KitchenOrderAdapter(orders);
        recyclerView.setAdapter(adapter);

        loadOrders();
    }

    private void loadOrders() {
        ApiClient.getApiService().getKitchenOrders().enqueue(new Callback<>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<List<KitchenOrder>> call, @NonNull Response<List<KitchenOrder>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    orders.clear();
                    orders.addAll(response.body());
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Failed to load orders", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<KitchenOrder>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
