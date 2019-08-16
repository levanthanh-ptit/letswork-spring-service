package com.letswork.springservice.repositories.services;

import com.letswork.springservice.generalexception.BadRequestException;
import com.letswork.springservice.generalexception.NoContentException;
import com.letswork.springservice.repositories.CRUD.GroupCrud;
import com.letswork.springservice.repositories.entities.GroupEntity;
import com.letswork.springservice.repositories.entities.TaskEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupService {

    @Autowired
    public GroupCrud groupCrud;
    @Autowired
    TaskService taskService;

    public GroupEntity findGroupById(Long taskId) throws BadRequestException {
        Optional<GroupEntity> group = groupCrud.findById(taskId);
        if (!group.isPresent()) throw new BadRequestException("group id not found");
        return group.get();
    }

    public List<GroupEntity> findAll() throws BadRequestException {
        List<GroupEntity> allTask = (List<GroupEntity>) groupCrud.findAll();
        if (allTask.isEmpty()) throw new BadRequestException("group id not found");
        return allTask;
    }

    public List<GroupEntity> findAllByProjectId(Long id) throws NoContentException {
        List<GroupEntity> allGroup = groupCrud.findAllByProjectId(id);
        if (allGroup.isEmpty()) throw new NoContentException("doesn't have any group");
        return allGroup;
    }

    public TaskEntity addTask(String title, String description, Long taskGroupId) {
        GroupEntity groupEntity = this.findGroupById(taskGroupId);
        TaskEntity taskEntity = new TaskEntity(title, description);
        taskEntity.setGroup(groupEntity);
        return taskService.taskCrud.save(taskEntity);
    }

    public void updateGroup(Long id, String title) {
        GroupEntity group = findGroupById(id);
        if (title != null && group.getTitle().compareTo(title) != 0) {
            group.setTitle(title);
            groupCrud.save(group);
        }
    }

    public void deleteGroup(Long id) {
        GroupEntity groupEntity = findGroupById(id);
        groupCrud.delete(groupEntity);
    }
}
