package com.example.app.bracittask.repository;

import com.example.app.bracittask.entity.Battery;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BatteryRepository extends JpaRepository<Battery, Long> {
    List<Battery> findByPostcodeBetween(String start, String end);
}
