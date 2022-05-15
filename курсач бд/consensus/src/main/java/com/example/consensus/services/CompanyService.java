package com.example.consensus.services;

import com.example.consensus.exception.FileStorageException;
import com.example.consensus.repositories.CompanyRepository;
import com.example.consensus.entities.Company;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public Company getCompanyByName(String name) {
        Company company = companyRepository.findByName(name);

        if (company == null) {
            throw new FileStorageException("Company with name = " + name + " not exists!");
        }
        return company;
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

    public Company deleteCompany(Long id) {
        Company company = companyRepository.findById(id).orElseThrow(() -> new FileStorageException("Company with id = " + id + " not exists!"));
        companyRepository.deleteById(id);
        return company;
    }
}
