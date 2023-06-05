package com.example.NamMQI.Online.Workshop.model;

import javax.persistence.*;

@Entity()
@Table(name = "users_roles")
public class UserRoles {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
