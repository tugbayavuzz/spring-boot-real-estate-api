package com.realestate.model.entity;

import com.realestate.model.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Users")
public class UserEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private UserType userType;
    private String firstName;
    private String lastName;
    private String companyName;

    @Column(unique = true)
    private String email;
    private String phone;
    private String address;
    private String password;

}
