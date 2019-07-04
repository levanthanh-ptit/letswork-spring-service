package com.letswork.springservice.user.model;

import com.letswork.springservice.repositories.entities.UserEntity;
import com.letswork.springservice.utils.ObjectUtil;

import java.util.List;

public class UserSearchModel {

    private String userName;

    private String fullName;

    public UserSearchModel() {
    }

    public UserSearchModel(UserEntity entity) {
        this.userName = entity.getUserName();
        this.fullName = entity.getFirstName() + " " + entity.getLastName();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public static List<UserSearchModel> covertFromUserEntity(List<UserEntity> userEntities) {
        ObjectUtil<UserEntity, UserSearchModel> converter = UserSearchModel::new;
        return converter.convertToList(userEntities);
    }
}
