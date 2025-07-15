package uk.edu.le.co2124.frontend_app.data;

public class OrderItem {
    private int orderID;
    private String productId;
    private String name;
    private int quantity;
    private String price;

    public OrderItem(String productId, String name, int quantity, String price) {
        this.productId = productId;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    // Required for Retrofit + Gson
    public OrderItem() {}

    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getPrice() { return price; }
    public void setPrice(String price) { this.price = price; }

    public void setOrderID(){
        this.orderID = orderID;
    }

    public int getOrderID(){
        return orderID;
    }
}
