package com.example.NamMQI.Online.Workshop.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {
    private Long id;
    @NotEmpty
    private String theme;

    @NotEmpty
    private String complaintText;

    @NotEmpty
    private String position;

}
