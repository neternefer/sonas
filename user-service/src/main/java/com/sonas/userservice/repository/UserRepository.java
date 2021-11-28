package com.sonas.userservice.repository;

import com.sonas.userservice.dao.User;
import com.sonas.userservice.enums.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByUserType(UserType userType);

    User findByUsername(String username);
}
