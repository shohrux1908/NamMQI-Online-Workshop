package com.example.NamMQI.Online.Workshop.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;


   @NotEmpty
    private String department;

    @NotEmpty
    private String position;

    @Email
    @NotEmpty(message = "Email bosh bo'lmasloigi kerak")
    private String email;

    @NotEmpty
    private String phoneNumber;

    @NotEmpty(message = "Login bo'sh bo'lmasligi kerak")
    private String username;
    @NotEmpty(message = "Password should not be empty")
    private String password;



}
