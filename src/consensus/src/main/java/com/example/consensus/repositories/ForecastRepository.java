package com.example.consensus.repositories;

import com.example.consensus.entities.Forecast;
import com.example.consensus.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Transactional
@Repository
public interface ForecastRepository extends JpaRepository<Forecast, Long> {
    List<Forecast> findByCompanyForForecastsId(Long companyId);

    @Query(value = "SELECT avg(goal_price) FROM forecast JOIN company c on forecast.company_id = c.id" +
            " WHERE c.id = ?1", nativeQuery = true)
    Double getAvgGoalPrice(Long companyId);
}

