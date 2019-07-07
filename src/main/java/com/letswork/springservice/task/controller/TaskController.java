package com.letswork.springservice.task.controller;

import com.letswork.springservice.repositories.services.TaskService;
import com.letswork.springservice.task.model.TaskModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "/task")
public class TaskController {

    @Autowired
    TaskService taskService;

    @GetMapping(path = "/{id}")
    public TaskModel findTaskById(@PathVariable Long id){
        return new TaskModel(taskService.findTaskById(id));
    }

    @GetMapping(path = "/filter")
    public List<TaskModel> filterByProjectId(@RequestParam(name = "project_id") Long projectId){
        return TaskModel.convertFromTaskEntities(taskService.findAllByProjectId(projectId));
    }
}
