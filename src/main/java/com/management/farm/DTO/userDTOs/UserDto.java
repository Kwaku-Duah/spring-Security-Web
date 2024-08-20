package com.management.farm.DTO.userDTOs;
import jakarta.validation.constraints.*;

import lombok.*;

@Data
public class UserDto {
    private Long id;
    @NotBlank(message = "Username cannot be blank")
    private String name;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    private String email;

    private String phoneNumber;
    private String password;
   
}
