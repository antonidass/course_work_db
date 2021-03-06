package com.example.consensus.services;

import com.example.consensus.exception.FileStorageException;
import com.example.consensus.repositories.CompanyRepository;
import com.example.consensus.entities.Company;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CompanyService {
    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public Company getCompanyById(Long id) {
        return companyRepository.findById(id).orElseThrow(() -> new FileStorageException("Company with id = " + id + " not exists!"));
    }

    public Company updateCompanyById(long id, Company newCompany) {
        Company company = companyRepository.findById(id).orElseThrow(() -> new FileStorageException("Company with id = " + id + " not exists!"));
        company.setFields(newCompany);
        return companyRepository.save(company);
    }

    public Company addCompany(Company company) {
        return companyRepository.save(company);
    }

    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
    }
}
