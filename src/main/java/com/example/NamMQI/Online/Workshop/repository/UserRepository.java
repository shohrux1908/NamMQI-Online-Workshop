package com.example.NamMQI.Online.Workshop.repository;
import com.example.NamMQI.Online.Workshop.model.Admin;
import com.example.NamMQI.Online.Workshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String usename);
    User findByUsernameAndPassword(String username, String password);
    Admin findAdminByUsernameAndPassword(String username, String password);

//    void deleteByIds(long id);

    ;

}
