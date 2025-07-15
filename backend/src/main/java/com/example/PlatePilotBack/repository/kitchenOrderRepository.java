// KitchenOrderRepository.java
package com.example.PlatePilotBack.repository;

import com.example.PlatePilotBack.model.kitchenOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface kitchenOrderRepository extends JpaRepository<kitchenOrder, String> {
}
