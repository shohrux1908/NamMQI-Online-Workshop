package com.example.NamMQI.Online.Workshop.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "complaint")
public class Message {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @NotBlank
        @Column(nullable = false)
        private String theme;

        @NotBlank
        @Column(length = 1000)
        private String complaintText;

        @NotBlank
        @Column(nullable = false)
        private String position;


        @Column(name = "user_id")
        private Long user;


}
