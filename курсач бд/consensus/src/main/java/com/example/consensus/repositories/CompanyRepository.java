package com.example.consensus.repositories;

import com.example.consensus.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
}
