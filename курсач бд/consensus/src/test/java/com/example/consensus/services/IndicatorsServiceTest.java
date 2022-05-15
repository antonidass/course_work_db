package com.example.consensus.services;

import com.example.consensus.entities.Company;
import com.example.consensus.entities.Indicators;
import com.example.consensus.exception.FileStorageException;
import com.example.consensus.repositories.IndicatorsRepository;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IndicatorsServiceTest {

    @Mock
    private IndicatorsRepository indicatorsRepository;

    @InjectMocks
    private IndicatorsService indicatorsService;

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
    public void getIndicatorsByCompanyIdShouldReturnIndicators() {
        indicatorsRepository.save(indicators1);

        when(indicatorsRepository.findByCompanyId(company1.getId())).thenReturn(indicators1);
        Indicators indicators = indicatorsService.getIndicatorsByCompanyId(company1.getId());
        assertThat(indicators).isEqualTo(indicators1);

        verify(indicatorsRepository, times(1)).save(indicators1);
        verify(indicatorsRepository, times(1)).findByCompanyId(indicators.getId());
    }


    @Test
    @DisplayName("delete indicators by company id test")
    public void givenIdTODeleteThenShouldDeleteTheIndicators() {
        when(indicatorsRepository.findByCompanyId(company1.getId())).thenReturn(indicators1);
        indicatorsService.deleteIndicatorsByCompanyId(company1.getId());
        verify(indicatorsRepository, times(1)).deleteById(indicators1.getId());
    }

    @Test
    @DisplayName("delete company indicators that is not exists test")
    public void shouldThrowExceptionWhenIndicatorsDoesntExist() {
        FileStorageException fileStorageException = assertThrows(FileStorageException.class, () -> {
            indicatorsService.deleteIndicatorsByCompanyId(22L);
        });
        String expectedMessage = "Indicators with company_id = 22 not exists!";
        assertThat(expectedMessage.contains(fileStorageException.getMessage())).isTrue();
    }

    @Test
    @DisplayName("update indicators by company_id test")
    public void updateIndicatorsByIdShouldUpdateIndicators() {
        when(indicatorsRepository.findByCompanyId(company1.getId())).thenReturn(indicators1);
        indicatorsService.updateIndicatorsByCompanyId(company1.getId(), indicators2);
        Indicators indicatorsUpdated = indicatorsService.getIndicatorsByCompanyId(company1.getId());
        assertThat(indicatorsUpdated.getIncome() == indicators2.getIncome()).isTrue();
    }
}