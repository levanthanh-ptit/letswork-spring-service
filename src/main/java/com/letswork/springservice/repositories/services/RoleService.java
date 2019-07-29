package com.letswork.springservice.repositories.services;

import com.letswork.springservice.repositories.CRUD.RoleCrud;
import com.letswork.springservice.repositories.entities.ProjectEntity;
import com.letswork.springservice.repositories.entities.RoleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService {

    @Autowired
    RoleCrud roleCrud;

    public List<ProjectEntity> findAllProjectByUserId(Long userId){
        List<RoleEntity> roleEntities = roleCrud.findAllByUser_Id(userId);
        List<ProjectEntity> projectEntities = new ArrayList<>();
        for (RoleEntity e: roleEntities ) {
            projectEntities.add(e.getProject());
        }
        return projectEntities;
    }
}
