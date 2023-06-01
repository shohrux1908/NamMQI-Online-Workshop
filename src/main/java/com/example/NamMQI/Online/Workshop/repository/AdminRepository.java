package com.example.NamMQI.Online.Workshop.repository;

import com.example.NamMQI.Online.Workshop.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface AdminRepository extends JpaRepository<Admin,Long> {
    Admin findByLoginAndPassword(String login, String password);
}
