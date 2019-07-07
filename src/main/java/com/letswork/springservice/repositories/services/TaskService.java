package com.letswork.springservice.repositories.services;

import com.letswork.springservice.generalexception.NoContentException;
import com.letswork.springservice.repositories.CRUD.TaskCrud;
import com.letswork.springservice.repositories.entities.TaskEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    public TaskCrud taskCrud;

    public TaskEntity findTaskById(Long taskId) throws NoContentException {
        Optional<TaskEntity> taskEntity = taskCrud.findById(taskId);
        if (!taskEntity.isPresent()) throw new NoContentException("task id not found");
        return taskEntity.get();
    }

    public List<TaskEntity> findAll() throws NoContentException {
        List<TaskEntity> allTask = (List<TaskEntity>) taskCrud.findAll();
        if (allTask.isEmpty()) throw new NoContentException("task id not found");
        return allTask;
    }

    public List<TaskEntity> findAllByProjectId(Long id) throws NoContentException {
        List<TaskEntity> allTask = (List<TaskEntity>) taskCrud.findAllByProjectId(id);
        if (allTask.isEmpty()) throw new NoContentException("task id not found");
        return allTask;
    }

    public void assignMember(Long taskId, Long userId) throws NoContentException {

    }
}
