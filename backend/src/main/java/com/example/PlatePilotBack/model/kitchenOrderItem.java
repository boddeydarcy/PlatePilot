// KitchenOrderItem.java
package com.example.PlatePilotBack.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class kitchenOrderItem {
    @Id
    private String id;

    private String name;
    private int quantity;

    public kitchenOrderItem() {}

    public kitchenOrderItem(String id, String name, int quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public int getQuantity() { return quantity; }

    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
