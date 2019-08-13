package com.letswork.springservice.auth.model;

import lombok.Data;

@Data
public class SignUpModel {
    String username;

    String email;

    String password;

    String firstName;

    String lastName;
}
