package com.example.NamMQI.Online.Workshop.dto;

import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class MessageDto {
    private Long id;
    @NotEmpty
    private String theme;

    @NotEmpty
    private String complaintText;

    @NotEmpty
    private String position;

    public MessageDto(Long id, String theme, String complaintText, String position) {
        this.id = id;
        this.theme = theme;
        this.complaintText = complaintText;
        this.position = position;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getComplaintText() {
        return complaintText;
    }

    public void setComplaintText(String complaintText) {
        this.complaintText = complaintText;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

//    @Override
//    public String toString() {
//        return "MessageDto{" +
//                "id=" + id +
//                ", theme='" + theme + '\'' +
//                ", complaintText='" + complaintText + '\'' +
//                ", position='" + position + '\'' +
//                '}';
//    }

    public MessageDto() {
    }
}
