package com.letswork.springservice.repositories.services;

import com.letswork.springservice.generalexception.NoContentException;
import com.letswork.springservice.repositories.CRUD.TaskCrud;
import com.letswork.springservice.repositories.entities.TaskEntity;
import com.letswork.springservice.repositories.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    public TaskCrud taskCrud;
    @Autowired
    UserService userService;
    @Autowired
    ProjectService projectService;

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

    public void assignMember(Long taskId, Long assignerId, Long userId) throws NoContentException {
        Optional<TaskEntity> task = taskCrud.findById(taskId);
        if (!task.isPresent()) throw new NoContentException("task id not found");
        TaskEntity taskEntity = task.get();
        UserEntity assigner = userService.findUserById(assignerId);
        UserEntity user = userService.findUserById(userId);
        List<UserEntity> members = projectService.findProjectMember(taskEntity.getProject().getId());
        if (!members.contains(assigner)) throw new NoContentException("assigner is not member");
        if (!members.contains(user)) throw new NoContentException("user is not member");
        taskEntity.addAssignment(assignerId, user);
        taskCrud.save(taskEntity);
    }
}
