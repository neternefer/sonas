package com.sonas.portfolioservice.repository;

import com.sonas.portfolioservice.dao.MinTechnology;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MinTechRepository extends JpaRepository<MinTechnology, Long> {
}
