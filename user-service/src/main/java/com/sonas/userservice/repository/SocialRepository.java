package com.sonas.userservice.repository;

import com.sonas.userservice.dao.Social;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SocialRepository extends JpaRepository<Social, Long> {

    List<Social> findByContactId(long contactId);
}
