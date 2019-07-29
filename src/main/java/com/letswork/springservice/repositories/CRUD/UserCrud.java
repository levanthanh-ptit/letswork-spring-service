package com.letswork.springservice.repositories.CRUD;

import com.letswork.springservice.repositories.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
public interface UserCrud extends CrudRepository<UserEntity, Long> {

    Optional<List<UserEntity>> findAllByCompany(String company);

    Optional<List<UserEntity>> findAllByFirstNameContainingOrLastNameContainingOrUserNameContaining(String keyWord1, String keyWord2, String keyword3);

    boolean existsByUserName(String userName);

    Optional<UserEntity> findUserEntityByUserName(String userName);
}
