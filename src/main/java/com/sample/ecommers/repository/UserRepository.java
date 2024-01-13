package com.sample.ecommers.repository;

import com.sample.ecommers.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <User, Long> {
    boolean existsByEmail(String email);
}
