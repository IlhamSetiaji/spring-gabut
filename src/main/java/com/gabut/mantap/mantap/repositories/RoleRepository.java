package com.gabut.mantap.mantap.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gabut.mantap.mantap.entities.Role;
import com.gabut.mantap.mantap.entities.enums.RoleEnum;

public interface RoleRepository extends JpaRepository<Role, String> {
  Optional<Role> findByName(RoleEnum name);
}
