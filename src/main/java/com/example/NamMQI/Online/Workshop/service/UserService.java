package com.example.NamMQI.Online.Workshop.service;


import com.example.NamMQI.Online.Workshop.dto.MessageDto;
import com.example.NamMQI.Online.Workshop.dto.UserDto;
import com.example.NamMQI.Online.Workshop.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    void saveUser(UserDto userDto);

    void saveMessage(MessageDto messageDto);

    User findByUsername(String username);


    User findBYUsernameAndPassword(String username, String password);

    List<UserDto> findAllUsers();

    List<MessageDto> findAllMessage();
    void deleteById(long id);

    void deleteByUserRole(Long user_id);

}
