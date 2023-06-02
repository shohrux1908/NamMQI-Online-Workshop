package com.example.NamMQI.Online.Workshop.dto;


import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminDto {
    private Long id;
    @NotEmpty
    private String login;
    @NotEmpty
    private String password;
}