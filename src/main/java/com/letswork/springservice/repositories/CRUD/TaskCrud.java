package com.letswork.springservice.repositories.CRUD;

import com.letswork.springservice.repositories.entities.TaskEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TaskCrud extends CrudRepository<TaskEntity, Long> {
    public List<TaskEntity> findAllByProjectId(Long projectId);
}
