package com.management.farm.Service.userServices;


import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.management.farm.Exception.UserExceptions.EmailAlreadyInUseException;

import com.management.farm.DTO.userDTOs.UserDto;
import com.management.farm.Model.userModels.Role;
import com.management.farm.Model.userModels.User;
import com.management.farm.Repository.userRepositories.UserRepository;
import com.management.farm.Security.JwtUtil;
import jakarta.validation.Valid;

@Service
public class UserService {

    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
 

    /**
    * Registers a new user with the provided details.
    * <p>
    * This method checks if the email is already in use, and if not,
    * it creates a new user with the provided information. The password
    * is assigned to the user.
    * </p>
    *
    * @param userDto The data transfer object containing user details.
    * @return The newly registered user.
    * @throws EmailAlreadyInUseException if the email is already registered.
    */
    public User registerUser(@Valid UserDto userDto){
        if (userRepository.existsByEmail(userDto.getEmail())){
            throw new EmailAlreadyInUseException("Email already in use!");
        }


        User user = new User();

        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPhoneNumber(userDto.getPhoneNumber());

        // password encoding to satisfy output encoding
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

         // Role Based Access Control
         Role guestRole = roleService.findOrCreateRole("GUEST");
         user.setRoles(Collections.singleton(guestRole));
 
        return userRepository.save(user);
    }



   /**
     * Logs in a user and generates a JWT token.
     *
     * @param email    User email.
     * @param password User password.
     * @return Map containing user details and JWT token.
     * @throws Exception if credentials are invalid.
     */
    public Map<String, Object> loginUser(String email, String password) throws Exception {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (passwordEncoder.matches(password, user.getPassword())) {


                // JWT token generation for authentication and authorization
                String token = jwtUtil.generateToken(user.getId(), user.getRoles().iterator().next().getRole());

                // Prepare response map with LinkedHashMap for ordered output
                Map<String, Object> response = new LinkedHashMap<>();
                response.put("id", user.getId());
                response.put("name", user.getName());
                response.put("email", user.getEmail());
                response.put("phoneNumber", user.getPhoneNumber());
                response.put("roles", user.getRoles().iterator().next().getRole());
                response.put("token", token);

                return response;
                
            } else {
                throw new Exception("Invalid credentials");
            }
        } else {
            throw new Exception("User not found");
        }
    }


      /**
     * Fetches all registered users.
     *
     * @return List of users.
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
