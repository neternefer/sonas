package com.sonas.jobservice.repository;

import com.sonas.jobservice.dao.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {
}
