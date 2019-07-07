package com.letswork.springservice.user.model;

import com.letswork.springservice.repositories.entities.UserEntity;
import com.letswork.springservice.utils.ObjectUtil;
import lombok.Data;

import java.util.List;

@Data
public class UserSearchModel {

    private String userName;

    private String fullName;

    public UserSearchModel() {
    }

    public UserSearchModel(UserEntity entity) {
        this.userName = entity.getUserName();
        this.fullName = entity.getFirstName() + " " + entity.getLastName();
    }

    public static List<UserSearchModel> covertFromUserEntity(List<UserEntity> userEntities) {
        ObjectUtil<UserEntity, UserSearchModel> converter = UserSearchModel::new;
        return converter.convertToList(userEntities);
    }
}
