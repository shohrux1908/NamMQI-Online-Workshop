package com.example.NamMQI.Online.Workshop.service.impl;

import com.example.NamMQI.Online.Workshop.model.Admin;
import com.example.NamMQI.Online.Workshop.repository.AdminRepository;
import com.example.NamMQI.Online.Workshop.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminRepository adminRepository;

    @Override
    public Admin findByLoginAndPassword(String login, String password) {
        return adminRepository.findByLoginAndPassword(login,password);
    }
}
