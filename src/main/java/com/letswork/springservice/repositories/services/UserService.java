package com.letswork.springservice.repositories.services;

import com.letswork.springservice.generalexception.BadRequestException;
import com.letswork.springservice.repositories.CRUD.UserCrud;
import com.letswork.springservice.repositories.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    public UserCrud userCrud;

    public UserEntity addNewUser(String userName, String email, String password, String firstName, String lastName) {
        if (checkUserExists(userName)) throw new BadRequestException("UserName is already taken");
        UserEntity userEntity = new UserEntity(userName, email, password, firstName, lastName);
        return userCrud.save(userEntity);
    }

    public List<UserEntity> searchUser(String keyWord) {
        Optional<List<UserEntity>> userEntities =
                userCrud.findAllByFirstNameContainingOrLastNameContainingOrUserNameContaining(keyWord, keyWord, keyWord);
        if (!userEntities.isPresent()) throw new BadRequestException("No user found");
        return userEntities.get();
    }

    public UserEntity findUserById(Long userId) {
        Optional<UserEntity> userEntity = userCrud.findById(userId);
        if (!userEntity.isPresent()) throw new BadRequestException("No user found");
        return userEntity.get();
    }

    public UserEntity findUserByUsername(String userName) {
        Optional<UserEntity> userEntity = userCrud.findUserEntityByUserName(userName);
        if (!userEntity.isPresent()) throw new BadRequestException("No user found for username '" + userName + "'");
        return userEntity.get();
    }

    public List<UserEntity> findAll() {
        List<UserEntity> allUser = (List<UserEntity>) userCrud.findAll();
        if (allUser.isEmpty()) throw new BadRequestException("No user found");
        return allUser;
    }

    public List<UserEntity> findAllByCompany(String company) {
        Optional<List<UserEntity>> userEntities = userCrud.findAllByCompany(company);
        if (!userEntities.isPresent()) {
            throw new BadRequestException("No user found");
        }
        return userEntities.get();
    }

    public void updateUser(Long id, String firstName, String lastName, String email,
                           String company, String bio, String country) {
        UserEntity user = findUserById(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setCompany(company);
        user.setBio(bio);
        user.setCountry(country);
        userCrud.save(user);
    }

    public boolean checkUserExists(String userName) {
        return userCrud.existsByUserName(userName);
    }
}
