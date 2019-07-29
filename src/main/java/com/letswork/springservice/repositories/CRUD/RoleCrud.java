package com.letswork.springservice.repositories.CRUD;

import com.letswork.springservice.repositories.entities.RoleEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RoleCrud extends CrudRepository<RoleEntity, Long> {
    List<RoleEntity> findAllByUser_Id(Long userId);
}
