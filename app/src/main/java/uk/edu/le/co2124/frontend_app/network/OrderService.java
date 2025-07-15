package uk.edu.le.co2124.frontend_app.network;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import uk.edu.le.co2124.frontend_app.data.OrderItem;

public interface OrderService {
    @POST("/api/orders")
    Call<Void> submitOrder(@Body List<OrderItem> items);
}