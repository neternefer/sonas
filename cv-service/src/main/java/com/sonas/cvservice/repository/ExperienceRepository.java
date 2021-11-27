package com.sonas.cvservice.repository;

import com.sonas.cvservice.dao.Experience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Long> {

    List<Experience> findByCvId(long cvId);
}
