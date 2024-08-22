package com.management.farm.Service.userServices;

import com.management.farm.Model.userModels.Role;
import com.management.farm.Repository.userRepositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;


    /**
     * Finds an existing role by its name or creates and saves a new role if it does not exist.
     *
     * @param roleName the name of the role to find or create
     * @return the existing or newly created Role
     */
    public Role findOrCreateRole(String roleName) {
        Optional<Role> roleOpt = roleRepository.findByRole(roleName);
        if (roleOpt.isPresent()) {
            return roleOpt.get();
        } else {
            Role role = new Role();
            role.setRole(roleName);
            return roleRepository.save(role);
        }
    }
}
