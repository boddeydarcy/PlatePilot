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

public class DrinksFragment extends Fragment implements BasketManager.BasketChangeListener{
    private Button viewBasketButton;
    private TabLayout drinksSubTabs;
    private RecyclerView drinksRecyclerView;
    private MenuAdapter adapter;

    private final String[] subCategories = {
            "Soft Drinks", "Red Wine", "White Wine", "Rosé", "Sparkling Wine",
            "Draught Beer", "Bottled Beer", "Cocktails", "Mocktails", "Hot Drinks"
    };

    private final List<MenuItem> drinkItems = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_drinks, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        drinksSubTabs = view.findViewById(R.id.drinksTabs);
        drinksRecyclerView = view.findViewById(R.id.drinksRecyclerView);
        viewBasketButton = view.findViewById(R.id.viewBasketButton); // must match layout ID

        adapter = new MenuAdapter(drinkItems, item -> {
            BasketManager.getInstance().addItem(item);
            updateBasketButton();
            Toast.makeText(getContext(), "Added: " + item.getName(), Toast.LENGTH_SHORT).show();
        });

        drinksRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        drinksRecyclerView.setAdapter(adapter);

        for (String sub : subCategories) {
            drinksSubTabs.addTab(drinksSubTabs.newTab().setText(sub));
        }

        drinksSubTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                loadDrinksForSubcategory(tab.getText().toString());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        viewBasketButton.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), BasketActivity.class));
        });

        updateBasketButton(); // ensure UI is synced at launch
    }

    private void updateBasketButton() {
        int count = BasketManager.getInstance().getItemCount();
        double total = BasketManager.getInstance().getTotalPrice();
        viewBasketButton.setText("View Tab (" + count + " items - £" + String.format("%.2f", total) + ")");
    }

    private void loadDrinksForSubcategory(String subCategory) {
        drinkItems.clear();

        switch (subCategory) {
            case "Soft Drinks":
                drinkItems.add(new MenuItem("d001", "Coca-Cola", "2.50"));
                drinkItems.add(new MenuItem("d002", "Sprite", "2.50"));
                drinkItems.add(new MenuItem("d003", "Fanta", "2.50"));
                break;

            case "Red Wine":
                drinkItems.add(new MenuItem("d004", "Merlot", "6.00"));
                drinkItems.add(new MenuItem("d005", "Cabernet Sauvignon", "6.50"));
                break;

            case "White Wine":
                drinkItems.add(new MenuItem("d006", "Sauvignon Blanc", "6.00"));
                drinkItems.add(new MenuItem("d007", "Chardonnay", "6.50"));
                break;

            case "Rosé":
                drinkItems.add(new MenuItem("d008", "Zinfandel Rosé", "6.00"));
                break;

            case "Sparkling Wine":
                drinkItems.add(new MenuItem("d009", "Prosecco", "7.00"));
                break;

            case "Draught Beer":
                drinkItems.add(new MenuItem("d010", "Guinness Pint", "5.50"));
                drinkItems.add(new MenuItem("d011", "Heineken Pint", "5.00"));
                break;

            case "Bottled Beer":
                drinkItems.add(new MenuItem("d012", "Corona", "4.00"));
                drinkItems.add(new MenuItem("d013", "Budweiser", "4.00"));
                break;

            case "Cocktails":
                drinkItems.add(new MenuItem("d014", "Mojito", "7.50"));
                drinkItems.add(new MenuItem("d015", "Margarita", "7.50"));
                break;

            case "Mocktails":
                drinkItems.add(new MenuItem("d016", "Virgin Mojito", "5.00"));
                drinkItems.add(new MenuItem("d017", "Nojito", "5.00"));
                break;

            case "Hot Drinks":
                drinkItems.add(new MenuItem("d018", "Espresso", "2.00"));
                drinkItems.add(new MenuItem("d019", "Cappuccino", "2.50"));
                drinkItems.add(new MenuItem("d020", "Tea", "2.00"));
                break;
        }

        adapter.notifyDataSetChanged();
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
