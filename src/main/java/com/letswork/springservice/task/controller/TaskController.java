package com.letswork.springservice.task.controller;

import com.letswork.springservice.repositories.services.GroupService;
import com.letswork.springservice.repositories.services.TaskService;
import com.letswork.springservice.task.model.AssignmentModel;
import com.letswork.springservice.task.model.TaskModel;
import com.letswork.springservice.user.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "/task")
public class TaskController {

    @Autowired
    TaskService taskService;
    @Autowired
    GroupService groupService;

    @GetMapping(path = "/all")
    public List<TaskModel> findAllTask() {
        return TaskModel.toTaskModelList(taskService.findAll());
    }

    @GetMapping(path = "/{id}")
    public TaskModel findTaskById(@PathVariable Long id) {
        return new TaskModel(taskService.findTaskById(id));
    }

    @GetMapping(path = "/filter")
    public List<TaskModel> filterByProjectId(@RequestParam(name = "project_id") Long projectId) {
        return TaskModel.toTaskModelList(taskService.findAllByProjectId(projectId));
    }

    @GetMapping(path = "/{id}/assignment")
    public List<AssignmentModel> getTaskAssignment(@PathVariable(name = "id") Long id) {
        return AssignmentModel.toAssignmentModelList(taskService.getAssignment(id));
    }

    @PostMapping(path = "/{id}/assign-user")
    public void assignUser(@PathVariable(name = "id") Long id, @RequestBody AssignmentModel body) {
        System.out.println(body.toString());
        taskService.assignMember(id, body.getAssignerId(), body.getUserId());
    }

    @DeleteMapping(path = "/{id}/remove-user")
    public void assignUser(@PathVariable(name = "id") Long id, @RequestParam(name = "user_id") Long userId) {
        taskService.removeAssignment(id, userId);
    }

    @PostMapping(path = "/change-group")
    public TaskModel changeGroup(@RequestParam(name = "id") Long id,
                            @RequestParam(name = "target_group_id") Long targetGroupId) {
        return new TaskModel(taskService.changeGroup(id, targetGroupId));
    }

    @PatchMapping(path = "/{id}")
    public void updateTask(@PathVariable Long id, @RequestBody TaskModel taskModel) {
        System.out.println(taskModel.toString());
        taskService.updateTask(
                id,
                taskModel.getTitle(),
                taskModel.getDescription(),
                taskModel.getEstimateTime(),
                taskModel.getSpendTime()
        );
    }

    @DeleteMapping(path = "/{id}")
    public void deleteTask(@PathVariable(name = "id") Long id) {
        taskService.deleteTask(id);
    }
}
