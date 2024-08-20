package com.management.farm.Model.userModels;


import java.util.Set;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name = "app_Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String phoneNumber;

    // perfect to avoid seriliazing the password when converting user object to json
    @JsonIgnore
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_roles",
        joinColumns =   @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")

    )

    private Set<Role> roles;

        @JsonGetter("roles")
    public String getRole() {
        // Return a single string of roles, assuming one role per user
        return roles.stream()
                     .map(Role::getRole)
                     .findFirst()
                     .orElse("No Role");
    }
    
    
}
