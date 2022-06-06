package com.example.consensus.repositories;

import com.example.consensus.entities.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional
@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    List<News> findByCompanyForNewsId(Long companyId);
}
