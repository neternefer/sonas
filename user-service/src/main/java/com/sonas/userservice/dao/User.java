package com.sonas.userservice.dao;

import com.sonas.userservice.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long userId;

    private String email;

    private String password;

    private String name;

    @Column(name = "last_name")
    private String lastName;

    private String username;

    @Column(name = "user_type")
    @Enumerated
    private UserType userType;

    @Column(name = "contact_id")
    private long contactId;

    public User(String email,
                String password,
                UserType userType,
                long contactId) {
        this.email = email;
        this.password = password;
        this.userType = userType;
        this.contactId = contactId;
    }

    public User(String email,
                String password,
                String name,
                String lastName,
                String username,
                UserType userType,
                long contactId) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.lastName = lastName;
        this.username = username;
        this.userType = userType;
        this.contactId = contactId;
    }
}
