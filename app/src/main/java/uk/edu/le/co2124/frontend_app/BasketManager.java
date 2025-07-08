package uk.edu.le.co2124.frontend_app;

import java.util.ArrayList;
import java.util.List;

import uk.edu.le.co2124.frontend_app.data.MenuItem;

public class BasketManager {
    private static BasketManager instance;
    private final List<MenuItem> items = new ArrayList<>();

    public static BasketManager getInstance() {
        if (instance == null) {
            instance = new BasketManager();
        }
        return instance;
    }

    public void addItem(MenuItem item) {
        items.add(item);
    }

    public List<MenuItem> getItems() {
        return items;
    }

    public int getItemCount() {
        return items.size();
    }

    public double getTotalPrice() {
        double total = 0;
        for (MenuItem item : items) {
            try {
                total += Double.parseDouble(item.getPrice());
            } catch (NumberFormatException e) {
                // skip invalid price
            }
        }
        return total;
    }

    public void clear() {
        items.clear();
    }
}