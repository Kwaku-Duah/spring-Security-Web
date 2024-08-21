package com.management.farm.DTO.userDTOs;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
public class UserDto {
    private Long id;


// validation for all input fields in the application
    @NotBlank(message = "Username cannot be blank")
    @Size(min = 5, max = 30, message = "Username must be between 5 and 30 characters")
    private String name;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    private String email;

    @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Phone number must be valid")
    private String phoneNumber;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(
        regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$", 
        message = "Password must contain at least one digit, one lowercase letter, one uppercase letter, and one special character (@#$%^&+=)"
    )
    private String password;
}
