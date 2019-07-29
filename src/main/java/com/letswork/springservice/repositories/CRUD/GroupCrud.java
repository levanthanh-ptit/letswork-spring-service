package com.letswork.springservice.repositories.CRUD;

import com.letswork.springservice.repositories.entities.GroupEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GroupCrud extends CrudRepository<GroupEntity, Long> {
    List<GroupEntity> findAllByProjectId(Long projectId);
}
