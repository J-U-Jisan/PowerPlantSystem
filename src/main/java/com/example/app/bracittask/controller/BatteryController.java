package com.example.app.bracittask.controller;

import com.example.app.bracittask.entity.Battery;
import com.example.app.bracittask.service.BatteryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/batteries")
public class BatteryController {
    @Autowired
    private BatteryService batteryService;

    @PostMapping
    public ResponseEntity<List<Battery>> addBatteries(@RequestBody @Valid List<Battery> batteries) {
        List<Battery> savedBatteries = batteryService.saveBatteries(batteries);
        return ResponseEntity.ok(savedBatteries);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getBatteriesByPostcodeRange(
            @RequestParam String postcodeRange) {
        String[] range = postcodeRange.split("-");
        String start = range[0];
        String end = range[1];
        List<Battery> batteries = batteryService.getBatteriesByPostcodeRange(start, end);

        List<String> batteryNames = batteries.stream()
                .map(Battery::getName)
                .sorted()
                .collect(Collectors.toList());

        int totalWattCapacity = batteries.stream()
                .mapToInt(Battery::getWattCapacity)
                .sum();

        double averageWattCapacity = batteries.stream()
                .mapToInt(Battery::getWattCapacity)
                .average()
                .orElse(0);

        Map<String, Object> response = new HashMap<>();
        response.put("batteryNames", batteryNames);
        response.put("statistics", Map.of(
                "totalWattCapacity", totalWattCapacity,
                "averageWattCapacity", averageWattCapacity
        ));

        return ResponseEntity.ok(response);
    }
}

