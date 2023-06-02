package com.example.NamMQI.Online.Workshop.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import lombok.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name="users_table")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User
{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable=false)
    private String name;

    @Column(nullable = false)
    private String department;

    @Column(nullable = false)
    private String position;

    @Email
    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false)
    private String phoneNumber;



    @Column(nullable=false, unique=true)
    private String username;

    @Column(nullable=false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinTable(
            name="users_roles",
            joinColumns={@JoinColumn(name="USER_ID", referencedColumnName="ID")},
            inverseJoinColumns={@JoinColumn(name="ROLE_ID", referencedColumnName="ID")})
    private Collection<Role> roles = new ArrayList<>();

    public User(String name, String department, String position, String email, String phoneNumber, String username, String password, List<Role> roles) {
        this.name = name;
        this.department = department;
        this.position = position;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public User(String name, String username, String password, List<Role> roles) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }


//    @Override
//    public String toString() {
//        return "User{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", department='" + department + '\'' +
//                ", position='" + position + '\'' +
//                ", email='" + email + '\'' +
//                ", phoneNumber='" + phoneNumber + '\'' +
//                ", username='" + username + '\'' +
//                ", password='" + password + '\'' +
//                ", roles=" + roles +
//                '}';
//    }
}
