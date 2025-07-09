package uk.edu.le.co2124.frontend_app.ui.fragments;

import android.app.Fragment;
import android.widget.Button;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import uk.edu.le.co2124.frontend_app.BasketManager;
import uk.edu.le.co2124.frontend_app.adapters.MenuAdapter;
import uk.edu.le.co2124.frontend_app.data.MenuItem;

public abstract class BaseMenuFragment extends Fragment implements BasketManager.BasketChangeListener {

    protected TabLayout subTabs;
    protected RecyclerView recyclerView;
    protected Button viewBasketButton;
    protected MenuAdapter adapter;
    protected List<MenuItem> items = new ArrayList<>();

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
    public void onBasketChanged() {
        updateBasketButton();
    }

    protected void updateBasketButton() {
        int count = BasketManager.getInstance().getItemCount();
        double total = BasketManager.getInstance().getTotalPrice();
        viewBasketButton.setText("View Tab (" + count + " items - £" + String.format("%.2f", total) + ")");
    }

    protected abstract void loadItemsForCategory(String category);
    protected abstract String[] getSubcategories();
}