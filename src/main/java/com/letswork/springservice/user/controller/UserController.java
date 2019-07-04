package com.letswork.springservice.user.controller;

import com.letswork.springservice.genneralexception.model.ExceptionModel;
import com.letswork.springservice.repositories.entities.UserEntity;
import com.letswork.springservice.genneralexception.NoContentException;
import com.letswork.springservice.repositories.services.UserService;
import com.letswork.springservice.user.model.UserInfoModel;
import com.letswork.springservice.user.model.UserSearchModel;
import com.letswork.springservice.utils.GFG;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ExceptionHandler(NoContentException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    private ExceptionModel handleUserException(NoContentException e){
        return new ExceptionModel(e.getMessage());
    }

    @GetMapping(path = "/all", produces = "application/json")
    private Iterable<UserEntity> getAllUser() {
        return userService.findAll();
    }

    @GetMapping(path = "/info", produces = "application/json")
    private UserInfoModel getUserInfoById(@RequestParam Long id) {
        return new UserInfoModel(userService.findUserById(id));
    }

    @GetMapping(path = "/filter-by-company")
    private List<UserInfoModel> getUserInfoByCompany(@RequestParam String company){
        return UserInfoModel.covertFromUserEntity(userService.findAllByCompany(company));
    }

    @GetMapping(path = "/seeding-data")
    private void seedingUser() {
        for (int i = 0; i < 5; i++) {
            UserEntity userEntity =
                    new UserEntity(GFG.randomCmnd(),
                            GFG.randomFirstName(),
                            GFG.randomLastName(),
                            GFG.randomEmail(),
                            "Unimployeed",
                            "Need job",
                            "Viet Nam"
                    );
            userService.userCrud.save(userEntity);
        }
    }

    @GetMapping(path = "/search")
    private List<UserSearchModel> searchUsersByName(@RequestParam(name = "key_word") String keyWord){
        return UserSearchModel.covertFromUserEntity(userService.searchUser(keyWord));
    }
}
