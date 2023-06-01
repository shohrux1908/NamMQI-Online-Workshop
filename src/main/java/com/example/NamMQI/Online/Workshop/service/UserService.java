package com.example.NamMQI.Online.Workshop.service;


import com.example.NamMQI.Online.Workshop.dto.MessageDto;
import com.example.NamMQI.Online.Workshop.dto.UserDto;
import com.example.NamMQI.Online.Workshop.model.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    void saveMessage(MessageDto messageDto);

    User findByUsername(String username);


    User findBYUsernameAndPassword(String username, String password);

    List<UserDto> findAllUsers();

    List<MessageDto> findAllMessage();
}
