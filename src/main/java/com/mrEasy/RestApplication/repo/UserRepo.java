package com.mrEasy.RestApplication.repo;

import com.mrEasy.RestApplication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
