package com.letswork.springservice.project.model;

import com.letswork.springservice.repositories.entities.UserEntity;
import lombok.Data;

@Data
public class MemberModel {
    Long id;

    String fullName;

    String ownership;

    public MemberModel(Long id, String fullName, String ownership) {
        this.id = id;
        this.fullName = fullName;
        this.ownership = ownership;
    }

    public MemberModel(UserEntity user, String ownership) {
        this.id = user.getId();
        this.fullName = user.getFirstName() + " " + user.getLastName();
        this.ownership = ownership;
    }
}
