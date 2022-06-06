package com.example.consensus.repositories;

import com.example.consensus.entities.Company;
import com.example.consensus.entities.Forecast;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ForecastRepositoryTest {
    @Autowired
    private ForecastRepository forecastRepository;

    @Autowired
    private CompanyRepository companyRepository;

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
    @Order(1)
    @Rollback(value = false)
    public void whenSaveForecastItShouldReturnForecast() {
        companyRepository.save(company);
        forecastRepository.save(forecast1);
        assertThat(forecast1).isEqualTo(forecastRepository.getById(forecast1.getId()));
    }

    @Test
    @DisplayName("find all forecasts by company test")
    @Order(2)
    @Rollback(value = false)
    public void getAllForecastByCompanyIdShouldReturnAllForecasts() {
        companyRepository.save(company);
        forecastRepository.save(forecast1);
        forecastRepository.save(forecast2);

        List<Forecast> forecasts = forecastRepository.findByCompanyForForecastsId(company.getId());
        AssertionsForClassTypes.assertThat(forecasts).isEqualTo(forecastList);
    }

    @Test
    @DisplayName("find forecast by id test")
    @Rollback(value = false)
    @Order(3)
    public void givenIdThenShouldReturnForecastOfThatId() {
        companyRepository.save(company);
        forecastRepository.save(forecast1);
        AssertionsForClassTypes.assertThat(forecastRepository.getById(forecast1.getId())).isEqualTo(forecast1);
    }

    @Test
    @DisplayName("delete forecast by id test")
    @Order(4)
    public void givenIdTODeleteThenShouldDeleteTheForecast() {
        companyRepository.save(company);
        forecastRepository.save(forecast1);
        forecastRepository.delete(forecast1);
        assertThat(forecastRepository.findById(forecast1.getId()).isPresent()).isEqualTo(false);
    }

    @Test
    @DisplayName("update forecast by id test")
    @Rollback(value = false)
    @Order(5)
    public void updateCompanyByIdShouldUpdateCompany() {
        companyRepository.save(company);
        forecastRepository.save(forecast2);

        Forecast forecast = forecastRepository.getById(forecast2.getId());
        forecast.setForecast("Update");
        assertThat(forecastRepository.findById(forecast2.getId()).get().getForecast()).isEqualTo("Update");
    }
}