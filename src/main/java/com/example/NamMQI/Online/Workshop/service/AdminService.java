package com.example.NamMQI.Online.Workshop.service;

import com.example.NamMQI.Online.Workshop.model.Admin;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;


public interface AdminService {

    Admin findByLoginAndPassword(String login, String password);

}
