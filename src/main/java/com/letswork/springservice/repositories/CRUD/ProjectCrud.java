package com.letswork.springservice.repositories.CRUD;

import com.letswork.springservice.repositories.entities.ProjectEntity;
import org.springframework.data.repository.CrudRepository;

public interface ProjectCrud extends CrudRepository<ProjectEntity, Long> {
}
