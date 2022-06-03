package com.example.consensus.controllers;

import com.example.consensus.entities.Forecast;
import com.example.consensus.exception.FileStorageException;
import com.example.consensus.services.ForecastService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?>  getAllForecastsByCompanyId(@PathVariable(name="id") Long id) {
        List<Forecast> forecasts = forecastService.getAllForecastsByCompanyId(id);
        return new ResponseEntity<>(forecasts, HttpStatus.OK);
    }

    @GetMapping("/forecast/all/time")
    public ResponseEntity<?>  getAllForecastTime() {
        Long start = System.currentTimeMillis();
        List<Forecast> forecasts = forecastService.getAllForecasts();
        Long end = System.currentTimeMillis();
        return new ResponseEntity<>("Время обработки запроса = " + (end - start), HttpStatus.OK);
    }

    @GetMapping("/company/{id}/avg/forecast")
    public ResponseEntity<?>  getAvgForecastForCompany(@PathVariable(name="id") Long id) {
        Long avgPrice = forecastService.getAvgForecastForCompany(id);
        if (avgPrice == null) {
            return new ResponseEntity<>("Отсутствуют прогнозы на данную компанию", HttpStatus.OK);
        }
        return new ResponseEntity<>("Средняя прогнозируемая цена акции = " + avgPrice, HttpStatus.OK);
    }


    @GetMapping("/forecast/{id}")
    public ResponseEntity<?> getForecastById(@PathVariable(name = "id") Long id) {
        Forecast forecast;
        try {
            forecast = forecastService.getForecastById(id);
        }  catch (FileStorageException exc) {
            return new ResponseEntity<>("Прогноз с id = " + id + " не найден!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(forecast, HttpStatus.OK);
    }

    @PutMapping("/forecast/{id}")
    public ResponseEntity<?> updateForecastById(@PathVariable(name = "id") Long id, @RequestBody Forecast forecastDetails) {
        Forecast forecast;
        try {
            forecast = forecastService.updateForecastById(id, forecastDetails);
        }  catch (FileStorageException exc) {
            return new ResponseEntity<>("Прогноз с id = " + id + " не найден!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(forecast, HttpStatus.OK);
    }

    @PostMapping("/forecast/")
    public ResponseEntity<?> addForecast(@RequestBody Forecast forecastDetails) {
        Forecast forecast;
        forecast = forecastService.addForecast(forecastDetails);
        return new ResponseEntity<>(forecast, HttpStatus.OK);
    }

    @DeleteMapping("/forecast/{id}")
    public ResponseEntity<?> deleteForecast(@PathVariable(name = "id") Long id) {
        Forecast forecast;
        try {
            forecast = forecastService.deleteForecast(id);
        }  catch (FileStorageException exc) {
            return new ResponseEntity<>("Прогноз с id = " + id + " не найден!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(forecast, HttpStatus.OK);
    }
}
