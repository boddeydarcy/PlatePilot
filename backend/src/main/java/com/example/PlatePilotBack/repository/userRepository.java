package com.example.PlatePilotBack.repository;

import com.example.PlatePilotBack.model.user;
import org.springframework.data.jpa.repository.JpaRepository;

public interface userRepository extends JpaRepository<user, String> {
}
