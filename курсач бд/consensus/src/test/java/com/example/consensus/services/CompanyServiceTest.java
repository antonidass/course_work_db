package com.example.consensus.services;

import com.example.consensus.exception.FileStorageException;
import com.example.consensus.repositories.CompanyRepository;
import com.example.consensus.entities.Company;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompanyServiceTest {

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private CompanyService companyService;

    private Company company1, company2;
    List<Company> companyList;

    @BeforeEach
    void setUp() {
        companyList = new ArrayList<>();
        company1 = new Company();
        company2 = new Company();
        company1.setId(1L);
        company2.setName("Test company 1");
        company2 = new Company();
        company2.setId(2L);
        company2.setName("Test company 2");
        companyList.add(company1);
        companyList.add(company2);
    }

    @AfterEach
    void tearDown() {
        company1 = company2 = null;
        companyList = null;
    }

    @Test
    @DisplayName("save company test")
    public void whenSaveCompanyItShouldReturnCompany() {
        when(companyRepository.save(any())).thenReturn(company1);
        companyService.addCompany(company1);
        verify(companyRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("find all companies test")
    public void getAllCompaniesShouldReturnAllCompanies() {
        companyRepository.save(company1);
        companyRepository.save(company2);

        when(companyRepository.findAll()).thenReturn(companyList);
        List<Company> companies = companyService.getAllCompanies();
        assertThat(companies).isEqualTo(companyList);

        verify(companyRepository, times(1)).save(company1);
        verify(companyRepository, times(1)).save(company2);
        verify(companyRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("find company by id test")
    public void givenIdThenShouldReturnCompanyOfThatId() {
        when(companyRepository.findById(1L)).thenReturn(Optional.ofNullable(company1));
        assertThat(companyService.getCompanyById(company1.getId())).isEqualTo(company1);
    }

    @Test
    @DisplayName("delete company by id test")
    public void givenIdTODeleteThenShouldDeleteTheCompany() {
        when(companyRepository.findById(company1.getId())).thenReturn(Optional.of(company1));
        companyService.deleteCompany(company1.getId());
        verify(companyRepository, times(1)).deleteById(company1.getId());
    }

    @Test
    @DisplayName("delete company that is not exists test")
    public void shouldThrowExceptionWhenCompanyDoesntExist() {
        FileStorageException fileStorageException = assertThrows(FileStorageException.class, () -> {
            companyService.deleteCompany(22L);
        });
        String expectedMessage = "Company with id = 22 not exists!";
        assertThat(expectedMessage.contains(fileStorageException.getMessage())).isTrue();
    }

    @Test
    @DisplayName("update company by id test")
    public void updateCompanyByIdShouldUpdateCompany() {
        when(companyRepository.findById(company1.getId())).thenReturn(Optional.of(company1));
        companyService.updateCompanyById(company1.getId(), company2);
        Company companyUpdated = companyService.getCompanyById(company1.getId());
        assertThat(companyUpdated.getName().equals(company2.getName())).isTrue();
    }
}