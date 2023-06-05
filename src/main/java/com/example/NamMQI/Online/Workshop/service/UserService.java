package com.example.NamMQI.Online.Workshop.service;


import com.example.NamMQI.Online.Workshop.dto.MessageDto;
import com.example.NamMQI.Online.Workshop.dto.UserDto;
import com.example.NamMQI.Online.Workshop.model.Message;
import com.example.NamMQI.Online.Workshop.model.User;
import com.example.NamMQI.Online.Workshop.service.impl.UserNotFoundException;
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
      User getUserById(Long id);

     User get(Long id) throws UserNotFoundException;

    void save(User user);
}
