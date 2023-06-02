package com.example.NamMQI.Online.Workshop.controller;



import com.example.NamMQI.Online.Workshop.model.Role;
import com.example.NamMQI.Online.Workshop.model.User;
import com.example.NamMQI.Online.Workshop.repository.RoleRepository;
import com.example.NamMQI.Online.Workshop.repository.UserRepository;
import com.example.NamMQI.Online.Workshop.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.security.core.Authentication;
        import org.springframework.security.core.context.SecurityContextHolder;
        import org.springframework.security.core.userdetails.UserDetails;
        import org.springframework.stereotype.Controller;
        import org.springframework.web.bind.annotation.GetMapping;

        import java.util.Collection;

@Controller
public class AdminController  {

//    @Autowired
//    RoleRepository roleRepository;
//    @Autowired
//    UserRepository userRepository;
//
//    @Autowired
//    UserServiceImpl userServiceImpl;
//
//    @GetMapping("/login")
//    public String login() {
//        return "login";
//    }
//
//    @GetMapping("/")
//    public String home() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Object principal = authentication.getPrincipal();
//        String username = ((UserDetails) principal).getUsername();
//        User user = userServiceImpl.getUser(username);
//        Collection<Role> roles = user.getRoles();
//
//        User admin = userServiceImpl.getUser("admin");
//        Collection<Role> adminRoles = admin.getRoles();
//
//
//        boolean equals = adminRoles.equals(roles);
//
//        return equals ? "redirect:/admin" : "users";
//    }

}