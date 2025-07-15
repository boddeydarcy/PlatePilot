package uk.edu.le.co2124.frontend_app;

import java.util.ArrayList;
import java.util.List;
import uk.edu.le.co2124.frontend_app.data.OrderItem;

import uk.edu.le.co2124.frontend_app.data.MenuItem;

public class BasketManager {
    private static BasketManager instance;
    private final List<MenuItem> items = new ArrayList<>();

    public interface BasketChangeListener {
        void onBasketChanged();
    }

    private final List<BasketChangeListener> listeners = new ArrayList<>();

    public static BasketManager getInstance() {
        if (instance == null) {
            instance = new BasketManager();
        }
        return instance;
    }

    public void addItem(MenuItem item) {
        items.add(item);
        notifyListeners();
    }

    public void removeItem(int index) {
        if (index >= 0 && index < items.size()) {
            items.remove(index);
            notifyListeners();
        }
    }

    public void addListener(BasketChangeListener listener) {
        listeners.add(listener);
    }

    public void removeListener(BasketChangeListener listener) {
        listeners.remove(listener);
    }

    private void notifyListeners() {
        for (BasketChangeListener listener : listeners) {
            listener.onBasketChanged();
        }
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
        notifyListeners();
    }


    public List<OrderItem> getOrderItems() {
        List<OrderItem> orderItems = new ArrayList<>();
        for (MenuItem item : items) {
            orderItems.add(new OrderItem(item.getId(), 1)); // Assuming quantity = 1 for now
        }
        return orderItems;
    }
}