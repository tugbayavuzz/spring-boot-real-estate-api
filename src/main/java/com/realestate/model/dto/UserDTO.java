package com.realestate.model.dto;


import com.realestate.model.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;
    private UserType userType;
    private String firstName;
    private String lastName;
    private String companyName;
    private String email;
    private String phone;
    private String password;
    private String address;

}
