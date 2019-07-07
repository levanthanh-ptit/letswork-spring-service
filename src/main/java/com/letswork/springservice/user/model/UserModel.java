package com.letswork.springservice.user.model;

import com.letswork.springservice.repositories.entities.UserEntity;
import com.letswork.springservice.utils.ObjectUtil;
import lombok.Data;

import java.util.List;

@Data
public class UserModel {

    private Long id;

    private String userName;

    private String firstName;

    private String lastName;

    private String email;

    private String company;

    private String bio;

    private String country;

    public UserModel(){ }

    public UserModel(UserEntity entity){
        this.id = entity.getId();
        this.userName = entity.getUserName();
        this.firstName = entity.getFirstName();
        this.lastName = entity.getLastName();
        this.email = entity.getEmail();
        this.company = entity.getCompany();
        this.bio = entity.getBio();
        this.country = entity.getCountry();
    }

    public static List<UserModel> covertFromUserEntities(List<UserEntity> userEntities){
        ObjectUtil<UserEntity, UserModel> converter = UserModel::new;
        return converter.convertToList(userEntities);
    }
}
