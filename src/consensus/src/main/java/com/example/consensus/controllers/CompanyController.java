package com.example.consensus.controllers;

import com.example.consensus.entities.Company;
import com.example.consensus.exception.FileStorageException;

import com.example.consensus.services.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/company/all")
    public ResponseEntity<List<?>> getAllCompanies() {
        List<Company> companies = companyService.getAllCompanies();
        return new ResponseEntity<>(companies, HttpStatus.OK);
    }

    @GetMapping("/company/{id}")
    public ResponseEntity<?> getCompanyById(@PathVariable Long id) {
        Company company;
        try {
            company = companyService.getCompanyById(id);
        }  catch (FileStorageException exc) {
            return new ResponseEntity<>("Компания с id = " + id + " не найдена!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(company, HttpStatus.OK);
    }

    @PutMapping("/company/{id}")
    public ResponseEntity<?> updateCompany(@PathVariable Long id, @RequestBody Company companyDetails) {
        Company company;
        try {
            company = companyService.updateCompanyById(id, companyDetails);
        }  catch (FileStorageException exc) {
            return new ResponseEntity<>("Компания с id = " + id + " не найдена!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(company, HttpStatus.OK);
    }

    @PostMapping("/company/add")
    public ResponseEntity<?> addCompany(@Valid @RequestBody Company company) {
        Company companyNew = companyService.addCompany(company);
        return new ResponseEntity<>(companyNew, HttpStatus.OK);
    }

    @DeleteMapping("/company/{id}")
    public ResponseEntity<?> deleteCompany(@PathVariable Long id) {
        try {
            companyService.deleteCompany(id);
        }  catch (Exception exc) {
            return new ResponseEntity<>("Компания с id = " + id + " не найдена!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Компания с id = " + id + " успешно удалена!", HttpStatus.OK);
    }
}
