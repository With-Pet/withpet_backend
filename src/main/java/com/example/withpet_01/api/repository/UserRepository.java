package com.example.withpet_01.api.repository;


import com.example.withpet_01.api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(String id);;
    Optional<User> findByidAndProvider(String id, String provider);
}

