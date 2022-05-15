package com.example.consensus.repositories;

import com.example.consensus.entities.Forecast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ForecastRepository extends JpaRepository<Forecast, Long> {
    List<Forecast> findByCompanyForForecastsId(Long companyId);
}
