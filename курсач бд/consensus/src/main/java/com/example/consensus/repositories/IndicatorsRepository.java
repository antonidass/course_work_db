package com.example.consensus.repositories;

import com.example.consensus.entities.Indicators;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IndicatorsRepository extends JpaRepository<Indicators, Long> {
    Indicators findByCompanyId(Long company);
}
