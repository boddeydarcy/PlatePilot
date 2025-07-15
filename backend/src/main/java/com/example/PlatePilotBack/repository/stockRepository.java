package com.example.PlatePilotBack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.PlatePilotBack.model.stockItem;

public interface stockRepository extends JpaRepository<stockItem, String> {
}