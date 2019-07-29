package com.letswork.springservice.group.controller;

import com.letswork.springservice.group.model.GroupModel;
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
        TaskModel createdTask = new TaskModel(groupService.addTask(taskModel, taskGroupId));
        return new ResponseEntity<>( createdTask, HttpStatus.CREATED);
    }

    @PatchMapping
    private void updateTask(@RequestBody GroupModel groupModel){
        groupService.updateGroup(groupModel.getId(), groupModel.getTitle());
    }

    @DeleteMapping(path = "/{id}")
    public void deleteGroup(@PathVariable(name = "id") Long id){
        groupService.deleteGroup(id);
    }
}
