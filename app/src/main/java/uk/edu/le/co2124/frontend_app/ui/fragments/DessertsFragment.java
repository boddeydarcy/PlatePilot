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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import uk.edu.le.co2124.frontend_app.BasketManager;
import uk.edu.le.co2124.frontend_app.R;
import uk.edu.le.co2124.frontend_app.adapters.MenuAdapter;
import uk.edu.le.co2124.frontend_app.data.MenuItem;
import uk.edu.le.co2124.frontend_app.ui.activities.BasketActivity;

public class DessertsFragment extends Fragment implements BasketManager.BasketChangeListener{

    private TabLayout dessertTabs;
    private RecyclerView dessertRecyclerView;
    private Button viewBasketButton;
    private MenuAdapter adapter;

    private final String[] subCategories = {
            "Cakes", "Ice Cream", "Puddings", "Fruit", "Other"
    };

    private final List<MenuItem> dessertItems = new ArrayList<>();
    private JSONObject menuJson;

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

        loadMenuJson();
        loadDesserts(subCategories[0]);
        updateBasketButton();

        viewBasketButton.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), BasketActivity.class));
        });
    }

    private void loadMenuJson() {
        try (InputStream is = requireContext().getAssets().open("menu.json")) {
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            String jsonStr = new String(buffer, StandardCharsets.UTF_8);
            menuJson = new JSONObject(jsonStr);
        } catch (IOException | JSONException e) {
            Toast.makeText(getContext(), "Failed to load menu", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadDesserts(String category) {
        dessertItems.clear();
        if (menuJson == null) return;

        try {
            JSONObject dessertsObject = menuJson.getJSONObject("desserts");
            JSONArray itemsArray = dessertsObject.getJSONArray(category);
            for (int i = 0; i < itemsArray.length(); i++) {
                JSONObject obj = itemsArray.getJSONObject(i);
                String id = obj.getString("id");
                String name = obj.getString("name");
                String price = obj.getString("price");
                dessertItems.add(new MenuItem(id, name, price));
            }
        } catch (JSONException e) {
            Toast.makeText(getContext(), "No items for " + category, Toast.LENGTH_SHORT).show();
        }

        adapter.notifyDataSetChanged();
    }

    private void updateBasketButton() {
        int count = BasketManager.getInstance().getItemCount();
        double total = BasketManager.getInstance().getTotalPrice();
        viewBasketButton.setText("View Tab (" + count + " items - £" + String.format("%.2f", total) + ")");
    }

    @Override
    public void onStart() {
        super.onStart();
        BasketManager.getInstance().addListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        BasketManager.getInstance().removeListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateBasketButton();
    }

    @Override
    public void onBasketChanged() {
        updateBasketButton();
    }
}
