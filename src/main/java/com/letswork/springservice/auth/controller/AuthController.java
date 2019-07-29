package com.letswork.springservice.auth.controller;

import com.letswork.springservice.auth.model.LoginModel;
import com.letswork.springservice.auth.model.TokenModel;
import com.letswork.springservice.generalexception.BadRequestException;
import com.letswork.springservice.repositories.entities.UserEntity;
import com.letswork.springservice.repositories.services.UserService;
import com.letswork.springservice.auth.model.SignUpModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/auth")
public class AuthController {
    @Autowired
    UserService userService;

    @PostMapping(path = "/sign_up")
    public void signUpByUsername(@RequestBody SignUpModel signUpModel) {
        userService.addNewUser(
                signUpModel.getUsername(),
                signUpModel.getPassword(),
                signUpModel.getFirstName(),
                signUpModel.getLastName()
        );
    }

    @PostMapping(path = "/login")
    public TokenModel login(@RequestBody LoginModel loginModel){
        UserEntity userEntity = userService.findUserByUsername(loginModel.getUserName());
        if(userEntity.getHashPassword().compareTo(loginModel.getPassword())==0) {
            return new TokenModel("bearer LSWP 12s1daf254d6f5sd4f56sdf46sd5");
        }
        else throw new BadRequestException("login failed");
    }
}