package uk.edu.le.co2124.frontend_app.data;

import java.util.List;

public class KitchenOrder {
    private String id;
    private List<KitchenOrderItem> items;
    private String status;

    public void setId(String id) {
        this.id = id;
    }

    public void setItems(List<KitchenOrderItem> items) {
        this.items = items;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() { return id; }
    public List<KitchenOrderItem> getItems() { return items; }
    public String getStatus() { return status; }

}
