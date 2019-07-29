package com.letswork.springservice.user.controller;

import com.letswork.springservice.project.model.ProjectModel;
import com.letswork.springservice.repositories.entities.UserEntity;
import com.letswork.springservice.repositories.services.RoleService;
import com.letswork.springservice.repositories.services.UserService;
import com.letswork.springservice.user.model.UserModel;
import com.letswork.springservice.user.model.UserSearchModel;
import com.letswork.springservice.utils.GFG;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @GetMapping(path = "/all", produces = "application/json")
    private Iterable<UserModel> getAllUser() {
        return UserModel.covertFromUserEntities(userService.findAll());
    }

    @GetMapping(path = "/info", produces = "application/json")
    private UserModel getUserInfoById(@RequestParam Long id) throws BindException {
        return new UserModel(userService.findUserById(id));
    }

    @GetMapping(path = "/{id}/projects")
    private List<ProjectModel> getAllProjectsByUserIs(@PathVariable(name = "id") Long id) {
        return ProjectModel.toProjectModelList(roleService.findAllProjectByUserId(id));
    }

    @GetMapping(path = "/filter-by-company")
    private List<UserModel> getUserInfoByCompany(@RequestParam String company){
        return UserModel.covertFromUserEntities(userService.findAllByCompany(company));
    }

    @GetMapping(path = "/seeding-data")
    private void seedingUser() {
        for (int i = 0; i < 5; i++) {
            UserEntity userEntity =
                    new UserEntity(GFG.randomCmnd(),
                            "123456Aa",
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
