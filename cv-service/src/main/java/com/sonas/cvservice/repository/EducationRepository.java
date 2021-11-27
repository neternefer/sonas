package com.sonas.cvservice.repository;

import com.sonas.cvservice.dao.Education;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EducationRepository extends JpaRepository<Education, Long> {

    List<Education> findByCvId(long cvId);
}
