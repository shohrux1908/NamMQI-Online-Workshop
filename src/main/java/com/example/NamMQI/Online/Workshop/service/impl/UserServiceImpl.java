package com.example.NamMQI.Online.Workshop.service.impl;

import com.example.NamMQI.Online.Workshop.dto.MessageDto;
import com.example.NamMQI.Online.Workshop.dto.UserDto;
import com.example.NamMQI.Online.Workshop.model.Message;
import com.example.NamMQI.Online.Workshop.model.Role;
import com.example.NamMQI.Online.Workshop.model.User;
import com.example.NamMQI.Online.Workshop.repository.MessageRepository;
import com.example.NamMQI.Online.Workshop.repository.RoleRepository;
import com.example.NamMQI.Online.Workshop.repository.UserRepository;
import com.example.NamMQI.Online.Workshop.repository.UserRoleRepository;
import com.example.NamMQI.Online.Workshop.service.UserService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
 public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;
    private MessageRepository messageRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,


                           UserRoleRepository userRoleRepository,
                           RoleRepository roleRepository,
                           MessageRepository messageRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userRoleRepository=userRoleRepository;
        this.roleRepository = roleRepository;
        this.messageRepository = messageRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public  void saveUser(UserDto userDto) {
        User user = new User();
        user.setDepartment(userDto.getDepartment());
        user.setPosition(userDto.getPosition());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Role role = roleRepository.findByName("ROLE_ADMIN");
        if(role == null){
            role = checkRoleExist();
        }

        user.setRoles(Arrays.asList(role));

        User savedUser = userRepository.save(user);
    }



    @Override
    public void saveMessage(MessageDto messageDto) {

        Message message =new Message();
        message.setId(messageDto.getId());
        message.setTheme(messageDto.getTheme());
        message.setComplaint_text(messageDto.getComplaintText());
        message.setPosition(messageDto.getPosition());
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
        return users.stream().map((user) -> convertEntityToDto(user))
                .collect(Collectors.toList());
    }

    @Override
    public List<MessageDto> findAllMessage() {
        List<Message> messages = messageRepository.findAll();
        return  messages.stream().map((message -> converEntytyMessage(message)))
                .collect(Collectors.toList());
    }
    @Transactional
    @Override
    public void deleteById(long id) {
        User user = userRepository.findById(id).orElse(null);
            if (user != null) {
                System.out.println(user.getRoles());
                userRepository.save(user);
            }
            userRoleRepository.deleteById(id);



    }

    @Override
    public void deleteByUserRole(Long user_id) {
        userRepository.findById(user_id);
    }

    @Override
    public User getUserById(Long id) {
        this.userRepository.getUserById(id);
        return null;
    }

    @Override
    public User get(Long id) throws UserNotFoundException {
        Optional<User> result =userRepository.findById(id);
        if (result.isPresent()){
            return result.get();
        }
        throw new UserNotFoundException("Could not find any users with ID"+id);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }


    private UserDto convertEntityToDto(User user){
        UserDto userDto = new UserDto();
//        String[] name = user.getName().split(" ");
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
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
        messageDto.setId(message.getId());
        messageDto.setTheme(message.getTheme());
        messageDto.setComplaintText(message.getComplaint_text());
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
