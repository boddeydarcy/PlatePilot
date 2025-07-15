package com.example.PlatePilotBack.controller;

import com.example.PlatePilotBack.model.kitchenOrder;
import com.example.PlatePilotBack.model.kitchenOrderItem;
import com.example.PlatePilotBack.repository.kitchenOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.PlatePilotBack.model.orderItem;
import com.example.PlatePilotBack.model.stockItem;
import com.example.PlatePilotBack.repository.stockRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class stockUpdateController {

    @Autowired
    private stockRepository stockRepository;

    @Autowired
    private kitchenOrderRepository kitchenOrderRepository;

    @PostMapping
    public ResponseEntity<?> processOrder(@RequestBody List<orderItem> items) {
        List<kitchenOrderItem> kitchenItems = new ArrayList<>();

        for (orderItem order : items) {
            Optional<stockItem> stockOpt = stockRepository.findById(order.getId());

            if (stockOpt.isEmpty()) {
                return ResponseEntity.badRequest().body("Item not found: " + order.getId());
            }

            stockItem stock = stockOpt.get();

            if (stock.getQuantity() < order.getQuantity()) {
                return ResponseEntity.badRequest().body("Insufficient stock for: " + stock.getName());
            }

            stock.setQuantity(stock.getQuantity() - order.getQuantity());
            stockRepository.save(stock);

            // Add to kitchen list
            kitchenItems.add(new kitchenOrderItem(stock.getId(), stock.getName(), order.getQuantity()));
        }

        kitchenOrder kitchenOrder = new kitchenOrder(kitchenItems, "Pending");
        kitchenOrderRepository.save(kitchenOrder);

        return ResponseEntity.ok("Order submitted and sent to kitchen.");
    }

    @GetMapping("/kitchen")
    public ResponseEntity<List<kitchenOrder>> getKitchenOrders() {
        return ResponseEntity.ok(kitchenOrderRepository.findAll());
    }

}
