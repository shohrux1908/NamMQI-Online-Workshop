package com.example.NamMQI.Online.Workshop.component;

import com.example.NamMQI.Online.Workshop.model.Role;
import com.example.NamMQI.Online.Workshop.model.User;
import com.example.NamMQI.Online.Workshop.repository.RoleRepository;
import com.example.NamMQI.Online.Workshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Value("${spring.sql.init.mode}")
    private String initialMode;
    @Override
    public void run(String... args) throws Exception {

        if (initialMode.equals("always")){

            userRepository.save(
                    new User(
                            "Admin","",
                            "","", "","",
                            "admin",
                            passwordEncoder.encode("111"),
                            List.of(new Role("ROLE_ADMIN"))
                    )
            );

        }

    }
}
