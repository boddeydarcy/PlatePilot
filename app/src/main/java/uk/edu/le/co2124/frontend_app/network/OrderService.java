package uk.edu.le.co2124.frontend_app.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import uk.edu.le.co2124.frontend_app.data.OrderItem;
import uk.edu.le.co2124.frontend_app.data.KitchenOrder;

public interface OrderService {

    @POST("/api/orders")
    Call<Void> submitOrder(@Body List<OrderItem> items);

    @GET("/api/orders/kitchen")
    Call<List<KitchenOrder>> getKitchenOrders();
}
