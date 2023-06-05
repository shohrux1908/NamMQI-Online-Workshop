package com.example.NamMQI.Online.Workshop.repository;

import com.example.NamMQI.Online.Workshop.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<Role, Long> {
    void deleteById(Long id);
}
