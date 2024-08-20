package com.management.farm.Repository.userRepositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.management.farm.Model.userModels.Role;


public interface RoleRepository extends JpaRepository<Role,Long> {
   
    Optional<Role> findByRole(String role);
}
