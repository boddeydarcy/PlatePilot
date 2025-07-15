// KitchenOrder.java
package com.example.PlatePilotBack.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class kitchenOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<kitchenOrderItem> items;

    private String status; // e.g., "Pending", "In Progress", "Completed"

    public kitchenOrder() {}

    public kitchenOrder(List<kitchenOrderItem> items, String status) {
        this.items = items;
        this.status = status;
    }

    public String getId() { return id; }
    public List<kitchenOrderItem> getItems() { return items; }
    public String getStatus() { return status; }

    public void setItems(List<kitchenOrderItem> items) { this.items = items; }
    public void setStatus(String status) { this.status = status; }
}
