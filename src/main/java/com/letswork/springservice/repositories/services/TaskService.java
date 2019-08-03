package com.letswork.springservice.repositories.services;

import com.letswork.springservice.generalexception.BadRequestException;
import com.letswork.springservice.generalexception.NoContentException;
import com.letswork.springservice.repositories.CRUD.TaskCrud;
import com.letswork.springservice.repositories.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    @Autowired
    GroupService groupService;

    public TaskEntity findTaskById(Long taskId) throws BadRequestException {
        Optional<TaskEntity> taskEntity = taskCrud.findById(taskId);
        if (!taskEntity.isPresent()) throw new BadRequestException("task id not found");
        return taskEntity.get();
    }

    public List<TaskEntity> findAll() throws BadRequestException {
        List<TaskEntity> allTask = (List<TaskEntity>) taskCrud.findAll();
        if (allTask.isEmpty()) throw new BadRequestException("task id not found");
        return allTask;
    }

    public List<TaskEntity> findAllByProjectId(Long id) throws NoContentException {
        List<TaskEntity> allTask = taskCrud.findAllByGroup_Project_Id(id);
        if (allTask.isEmpty()) throw new NoContentException("task id not found");
        return allTask;
    }

    public void assignMember(Long taskId, Long assignerId, Long userId) throws BadRequestException {
        Optional<TaskEntity> task = taskCrud.findById(taskId);
        if (!task.isPresent()) throw new BadRequestException("task id not found");
        TaskEntity taskEntity = task.get();
        UserEntity assigner = userService.findUserById(assignerId);
        UserEntity user = userService.findUserById(userId);
        List<RoleEntity> roleEntities = projectService.findProjectMember(taskEntity.getGroup().getProject().getId());
        List<UserEntity> member = new ArrayList<>();
        for (RoleEntity e : roleEntities) {
            member.add(e.getUser());
        }
        if (!member.contains(assigner)) throw new BadRequestException("assigner is not member");
        if (!member.contains(user)) throw new BadRequestException("user is not member");
        taskEntity.addAssignment(assignerId, user);
        taskCrud.save(taskEntity);
    }

    public List<AssignmentEntity> getAssignment(Long id){
        TaskEntity task= findTaskById(id);
        return task.getAssignment();
    }

    public void changeGroup(Long id, Long targetGroupId) throws BadRequestException {
        TaskEntity task = findTaskById(id);
        GroupEntity targetGroup = groupService.findGroupById(targetGroupId);
        task.setGroup(targetGroup);
        taskCrud.save(task);
    }

    public void updateTask(Long id, String title, String description, Long estimateTime, Long spendTime) {
        TaskEntity task = findTaskById(id);
        boolean isModify = false;
        if (title != null && task.getTitle().compareTo(title) != 0) {
            task.setTitle(title);
            isModify = true;
        }
        if (description != null && task.getDescription().compareTo(description) != 0) {
            task.setDescription(description);
            isModify = true;
        }
        if (estimateTime != null && task.getEstimateTime().compareTo(estimateTime) != 0) {
            task.setEstimateTime(estimateTime);
            isModify = true;
        }
        if (spendTime != null && task.getSpendTime().compareTo(spendTime) != 0) {
            task.setSpendTime(spendTime);
            isModify = true;
        }
        if (isModify) taskCrud.save(task);
    }

    public void deleteTask(Long id){
        TaskEntity taskEntity = findTaskById(id);
        taskCrud.delete(taskEntity);
    }
}
