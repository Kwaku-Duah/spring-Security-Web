package com.management.farm.Controller.Views;


import com.management.farm.DTO.userDTOs.LoginDto;
import com.management.farm.DTO.userDTOs.UserDto;
import com.management.farm.Service.userServices.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ThymeleafController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String homePage(Model model) {
        // You can add attributes to the model here if needed
        return "index"; // This returns the index.html Thymeleaf template
    }

    @GetMapping("/signup")
    public String signupPage(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "signup";
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("loginDto", new LoginDto());
        return "login";
    }

    @GetMapping("/welcome-user")
    public String welcomePage(Model model) {
        return "welcome";
    }

    @GetMapping("/session-expired")
    public String sessionExpired(Model model) {
        return "session-expired";
    }
    @GetMapping("/users")
    public String usersPage(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }
}
