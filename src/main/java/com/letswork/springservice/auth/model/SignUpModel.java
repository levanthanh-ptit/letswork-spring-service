package com.letswork.springservice.auth.model;

import lombok.Data;

@Data
public class SignUpModel {
    String username;

    String password;

    String firstName;

    String lastName;
}
