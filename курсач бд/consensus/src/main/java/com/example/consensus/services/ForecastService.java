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
        System.out.println(forecast);
        return forecastRepository.save(forecast);
    }

    public Forecast deleteForecast(Long id) {
        Forecast forecast =  forecastRepository.findById(id).orElseThrow(() -> new FileStorageException("Forecast with id = " + id + " not exists!"));
        forecastRepository.deleteById(id);
        return forecast;
    }

    public Long getAvgForecastForCompany(Long id) {
        List<Forecast> forecasts = forecastRepository.findByCompanyForForecastsId(id);
        if (forecasts.size() == 0) {
            return null;
        }
        Long avg_price = 0L;
        for (Forecast forecast : forecasts) {
            avg_price += forecast.getGoalPrice();
        }
        return avg_price / forecasts.size();
    }

    public List<Forecast> getAllForecasts() {
        List<Forecast> forecasts = forecastRepository.findAll();
        return forecasts;
    }
}
