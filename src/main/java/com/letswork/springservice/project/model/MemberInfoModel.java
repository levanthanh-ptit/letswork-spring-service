package com.letswork.springservice.project.model;

import com.letswork.springservice.repositories.entities.UserEntity;

public class MemberInfoModel {
    Long id;

    String fullName;

    String ownership;

    public MemberInfoModel(Long id, String fullName, String ownership) {
        this.id = id;
        this.fullName = fullName;
        this.ownership = ownership;
    }

    public MemberInfoModel(UserEntity user, String ownership) {
        this.id = user.getId();
        this.fullName = user.getFirstName() + " " + user.getLastName();
        this.ownership = ownership;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getOwnership() {
        return ownership;
    }

    public void setOwnership(String ownership) {
        this.ownership = ownership;
    }
}
