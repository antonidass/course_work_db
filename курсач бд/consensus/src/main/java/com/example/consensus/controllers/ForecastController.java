package com.example.consensus.controllers;

import com.example.consensus.entities.Forecast;
import com.example.consensus.services.ForecastService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ForecastController {
    private final ForecastService forecastService;

    public ForecastController(ForecastService forecastService) {
        this.forecastService = forecastService;
    }

    @GetMapping("/company/{id}/forecasts")
    public List<Forecast> getAllForecastsByCompanyId(@PathVariable(name="id") Long id) {
        return forecastService.getAllForecastsByCompanyId(id);
    }

    @GetMapping("/forecast/{id}")
    public Forecast getForecastById(@PathVariable(name = "id") Long id) {
        return forecastService.getForecastById(id);
    }

    @PutMapping("/forecast/{id}")
    public Forecast updateForecastById(@PathVariable(name = "id") Long id, @RequestBody Forecast forecastDetails) {
        return forecastService.updateForecastById(id, forecastDetails);
    }

    @PostMapping("/forecast")
    public Forecast addForecast(@RequestBody Forecast forecast) {
        return forecastService.addForecast(forecast);
    }

    @DeleteMapping("/forecast/{id}")
    public Forecast deleteForecast(@PathVariable(name = "id") Long id) {
        return forecastService.deleteForecast(id);
    }
}
