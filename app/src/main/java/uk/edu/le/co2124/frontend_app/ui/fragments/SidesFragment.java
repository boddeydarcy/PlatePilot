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

public class SidesFragment extends Fragment {

    private TabLayout sidesTabs;
    private RecyclerView sidesRecyclerView;
    private Button viewBasketButton;
    private MenuAdapter adapter;

    private final String[] subCategories = {
            "Chips", "Veg", "Rice", "Bread", "Dips"
    };

    private final List<MenuItem> sideItems = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sides, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        sidesTabs = view.findViewById(R.id.sidesTabs);
        sidesRecyclerView = view.findViewById(R.id.sidesRecyclerView);
        viewBasketButton = view.findViewById(R.id.viewBasketButton);

        adapter = new MenuAdapter(sideItems, item -> {
            BasketManager.getInstance().addItem(item);
            updateBasketButton();
            Toast.makeText(getContext(), "Added: " + item.getName(), Toast.LENGTH_SHORT).show();
        });

        sidesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        sidesRecyclerView.setAdapter(adapter);

        for (String sub : subCategories) {
            sidesTabs.addTab(sidesTabs.newTab().setText(sub));
        }

        sidesTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                loadSides(tab.getText().toString());
            }

            @Override public void onTabUnselected(TabLayout.Tab tab) {}
            @Override public void onTabReselected(TabLayout.Tab tab) {}
        });

        loadSides(subCategories[0]);
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

    private void loadSides(String category) {
        sideItems.clear();

        switch (category) {
            case "Chips":
                sideItems.add(new MenuItem("si001", "French Fries", "3.00"));
                sideItems.add(new MenuItem("si002", "Sweet Potato Fries", "3.50"));
                break;
            case "Veg":
                sideItems.add(new MenuItem("si003", "Steamed Veg", "3.20"));
                sideItems.add(new MenuItem("si004", "Grilled Asparagus", "3.80"));
                break;
            case "Rice":
                sideItems.add(new MenuItem("si005", "Steamed Rice", "2.50"));
                sideItems.add(new MenuItem("si006", "Pilaf Rice", "3.00"));
                break;
            case "Bread":
                sideItems.add(new MenuItem("si007", "Naan Bread", "2.50"));
                sideItems.add(new MenuItem("si008", "Ciabatta", "2.80"));
                break;
            case "Dips":
                sideItems.add(new MenuItem("si009", "Garlic Mayo", "1.00"));
                sideItems.add(new MenuItem("si010", "Spicy Salsa", "1.20"));
                break;
        }
        adapter.notifyDataSetChanged();
    }
}
