package com.example.NamMQI.Online.Workshop.controller;

import com.example.NamMQI.Online.Workshop.model.Admin;
import com.example.NamMQI.Online.Workshop.service.AdminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller

public class AdminController {

    @Autowired
    private AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }


    @GetMapping("/adminlogin")
    public String adminloginForm() {
        return "adminlogin";
    }




    @PostMapping("/adminlogin")
    public String adminlogin(@ModelAttribute() Admin admin, Model model) {
        Admin authenticated =adminService.findByLoginAndPassword(admin.getLogin(),admin.getPassword());
        if (authenticated!=null){
            model.addAttribute("adminlogin",authenticated.getLogin());
            return "redirect:/admin";
        }
        else {
            return "index";
        }
    }
}
