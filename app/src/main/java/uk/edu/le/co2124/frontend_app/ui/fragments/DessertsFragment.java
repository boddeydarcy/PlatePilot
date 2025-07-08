package uk.edu.le.co2124.frontend_app.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import uk.edu.le.co2124.frontend_app.BasketManager;
import uk.edu.le.co2124.frontend_app.R;
import uk.edu.le.co2124.frontend_app.adapters.MenuAdapter;
import uk.edu.le.co2124.frontend_app.data.MenuItem;
import uk.edu.le.co2124.frontend_app.ui.activities.BasketActivity;

public class DessertsFragment extends Fragment {

    private TabLayout dessertTabs;
    private RecyclerView dessertRecyclerView;

    private Button viewBasketButton;
    private MenuAdapter adapter;

    private final String[] subCategories = {
            "Cakes", "Ice Cream", "Puddings", "Fruit", "Other"
    };

    private final List<MenuItem> dessertItems = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_desserts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        dessertTabs = view.findViewById(R.id.dessertTabs);
        dessertRecyclerView = view.findViewById(R.id.dessertRecyclerView);
        viewBasketButton = view.findViewById(R.id.viewBasketButton);

        adapter = new MenuAdapter(dessertItems, item -> {
            BasketManager.getInstance().addItem(item);
            updateBasketButton();
            Toast.makeText(getContext(), "Added: " + item.getName(), Toast.LENGTH_SHORT).show();
        });

        dessertRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dessertRecyclerView.setAdapter(adapter);

        for (String sub : subCategories) {
            dessertTabs.addTab(dessertTabs.newTab().setText(sub));
        }

        dessertTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                loadDesserts(tab.getText().toString());
            }

            @Override public void onTabUnselected(TabLayout.Tab tab) {}
            @Override public void onTabReselected(TabLayout.Tab tab) {}
        });

        loadDesserts(subCategories[0]);
        updateBasketButton();

        viewBasketButton.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), BasketActivity.class));
        });
    }

    private void updateBasketButton() {
        int count = BasketManager.getInstance().getItemCount();
        double total = BasketManager.getInstance().getTotalPrice();
        viewBasketButton.setText("View Tab (" + count + " items - £" + String.format("%.2f", total) + ")");
    }

    private void loadDesserts(String category) {
        dessertItems.clear();

        switch (category) {
            case "Cakes":
                dessertItems.add(new MenuItem("de001", "Chocolate Cake", "5.00"));
                dessertItems.add(new MenuItem("de002", "Carrot Cake", "4.80"));
                break;
            case "Ice Cream":
                dessertItems.add(new MenuItem("de003", "Vanilla Ice Cream", "3.00"));
                dessertItems.add(new MenuItem("de004", "Strawberry Ice Cream", "3.00"));
                break;
            case "Puddings":
                dessertItems.add(new MenuItem("de005", "Sticky Toffee Pudding", "5.50"));
                dessertItems.add(new MenuItem("de006", "Treacle Sponge", "5.00"));
                break;
            case "Fruit":
                dessertItems.add(new MenuItem("de007", "Fruit Salad", "4.00"));
                break;
            case "Other":
                dessertItems.add(new MenuItem("de008", "Cheesecake", "5.20"));
                break;
        }

        adapter.notifyDataSetChanged();
    }
}
