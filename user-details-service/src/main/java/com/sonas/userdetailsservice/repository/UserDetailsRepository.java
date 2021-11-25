package com.sonas.userdetailsservice.repository;

import com.sonas.userdetailsservice.dao.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {

    Optional<UserDetails> findByUserId(long userId);
}
