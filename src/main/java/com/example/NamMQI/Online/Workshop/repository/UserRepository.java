package com.example.NamMQI.Online.Workshop.repository;
import com.example.NamMQI.Online.Workshop.model.Admin;
import com.example.NamMQI.Online.Workshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String email);
    User findByUsernameAndPassword(String username, String password);
    Admin findAdminByUsernameAndPassword(String username, String password);
}
