package com.example.consensus.repositories;

import com.example.consensus.entities.Company;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CompanyRepositoryTest {

    @Autowired
    private CompanyRepository companyRepository;

    private Company company1, company2;
    List<Company> companyList;

    @BeforeEach
    void setUp() {
        companyList = new ArrayList<>();
        company1 = new Company();
        company2 = new Company();
        company1.setId(1L);
        company2.setName("Test company 1");
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
    @Rollback(value = false)
    @Order(1)
    public void whenSaveCompanyItShouldReturnCompany() {
        companyRepository.save(company1);
        assertThat(company1).isEqualTo(companyRepository.getById(company1.getId()));
    }

    @Test
    @DisplayName("find all companies test")
    @Rollback(value = false)
    @Order(2)
    public void getAllCompaniesShouldReturnAllCompanies() {
        companyRepository.save(company1);
        companyRepository.save(company2);

        assertThat(companyRepository.findAll()).isEqualTo(companyList);
    }

    @Test
    @DisplayName("find company by id test")
    @Rollback(value = false)
    @Order(3)
    public void givenIdThenShouldReturnCompanyOfThatId() {
        companyRepository.save(company1);
        AssertionsForClassTypes.assertThat(companyRepository.findById(company1.getId()).get()).isEqualTo(company1);
    }

    @Test
    @DisplayName("delete company by id test")
    @Order(4)
    public void givenIdTODeleteThenShouldDeleteTheCompany() {
        companyRepository.save(company1);
        companyRepository.delete(company1);
        assertThat(companyRepository.findById(company1.getId()).isPresent()).isEqualTo(false);
    }

    @Test
    @DisplayName("update company by id test")
    @Rollback(value = false)
    @Order(5)
    public void updateCompanyByIdShouldUpdateCompany() {
        companyRepository.save(company1);
        Company company = companyRepository.getById(company1.getId());
        company.setName("Update");
        assertThat(Objects.equals(companyRepository.getById(company1.getId()).getName(), "Update")).isTrue();
    }
}