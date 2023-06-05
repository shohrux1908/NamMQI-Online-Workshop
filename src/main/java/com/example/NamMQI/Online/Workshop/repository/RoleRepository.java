package com.example.NamMQI.Online.Workshop.repository;


import com.example.NamMQI.Online.Workshop.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
//    void deleteByUserId(Long userId);

}
