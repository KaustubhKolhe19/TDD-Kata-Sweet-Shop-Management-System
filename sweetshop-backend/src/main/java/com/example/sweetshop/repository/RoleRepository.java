package com.example.sweetshop.repository;

import com.example.sweetshop.model.Role;
import com.example.sweetshop.model.Role.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(ERole name);
}
