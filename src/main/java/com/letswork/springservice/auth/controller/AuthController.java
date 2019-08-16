package com.letswork.springservice.auth.controller;

import com.letswork.springservice.auth.JwtTokenProvider;
import com.letswork.springservice.auth.model.LoginModel;
import com.letswork.springservice.auth.model.TokenModel;
import com.letswork.springservice.generalexception.AuthenticationException;
import com.letswork.springservice.generalexception.BadRequestException;
import com.letswork.springservice.repositories.entities.UserEntity;
import com.letswork.springservice.repositories.services.UserService;
import com.letswork.springservice.auth.model.SignUpModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping(path = "/auth")
public class AuthController {
    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    UserService userService;

    @PostMapping(path = "/sign_up")
    public TokenModel signUpByUsername(@RequestBody SignUpModel signUpModel) {
        UserEntity user = userService.addNewUser(
                signUpModel.getUsername(),
                signUpModel.getEmail(),
                signUpModel.getPassword(),
                signUpModel.getFirstName(),
                signUpModel.getLastName()
        );

        return new TokenModel(tokenProvider.generateToken(user), user.getId());
    }

    @PostMapping(path = "/login")
    public TokenModel login(@RequestBody LoginModel loginModel){
        UserEntity user = userService.findUserByUsername(loginModel.getUserName());
        if(user.getHashPassword().compareTo(loginModel.getPassword())==0) {
            return new TokenModel(tokenProvider.generateToken(user), user.getId());
        }
        else throw new BadRequestException("login failed");
    }
}
