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


    public User registerUser(UserDto userDto){
        if (userRepository.existsByEmail(userDto.getEmail())){
            throw new EmailAlreadyInUseException("Email already in use!");
        }


        User user = new User();

        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

         // Assign "GUEST" role
         Role guestRole = roleService.findOrCreateRole("GUEST");
         user.setRoles(Collections.singleton(guestRole));
 
        return userRepository.save(user);
    }

    public Map<String, Object> loginUser(String email, String password) throws Exception {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (passwordEncoder.matches(password, user.getPassword())) {

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

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
