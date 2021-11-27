package com.sonas.portfolioservice.repository;

import com.sonas.portfolioservice.dao.MinTechnology;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MinTechRepository extends JpaRepository<MinTechnology, Long> {

    List<MinTechnology> findByPortfolioId(long portfolioId);
}
