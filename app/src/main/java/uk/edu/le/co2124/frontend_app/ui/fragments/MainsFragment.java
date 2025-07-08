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

public class MainsFragment extends Fragment {

    private TabLayout mainsSubTabs;
    private RecyclerView mainsRecyclerView;
    private Button viewBasketButton;
    private MenuAdapter adapter;

    private final String[] subCategories = {
            "Pizza", "Pasta", "Beef", "Lamb", "Chicken", "Vegetarian", "Vegan"
    };

    private final List<MenuItem> mainsItems = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mains, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mainsSubTabs = view.findViewById(R.id.mainsTabs);
        mainsRecyclerView = view.findViewById(R.id.mainsRecyclerView);
        viewBasketButton = view.findViewById(R.id.viewBasketButton);

        adapter = new MenuAdapter(mainsItems, item -> {
            BasketManager.getInstance().addItem(item);
            updateBasketButton();
            Toast.makeText(getContext(), "Added: " + item.getName(), Toast.LENGTH_SHORT).show();
        });

        mainsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mainsRecyclerView.setAdapter(adapter);

        for (String sub : subCategories) {
            mainsSubTabs.addTab(mainsSubTabs.newTab().setText(sub));
        }

        mainsSubTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                loadMainsForSubcategory(tab.getText().toString());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        // Default load
        loadMainsForSubcategory(subCategories[0]);
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

    private void loadMainsForSubcategory(String subCategory) {
        mainsItems.clear();

        switch (subCategory) {
            case "Pizza":
                mainsItems.add(new MenuItem("m001", "Margherita", "8.50"));
                mainsItems.add(new MenuItem("m002", "Pepperoni", "9.50"));
                mainsItems.add(new MenuItem("m003", "BBQ Chicken", "10.00"));
                break;

            case "Pasta":
                mainsItems.add(new MenuItem("m004", "Spaghetti Bolognese", "9.00"));
                mainsItems.add(new MenuItem("m005", "Fettuccine Alfredo", "9.50"));
                mainsItems.add(new MenuItem("m006", "Penne Arrabbiata", "8.50"));
                break;

            case "Beef":
                mainsItems.add(new MenuItem("m007", "Beef Burger", "10.50"));
                mainsItems.add(new MenuItem("m008", "Steak Frites", "14.00"));
                break;

            case "Lamb":
                mainsItems.add(new MenuItem("m009", "Lamb Shank", "13.50"));
                mainsItems.add(new MenuItem("m010", "Grilled Lamb Kofta", "11.00"));
                break;

            case "Chicken":
                mainsItems.add(new MenuItem("m011", "Grilled Chicken Breast", "10.00"));
                mainsItems.add(new MenuItem("m012", "Chicken Parmesan", "11.00"));
                break;

            case "Vegetarian":
                mainsItems.add(new MenuItem("m013", "Vegetarian Lasagna", "9.00"));
                mainsItems.add(new MenuItem("m014", "Stuffed Peppers", "8.50"));
                break;

            case "Vegan":
                mainsItems.add(new MenuItem("m015", "Vegan Burger", "9.50"));
                mainsItems.add(new MenuItem("m016", "Chickpea Curry", "8.50"));
                break;
        }

        adapter.notifyDataSetChanged();
    }
}
