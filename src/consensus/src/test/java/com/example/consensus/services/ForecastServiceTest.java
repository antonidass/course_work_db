package com.example.consensus.services;

import com.example.consensus.entities.Company;
import com.example.consensus.entities.Forecast;
import com.example.consensus.exception.FileStorageException;
import com.example.consensus.repositories.ForecastRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ForecastServiceTest {

    @Mock
    private ForecastRepository forecastRepository;

    @InjectMocks
    private ForecastService forecastService;

    private Forecast forecast1, forecast2;
    private Company company;
    List<Forecast> forecastList;

    @BeforeEach
    void setUp() {
        forecastList = new ArrayList<>();
        forecast1 = new Forecast();
        forecast2 = new Forecast();
        forecast1.setId(1L);
        forecast2.setId(2L);
        company = new Company();
        company.setId(1L);
        company.setName("Test company");
        forecast1.setCompanyForForecasts(company);
        forecast2.setCompanyForForecasts(company);
        forecast1.setForecast("Forecast 1");
        forecast2.setForecast("Forecast 2");
        forecastList.add(forecast1);
        forecastList.add(forecast2);
    }

    @AfterEach
    void tearDown() {
        forecast1 = forecast2 = null;
        forecastList = null;
        company = null;
    }

    @Test
    @DisplayName("save forecast test")
    public void whenSaveForecastItShouldReturnForecast() {
        when(forecastRepository.save(any())).thenReturn(forecast1);
        forecastService.addForecast(forecast1);
        verify(forecastRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("find all forecasts by company id test")
    public void getAllForecastByCompanyIdShouldReturnAllForecasts() {
        forecastRepository.save(forecast1);
        forecastRepository.save(forecast2);

        when(forecastRepository.findByCompanyForForecastsId(company.getId())).thenReturn(forecastList);
        List<Forecast> forecasts = forecastService.getAllForecastsByCompanyId(company.getId());
        assertThat(forecasts).isEqualTo(forecastList);

        verify(forecastRepository, times(1)).save(forecast1);
        verify(forecastRepository, times(1)).save(forecast2);
        verify(forecastRepository, times(1)).findByCompanyForForecastsId(company.getId());
    }

    @Test
    @DisplayName("find forecast by id test")
    public void givenIdThenShouldReturnForecastOfThatId() {
        when(forecastRepository.findById(1L)).thenReturn(Optional.ofNullable(forecast1));
        assertThat(forecastService.getForecastById(forecast1.getId())).isEqualTo(forecast1);
    }

    @Test
    @DisplayName("delete forecast by id test")
    public void givenIdTODeleteThenShouldDeleteTheForecast() {
        when(forecastRepository.findById(forecast1.getId())).thenReturn(Optional.of(forecast1));
        forecastService.deleteForecast(forecast1.getId());
        verify(forecastRepository, times(1)).deleteById(forecast1.getId());
    }

    @Test
    @DisplayName("delete forecast that is not exists test")
    public void shouldThrowExceptionWhenForecastDoesntExist() {
        FileStorageException fileStorageException = assertThrows(FileStorageException.class, () -> {
            forecastService.deleteForecast(22L);
        });
        String expectedMessage = "Forecast with id = 22 not exists!";
        assertThat(expectedMessage.contains(fileStorageException.getMessage())).isTrue();
    }

    @Test
    @DisplayName("update forecast by id test")
    public void updateCompanyByIdShouldUpdateCompany() {
        when(forecastRepository.findById(forecast1.getId())).thenReturn(Optional.of(forecast1));
        forecastService.updateForecastById(forecast1.getId(), forecast2);
        Forecast forecastUpdated = forecastService.getForecastById(forecast1.getId());
        assertThat(forecastUpdated.getForecast().equals(forecast2.getForecast())).isTrue();
    }
}