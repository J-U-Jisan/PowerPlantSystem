package com.example.app.bracittask.service;

import com.example.app.bracittask.entity.Battery;
import com.example.app.bracittask.repository.BatteryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BatteryService {
    @Autowired
    private BatteryRepository batteryRepository;

    public List<Battery> saveBatteries(List<Battery> batteries) {
        return batteryRepository.saveAll(batteries);
    }

    public List<Battery> getBatteriesByPostcodeRange(String start, String end) {
        return batteryRepository.findByPostcodeBetween(start, end);
    }
}