package com.letswork.springservice.group.controller;

import com.letswork.springservice.auth.JwtTokenProvider;
import com.letswork.springservice.generalexception.AuthenticationException;
import com.letswork.springservice.group.model.GroupModel;
import com.letswork.springservice.repositories.entities.GroupEntity;
import com.letswork.springservice.repositories.entities.TaskEntity;
import com.letswork.springservice.repositories.services.GroupService;
import com.letswork.springservice.task.model.TaskModel;
import com.letswork.springservice.group.model.GroupInfoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/group")
public class GroupController {
    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    GroupService groupService;

    @GetMapping(path = "/{id}")
    private GroupInfoModel findTaskById(@PathVariable Long id) {
        return new GroupInfoModel(groupService.findGroupById(id));
    }

    @GetMapping(path = "/all")
    private List<GroupInfoModel> findTaskGroup() {
        return GroupInfoModel.toTaskGroupInfoModelList(groupService.findAll());
    }

    @PostMapping(path = "/{id}/add-task")
    private ResponseEntity<TaskModel> addTask(@RequestBody TaskModel taskModel,
                                              @PathVariable(name = "id") Long taskGroupId) {
        TaskEntity createdTaskEntity =
                groupService.addTask(
                        taskModel.getTitle(),
                        taskModel.getDescription(),
                        taskGroupId
                );
        TaskModel createdTask = new TaskModel(createdTaskEntity);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    @PatchMapping
    private void updateTask(@RequestBody GroupModel groupModel) {
        groupService.updateGroup(groupModel.getId(), groupModel.getTitle());
    }

    @DeleteMapping(path = "/{id}")
    public void deleteGroup(
            @PathVariable(name = "id") Long id,
            @RequestHeader(name = "Authorization") String auth)
            throws AuthenticationException {
        Long userId = tokenProvider.getUserIdFromAuthenticationString(auth);
        GroupEntity group = groupService.findGroupById(id);
        boolean isOwner = group.getProject().getRoleByMemberId(userId).compareTo("owner") == 0;
        if (isOwner)
            groupService.deleteGroup(id);
        else throw new AuthenticationException("You have no permission to delete this group.");
    }
}
