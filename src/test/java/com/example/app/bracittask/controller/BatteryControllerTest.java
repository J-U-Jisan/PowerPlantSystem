package com.example.app.bracittask.controller;

import com.example.app.bracittask.entity.Battery;
import com.example.app.bracittask.service.BatteryService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BatteryController.class)
public class BatteryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BatteryService batteryService;

    @Test
    public void testAddBatteries() throws Exception {
        List<Battery> batteries = List.of(
                new Battery("Battery1", "12345", 100),
                new Battery("Battery2", "23456", 200)
        );

        Mockito.when(batteryService.saveBatteries(Mockito.anyList())).thenReturn(batteries);

        mockMvc.perform(post("/batteries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[{\"name\": \"Battery1\", \"postcode\": \"12345\", \"wattCapacity\": 100}, {\"name\": \"Battery2\", \"postcode\": \"23456\", \"wattCapacity\": 200}]"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"name\": \"Battery1\", \"postcode\": \"12345\", \"wattCapacity\": 100}, {\"name\": \"Battery2\", \"postcode\": \"23456\", \"wattCapacity\": 200}]"));
    }

    @Test
    public void testGetBatteriesByPostcodeRange() throws Exception {
        List<Battery> batteries = List.of(
                new Battery("Battery2", "23456", 200),
                new Battery("Battery3", "24567", 150)
        );

        Mockito.when(batteryService.getBatteriesByPostcodeRange("20000", "30000")).thenReturn(batteries);

        mockMvc.perform(get("/batteries?postcodeRange=20000-30000"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"batteryNames\": [\"Battery2\", \"Battery3\"], \"statistics\": {\"totalWattCapacity\": 350, \"averageWattCapacity\": 175}}"));
    }
}
