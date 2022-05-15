package com.example.consensus.controllers;

import com.example.consensus.entities.Company;
import com.example.consensus.services.CompanyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/all")
    public List<Company> getAllCompanies() {
        return companyService.getAllCompanies();
    }

    @GetMapping("/company/")
    public Company getCompanyByName(@RequestParam(value = "name") String name) {
        return companyService.getCompanyByName(name);
    }

    @GetMapping("/company/{id}")
    public Company getCompanyById(@PathVariable Long id) {
        return companyService.getCompanyById(id);
    }

    @PutMapping("/company/{id}")
    public Company updateCompany(@PathVariable Long id, @RequestBody Company companyDetails) {
        return companyService.updateCompanyById(id, companyDetails);
    }

    @PostMapping("/company")
    public Company addCompany(@RequestBody Company company) {
        return companyService.addCompany(company);
    }

    @DeleteMapping("/company/{id}")
    public Company deleteCompany(@PathVariable Long id) {
        return companyService.deleteCompany(id);
    }
}
