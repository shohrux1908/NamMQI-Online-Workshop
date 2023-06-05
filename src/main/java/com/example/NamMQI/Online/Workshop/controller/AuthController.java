package com.example.NamMQI.Online.Workshop.controller;

import com.example.NamMQI.Online.Workshop.dto.CombinedData;
import com.example.NamMQI.Online.Workshop.dto.MessageDto;
import com.example.NamMQI.Online.Workshop.dto.UserDto;
import com.example.NamMQI.Online.Workshop.model.Message;
import com.example.NamMQI.Online.Workshop.model.Role;
import com.example.NamMQI.Online.Workshop.model.User;
import com.example.NamMQI.Online.Workshop.repository.MessageRepository;
import com.example.NamMQI.Online.Workshop.repository.RoleRepository;
import com.example.NamMQI.Online.Workshop.repository.UserRepository;
import com.example.NamMQI.Online.Workshop.repository.UserRoleRepository;
import com.example.NamMQI.Online.Workshop.service.UserService;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.example.NamMQI.Online.Workshop.service.impl.UserNotFoundException;
import com.example.NamMQI.Online.Workshop.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Controller
public class AuthController {

    private UserService userService;


    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserServiceImpl userServiceImpl;
     private  final MessageRepository messageRepository;

    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @GetMapping("/home")
    public String basic() {
        return "index";
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





    public AuthController(UserService userService, MessageRepository messageRepository) {
        this.userService = userService;
        this.messageRepository = messageRepository;
    }





    @GetMapping("/user_message")
    public String message(){
        return "user_message";
    }


    @GetMapping("/cabinet")
    public  String cabinet(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        User user1 = userRepository.findByUsername(username);
        Long uId = user1.getId();
        return "userpage";
    }
    @GetMapping("/apply")
    public  String apply(HttpSession session, Model model,User user){
        Long userId = (Long) session.getAttribute("userId");
        model.addAttribute("user",userId);
        model.addAttribute("user",user);
        return "apply_page";
    }




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
        model.addAttribute("user", user);
        userService.saveUser(user);
        return "redirect:/register?success";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, Model model, HttpSession session) {
        User authenticated = userService.findBYUsernameAndPassword(user.getUsername(), user.getPassword());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        User user1 = userRepository.findByUsername(username);
        Long uId = user1.getId();
        if (authenticated != null) {
            model.addAttribute("userLogin", authenticated.getUsername());

            // Foydalanuvchi identifikatorini sessionga saqlash
            session.setAttribute("userId", authenticated.getId());

            return "redirect:/users";
        } else {
            return "login";
        }
    }




    @GetMapping("/save/message")
    public  String savemessage1( Model model){
       MessageDto messageDto = new MessageDto();
        model.addAttribute("messages", messageDto);
        return "apply_page";
    }
    @PostMapping("/save/message")
    public String savemessage(@ModelAttribute() MessageDto messages,
                                    BindingResult result,
                              Model model){
        model.addAttribute("messages",messages);
        userService.saveMessage(messages);
        return "apply_page";
    }




    @GetMapping("/admin")
    public String listRegisteredUsers(Model model) {
        List<UserDto> users = userService.findAllUsers();
        List<MessageDto> messages = userService.findAllMessage();

        List<CombinedData> combinedDataList = new ArrayList<>();
        int maxSize = Math.max(users.size(), messages.size());
        for (int i = 0; i < maxSize; i++) {
            CombinedData combinedData = new CombinedData();
            if (i < users.size()) {
                combinedData.setUser(users.get(i));
            }
            if (i < messages.size()) {
                combinedData.setMessage(messages.get(i));
            }
            combinedDataList.add(combinedData);
        }

        model.addAttribute("combinedDataList", combinedDataList);
        return "users";
    }


//    --------------------------

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable(value = "id") long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }

    @GetMapping("/user/info")
    public String getUserInfo( Model model, HttpSession session) throws UserNotFoundException {
        // Foydalanuvchi identifikatorini sessiondan olish
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        User user = userRepository.findByUsername(username);
        Long uId = user.getId();
//        Long userId = (Long) session.getAttribute("userId");
//        if (uId == null) {
//            // Foydalanuvchi sessionga kiritmagan bo'lsa, avtorizatsiyadan o'tish
//            return "redirect:/login";
//        }




//        User user = userService.get(id);
        model.addAttribute("user", user);

        return "userinfo";
    }



    @GetMapping("/users/edit/{id}")
    public String showEditForm(@PathVariable("id" ) Long id, Model model, RedirectAttributes redirectAttributes){
        try {
           User user= userService.get(id);
           model.addAttribute("user",user);
           model.addAttribute("pageTitle","Edit User (ID "+id+" )");
           return "register";
        } catch (UserNotFoundException e) {
            redirectAttributes.addFlashAttribute("message","The user has been saved successfully");
            return "redirect:/admin";

        }

    }

    @GetMapping("/users/save")
    public String saveUser(User user){
        userService.save(user);
        return "redirect:/admin";
    }


}
