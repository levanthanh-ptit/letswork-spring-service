package com.letswork.springservice.user.model;

import com.letswork.springservice.repositories.entities.UserEntity;
import com.letswork.springservice.utils.ObjectUtil;

import java.util.List;

public class UserInfoModel {

    private Long id;

    private String userName;

    private String firstName;

    private String lastName;

    private String email;

    private String company;

    private String bio;

    private String country;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public UserInfoModel(){ }

    public UserInfoModel(UserEntity entity){
        this.id = entity.getId();
        this.userName = entity.getUserName();
        this.firstName = entity.getFirstName();
        this.lastName = entity.getLastName();
        this.email = entity.getEmail();
        this.company = entity.getCompany();
        this.bio = entity.getBio();
        this.country = entity.getCountry();
    }

    public static List<UserInfoModel> covertFromUserEntity(List<UserEntity> userEntities){
        ObjectUtil<UserEntity, UserInfoModel> converter = UserInfoModel::new;
        return converter.convertToList(userEntities);
    }
}
