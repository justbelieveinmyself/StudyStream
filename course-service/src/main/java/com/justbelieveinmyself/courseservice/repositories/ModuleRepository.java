package com.justbelieveinmyself.courseservice.repositories;

import com.justbelieveinmyself.courseservice.domains.entities.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {
}
