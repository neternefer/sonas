package com.sonas.cvservice.repository;

import com.sonas.cvservice.dao.Technology;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TechnologyRepository extends JpaRepository<Technology, Long> {

    List<Technology> findByCvId(long cvId);
}
