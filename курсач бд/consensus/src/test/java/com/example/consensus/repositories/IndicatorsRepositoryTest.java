package com.example.consensus.repositories;

import com.example.consensus.entities.Company;
import com.example.consensus.entities.Indicators;
import com.example.consensus.exception.FileStorageException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class IndicatorsRepositoryTest {

    @Autowired
    private IndicatorsRepository indicatorsRepository;

    @Autowired
    private CompanyRepository companyRepository;

    private Indicators indicators1, indicators2;
    private Company company1;
    List<Indicators> indicatorsList;

    @BeforeEach
    void setUp() {
        indicatorsList = new ArrayList<>();
        indicators1 = new Indicators();
        indicators2 = new Indicators();
        indicators1.setId(1L);
        indicators2.setId(2L);
        company1 = new Company();
        company1.setId(1L);
        company1.setName("Test company 1");
        indicators1.setCompany(company1);
        indicators1.setIncome(35);
        indicators2.setIncome(45);
        indicatorsList.add(indicators1);
        indicatorsList.add(indicators2);
    }

    @AfterEach
    void tearDown() {
        indicators1 = indicators2 = null;
        indicatorsList = null;
        company1 = null;
    }

    @Test
    @DisplayName("find indicators by company id test")
    @Order(1)
    @Rollback(value = false)
    public void getIndicatorsByCompanyIdShouldReturnIndicators() {
        companyRepository.save(company1);
        indicatorsRepository.save(indicators1);
        Indicators indicators = indicatorsRepository.findByCompanyId(company1.getId());
        assertThat(indicators).isEqualTo(indicators1);
    }

    @Test
    @Order(2)
    @Rollback(value = false)
    @DisplayName("delete indicators by company id test")
    public void givenIdTODeleteThenShouldDeleteTheIndicators() {
        companyRepository.save(company1);
        indicatorsRepository.save(indicators1);
        Indicators indicators = indicatorsRepository.findByCompanyId(company1.getId());
        indicatorsRepository.delete(indicators);
        assertThat(indicatorsRepository.findByCompanyId(company1.getId())).isEqualTo(null);

    }

    @Test
    @DisplayName("update indicators by company_id test")
    @Rollback(value = false)
    @Order(3)
    public void updateIndicatorsByIdShouldUpdateIndicators() {
        companyRepository.save(company1);
        indicatorsRepository.save(indicators1);

        Indicators indicators = indicatorsRepository.findByCompanyId(company1.getId());
        indicators.setIncome(88);
        assertThat(indicatorsRepository.findByCompanyId(company1.getId()).getIncome() == 88).isTrue();
    }
}