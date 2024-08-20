package com.management.farm.Controller.userControllers;


import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.management.farm.DTO.userDTOs.LoginDto;
import com.management.farm.DTO.userDTOs.UserDto;
import com.management.farm.Exception.UserExceptions.EmailAlreadyInUseException;
import com.management.farm.Model.userModels.User;
import com.management.farm.Service.userServices.UserService;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/api/users")
public class UserController {

   @Autowired
    private  UserService userService;

 
    @PostMapping("/signup")
    public ResponseEntity<User> registerUser(@RequestBody UserDto userDto){
        User registeredUser = userService.registerUser(userDto);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginUser(@RequestBody LoginDto loginDto) {
        try {
            Map<String, Object> response = userService.loginUser(loginDto.getEmail(), loginDto.getPassword());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Return specific error messages based on the exception
            String errorMessage = e.getMessage();
            if ("Invalid password".equals(errorMessage)) {
                return ResponseEntity.status(401).body(Collections.singletonMap("error", "Invalid password"));
            } else if ("User not found".equals(errorMessage)) {
                return ResponseEntity.status(401).body(Collections.singletonMap("error", "User not found"));
            } else {
                return ResponseEntity.status(500).body(Collections.singletonMap("error", "An error occurred"));
            }
        }
    }

    @ExceptionHandler(EmailAlreadyInUseException.class)
    public ResponseEntity<String> handleEmailAlreadyInUseException(EmailAlreadyInUseException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
    
}
