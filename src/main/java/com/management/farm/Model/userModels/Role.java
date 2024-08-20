package com.management.farm.Model.userModels;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name = "app_roles")

public class Role {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String role;

}
