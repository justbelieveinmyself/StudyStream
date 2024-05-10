package com.justbelieveinmyself.authservice.repository;

import com.justbelieveinmyself.authservice.domains.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT (COUNT(u) > 0) FROM User u WHERE u.username = ?1 OR u.email = ?2")
    boolean existsByUsernameOrEmail(String username, String email);
    @Query("SELECT (COUNT(u) > 0) FROM User u WHERE u.email = ?1")
    boolean existsByEmail(String email);
    @Query("SELECT u FROM User u WHERE u.username = ?1")
    Optional<User> findByUsername(String username);
    @Query("SELECT u FROM User u WHERE u.activationCode = ?1")
    Optional<User> findByActivationCode(String activationCode);
}
