package com.example.NamMQI.Online.Workshop.controller;

import com.example.NamMQI.Online.Workshop.dto.MessageDto;
import com.example.NamMQI.Online.Workshop.dto.UserDto;
import com.example.NamMQI.Online.Workshop.model.Admin;
import com.example.NamMQI.Online.Workshop.model.Role;
import com.example.NamMQI.Online.Workshop.model.User;
import com.example.NamMQI.Online.Workshop.repository.RoleRepository;
import com.example.NamMQI.Online.Workshop.repository.UserRepository;
import com.example.NamMQI.Online.Workshop.service.UserService;


import javax.validation.Valid;

import com.example.NamMQI.Online.Workshop.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@Controller
public class AuthController {

    private UserService userService;



    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserServiceImpl userServiceImpl;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public String home() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        User user = userServiceImpl.getUser(username);
        Collection<Role> roles = user.getRoles();
        User admin = userServiceImpl.getUser("admin");
        Collection<Role> adminRoles = admin.getRoles();
        boolean equals = adminRoles.equals(roles);
        return equals ? "redirect:/admin" : "userpage";
    }





    public AuthController(UserService userService) {
        this.userService = userService;


    }





    @GetMapping("/user_message")
    public String message(){
        return "user_message";
    }


    @GetMapping("/cabinet")
    public  String cabinet(){
        return "userpage";
    }


    @GetMapping("/info")
    public  String info(){
        return "userinfo";
    }



    @GetMapping("/apply")
    public  String apply(){
        return "apply_page";
    }


//    @GetMapping("/login")
//    public String loginForm() {
//        return "login";
//    }


    // handler method to handle user registration request
    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    // handler method to handle register user form submit request
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto user,
                               BindingResult result,
                               Model model){
        User existing = userService.findByUsername(user.getUsername());
        if (existing != null) {
            result.rejectValue("username", null, "Ushbu foydalanuvchi nomi bilan ro'yxatdan o'tgan hisob allaqachon mavjud");
        }
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "register";
        }
        userService.saveUser(user);
        return "redirect:/register?success";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute() User user, Model model) {
        User authenticated = userService.findBYUsernameAndPassword(user.getUsername(), user.getPassword());
        if (authenticated!=null){
            model.addAttribute("userLogin",authenticated.getUsername());
            return "users";
        }
        else {
            return "login";
        }
    }
//    return "userpage";
//    }

//    adminlogin


    @PostMapping("/messageSave")
    public String savemessage(@Valid@ModelAttribute("message") MessageDto messageDto,
                                    BindingResult result,
                                    Model model){
        model.addAttribute("message",messageDto);
        userService.saveMessage(messageDto);

        return "redirect:/apply";

    }


    @GetMapping("/admin")
    public String listRegisteredUsers(Model model){
        List<UserDto> users = userService.findAllUsers();
//        List<MessageDto> messages=userService.findAllMessage();
        model.addAttribute("users", users );
//        model.addAttribute("messages",messages );
        return "users";
    }
    @GetMapping("/message")
    @PreAuthorize("hasRole('ADMIN')")
    public String listRegisterMessages(Model model){
        List<MessageDto> messages=userService.findAllMessage();
        model.addAttribute("messages", messages);
        return "messages";
    }


//    --------------------------

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable(value = "id") long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }






}
