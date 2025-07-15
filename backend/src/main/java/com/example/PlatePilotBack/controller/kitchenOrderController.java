package com.example.PlatePilotBack.controller;

import com.example.PlatePilotBack.model.kitchenOrder;
import com.example.PlatePilotBack.repository.kitchenOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kitchen")
public class kitchenOrderController {

    @Autowired
    private kitchenOrderRepository kitchenOrderRepository;

    @GetMapping
    public ResponseEntity<List<kitchenOrder>> getKitchenOrders() {
        return ResponseEntity.ok(kitchenOrderRepository.findAll());
    }
}
