package com.example.app.bracittask.service;


import com.example.app.bracittask.entity.Battery;
import com.example.app.bracittask.repository.BatteryRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class BatteryServiceTest {
    @Autowired
    private BatteryService batteryService;

    @MockBean
    private BatteryRepository batteryRepository;

    @Test
    public void testSaveBatteries() {
        List<Battery> batteries = List.of(
                new Battery("Battery1", "12345", 100),
                new Battery("Battery2", "23456", 200)
        );

        Mockito.when(batteryRepository.saveAll(Mockito.anyList())).thenReturn(batteries);

        List<Battery> savedBatteries = batteryService.saveBatteries(batteries);

        assertEquals(batteries.size(), savedBatteries.size());
        assertEquals(batteries.get(0).getName(), savedBatteries.get(0).getName());
    }

    @Test
    public void testGetBatteriesByPostcodeRange() {
        List<Battery> batteries = List.of(
                new Battery("Battery2", "23456", 200),
                new Battery("Battery3", "24567", 150)
        );

        Mockito.when(batteryRepository.findByPostcodeBetween("20000", "30000")).thenReturn(batteries);

        List<Battery> foundBatteries = batteryService.getBatteriesByPostcodeRange("20000", "30000");

        assertEquals(2, foundBatteries.size());
        assertEquals("Battery2", foundBatteries.get(0).getName());
    }
}
