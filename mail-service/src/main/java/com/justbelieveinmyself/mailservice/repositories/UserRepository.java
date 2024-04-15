package com.justbelieveinmyself.mailservice.repositories;

import com.justbelieveinmyself.mailservice.domains.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
