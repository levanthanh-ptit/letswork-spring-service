package com.letswork.springservice.user.controller;

import com.letswork.springservice.genneralexception.model.ExceptionModel;
import com.letswork.springservice.repositories.CRUD.UserCrud;
import com.letswork.springservice.repositories.entities.UserEntity;
import com.letswork.springservice.genneralexception.NoContentException;
import com.letswork.springservice.user.model.UserInfoModel;
import com.letswork.springservice.utils.GFG;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/user")
public class UserController {
    @Autowired
    private UserCrud userCrud;

    @ExceptionHandler(NoContentException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    private ExceptionModel handleUserException(NoContentException e){
        return new ExceptionModel(e.getMessage());
    }

    @GetMapping(path = "/all", produces = "application/json")
    private Iterable<UserEntity> getAllUser() {
        Iterable<UserEntity> user = userCrud.findAll();
        return user;
    }

    @GetMapping(path = "/info", produces = "application/json")
    private UserInfoModel getUserInfoById(@RequestParam Long id, @RequestHeader String Authorization) {
        Optional<UserEntity> user = userCrud.findById(id);
        return new UserInfoModel(user.get());
    }

    @GetMapping(path = "/filter-by-company")
    private List<UserInfoModel> getUserInfoByCompany(@RequestParam String company){
        Optional<List<UserEntity>> users = userCrud.findAllByCompany(company);
        if(!users.isPresent()) {
            throw new NoContentException("not content");
        }
        List<UserEntity> userEntities = users.get();
        List<UserInfoModel> userInfoModels = new ArrayList<>();
        for (UserEntity e: userEntities) {
            userInfoModels.add(new UserInfoModel(e));
        }
        return userInfoModels;
    }

    @GetMapping(path = "/seeding-data")
    private void seedingUser() {
        for (int i = 0; i < 50; i++) {
            UserEntity userEntity =
                    new UserEntity(GFG.randomCmnd(),
                            GFG.randomFirstName(),
                            GFG.randomLastName(),
                            GFG.randomEmail(),
                            "Unimployeed",
                            "Need job",
                            "Viet Nam"
                    );
            userCrud.save(userEntity);
        }
    }


}
