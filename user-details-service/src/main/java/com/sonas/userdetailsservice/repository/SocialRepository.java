package com.sonas.userdetailsservice.repository;

import com.sonas.userdetailsservice.dao.Social;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SocialRepository extends JpaRepository<Social, Long> {
}
