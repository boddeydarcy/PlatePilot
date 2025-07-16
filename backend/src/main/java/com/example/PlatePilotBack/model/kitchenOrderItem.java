// KitchenOrderItem.java
package com.example.PlatePilotBack.model;

import jakarta.persistence.*;

@Entity
public class kitchenOrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Use Long and let it auto-generate

    private String stockId; // keep track of stock item ID
    private String name;
    private int quantity;

    public kitchenOrderItem() {}

    public kitchenOrderItem(String stockId, String name, int quantity) {
        this.stockId = stockId;
        this.name = name;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public String getStockId() {
        return stockId;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
