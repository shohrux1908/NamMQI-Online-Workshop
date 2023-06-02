package com.example.NamMQI.Online.Workshop.service.impl;

import com.example.NamMQI.Online.Workshop.dto.MessageDto;
import com.example.NamMQI.Online.Workshop.dto.UserDto;
import com.example.NamMQI.Online.Workshop.model.Admin;
import com.example.NamMQI.Online.Workshop.model.Message;
import com.example.NamMQI.Online.Workshop.model.Role;
import com.example.NamMQI.Online.Workshop.model.User;
import com.example.NamMQI.Online.Workshop.repository.AdminRepository;
import com.example.NamMQI.Online.Workshop.repository.MessageRepository;
import com.example.NamMQI.Online.Workshop.repository.RoleRepository;
import com.example.NamMQI.Online.Workshop.repository.UserRepository;
import com.example.NamMQI.Online.Workshop.service.UserService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
 public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    private MessageRepository messageRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,

                           RoleRepository roleRepository,
                           MessageRepository messageRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.messageRepository=messageRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public  void saveUser(UserDto userDto) {
        User user = new User();
        user.setDepartment(userDto.getDepartment());
        user.setPosition(userDto.getPosition());
        user.setName(userDto.getFirstName() + " " + userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setUsername(userDto.getUsername());


        //encrypt the password once we integrate spring security
//        user.setPassword(userDto.getPassword());
         user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Role role = roleRepository.findByName("ROLE_ADMIN");
//        Role role1=roleRepository.findByName("ROLR_USER");
        if(role == null){
            role = checkRoleExist();
        }
//        if (role1==null){
//            role1=checkRoleExist();
//        }

        user.setRoles(Arrays.asList(role));
//        user.setRoles(Arrays.asList(role1));
        User savedUser = userRepository.save(user);
    }



    @Override
    public void saveMessage(MessageDto messageDto) {
        Message message=new Message();
        message.setTheme(messageDto.getTheme());
        message.setComplaintText(messageDto.getComplaintText());
        message.setPosition(messageDto.getPosition());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        User username = userRepository.findByUsername(name);

        message.setUser(username.getId());

        messageRepository.save(message);
    }



    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }




    @Override
    public User findBYUsernameAndPassword(String username, String password) {
      return userRepository.findByUsernameAndPassword(username,password);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        System.out.println(users.toString());
        return users.stream().map((user) -> convertEntityToDto(user))
                .collect(Collectors.toList());
    }

    @Override
    public List<MessageDto> findAllMessage() {
        List<Message> messages=messageRepository.findAll();
        return  messages.stream().map((message -> converEntytyMessage(message)))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(long id) {
        this.userRepository.deleteById(id);
    }

    @Override
    public void deleteByUserRole(Long user_id) {
        userRepository.findById(user_id);
    }


    private UserDto convertEntityToDto(User user){
        UserDto userDto = new UserDto();

        String[] name = user.getName().split(" ");
        userDto.setFirstName(name[0]);
        userDto.setLastName(name[0]);
        userDto.setId(user.getId());
        userDto.setDepartment(user.getDepartment());
        userDto.setPosition(user.getPosition());
        userDto.setEmail(user.getEmail());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setUsername(user.getUsername());

        return userDto;
    }
    private MessageDto converEntytyMessage(Message message){
        MessageDto messageDto=new MessageDto();
        messageDto.setTheme(message.getTheme());
        messageDto.setComplaintText(message.getComplaintText());
        messageDto.setPosition(message.getPosition());
        return messageDto;
    }

    private Role checkRoleExist() {
        Role role = new Role();
        role.setName("ROLE_ADMIN");
//        role.setName("ROLE_USER");
        return roleRepository.save(role);
    }

    public User getUser(String username){
        return userRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepository.findByUsername(username);
        if (user==null) {
            throw new UsernameNotFoundException("Invalid username or password");
        }

        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
