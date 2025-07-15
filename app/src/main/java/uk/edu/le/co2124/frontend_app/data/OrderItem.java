package uk.edu.le.co2124.frontend_app.data;

public class OrderItem {
    private String id;
    private int quantity;

    public OrderItem(String id, int quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public String getId() { return id; }
    public int getQuantity() { return quantity; }
}