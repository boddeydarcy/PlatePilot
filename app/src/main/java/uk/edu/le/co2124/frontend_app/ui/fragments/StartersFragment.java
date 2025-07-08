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

public class StartersFragment extends Fragment {

    private TabLayout starterTabs;
    private RecyclerView starterRecyclerView;
    private Button viewBasketButton;
    private MenuAdapter adapter;

    private final String[] subCategories = {"Soups", "Salads", "Bread", "Seafood", "Sharing"};
    private final List<MenuItem> starterItems = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_starters, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        starterTabs = view.findViewById(R.id.starterTabs);
        starterRecyclerView = view.findViewById(R.id.starterRecyclerView);
        viewBasketButton = view.findViewById(R.id.viewBasketButton);

        adapter = new MenuAdapter(starterItems, item -> {
            BasketManager.getInstance().addItem(item);
            updateBasketButton();
            Toast.makeText(getContext(), "Added: " + item.getName(), Toast.LENGTH_SHORT).show();
        });

        starterRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        starterRecyclerView.setAdapter(adapter);

        for (String sub : subCategories) {
            starterTabs.addTab(starterTabs.newTab().setText(sub));
        }

        starterTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                loadStarters(tab.getText().toString());
            }

            @Override public void onTabUnselected(TabLayout.Tab tab) {}
            @Override public void onTabReselected(TabLayout.Tab tab) {}
        });

        loadStarters(subCategories[0]);
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
    private void loadStarters(String category) {
        starterItems.clear();
        switch (category) {
            case "Soups":
                starterItems.add(new MenuItem("s001", "Tomato Basil Soup", "4.50"));
                break;
            case "Salads":
                starterItems.add(new MenuItem("s002", "Caesar Salad", "5.50"));
                break;
            case "Bread":
                starterItems.add(new MenuItem("s003", "Garlic Bread", "3.00"));
                break;
            case "Seafood":
                starterItems.add(new MenuItem("s004", "Prawn Cocktail", "6.50"));
                break;
            case "Sharing":
                starterItems.add(new MenuItem("s005", "Sharing Platter", "8.50"));
                break;
        }
        adapter.notifyDataSetChanged();
    }
}
