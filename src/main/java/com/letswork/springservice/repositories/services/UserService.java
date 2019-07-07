package com.letswork.springservice.repositories.services;

import com.letswork.springservice.generalexception.NoContentException;
import com.letswork.springservice.repositories.CRUD.UserCrud;
import com.letswork.springservice.repositories.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindException;

import java.util.List;
import java.util.Optional;
@Service
public class UserService {

    @Autowired
    public UserCrud userCrud;

    public List<UserEntity> searchUser(String keyWord){
        Optional<List<UserEntity>> userEntities =
                userCrud.findAllByFirstNameContainingOrLastNameContainingOrUserNameContaining(keyWord, keyWord, keyWord);
        if(!userEntities.isPresent()) throw new NoContentException("No user found");
        return userEntities.get();
    }

    public UserEntity findUserById(Long userId) throws NoContentException {
        Optional<UserEntity> userEntity = userCrud.findById(userId);
        if (!userEntity.isPresent()) throw new NoContentException("No user found");
        return userEntity.get();
    }

    public List<UserEntity> findAll() throws NoContentException {
        List<UserEntity> allUser = (List<UserEntity>) userCrud.findAll();
        if (allUser.isEmpty()) throw new NoContentException("No user found");
        return allUser;
    }

    public List<UserEntity> findAllByCompany(String company){
        Optional<List<UserEntity>> userEntities = userCrud.findAllByCompany(company);
        if(!userEntities.isPresent()) {
            throw new NoContentException("No user found");
        }
        return userEntities.get();
    }
}
