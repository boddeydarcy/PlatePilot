package com.example.PlatePilotBack.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class stockItem {
    @Id
    private String id;
    private String name;
    private int quantity;

    public stockItem() {}

    public stockItem(String id, String name, int quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
