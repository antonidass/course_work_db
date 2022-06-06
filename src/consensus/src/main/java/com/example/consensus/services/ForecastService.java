package com.example.consensus.services;

import com.example.consensus.exception.FileStorageException;
import com.example.consensus.repositories.ForecastRepository;
import com.example.consensus.entities.Forecast;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ForecastService {
    private final ForecastRepository forecastRepository;

    public ForecastService(ForecastRepository forecastRepository) {
        this.forecastRepository = forecastRepository;
    }

    public List<Forecast> getAllForecastsByCompanyId(Long id) {
        return forecastRepository.findByCompanyForForecastsId(id);
    }

    public Forecast getForecastById(Long id) {
        return forecastRepository.findById(id).orElseThrow(() ->  new FileStorageException("Forecast with id = " + id + " not exists!"));
    }

    public Forecast updateForecastById(Long id, Forecast forecast)  {
        Forecast forecastNew = forecastRepository.findById(id).orElseThrow(() -> new FileStorageException("Forecast with id = " + id + " not exists!"));
        forecastNew.setFields(forecast);
        return forecastRepository.save(forecastNew);
    }

    public Forecast addForecast(Forecast forecast) {
        return forecastRepository.save(forecast);
    }

    public void deleteForecast(Long id) {
        forecastRepository.deleteById(id);
    }

    public Double getAvgForecastForCompany(Long id) {
        return forecastRepository.getAvgGoalPrice(id);
    }

    public List<Forecast> getAllForecasts() {
        List<Forecast> forecasts = forecastRepository.findAll();
        return forecasts;
    }
}
