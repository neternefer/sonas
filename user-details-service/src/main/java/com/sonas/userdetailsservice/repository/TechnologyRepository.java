package com.sonas.userdetailsservice.repository;

import com.sonas.userdetailsservice.dao.Technology;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TechnologyRepository extends JpaRepository<Technology, Long> {
}
