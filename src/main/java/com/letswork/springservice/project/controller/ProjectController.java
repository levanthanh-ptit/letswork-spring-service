package com.letswork.springservice.project.controller;

import com.letswork.springservice.project.model.MemberModel;
import com.letswork.springservice.project.model.ProjectInfoModel;
import com.letswork.springservice.repositories.services.ProjectService;
import com.letswork.springservice.task.model.TaskModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/project")
public class ProjectController {

    @Autowired
    ProjectService projectService;

    @GetMapping(path = "/all", produces = "application/json")
    private List<ProjectInfoModel> getAllProjects() {
        return ProjectInfoModel.convertFromProjectEntities(projectService.findAll());
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    private ProjectInfoModel getProjectInfoById(@PathVariable(name = "id") Long projectId) {
        return new ProjectInfoModel(projectService.findProjectById(projectId));
    }

    @GetMapping(path = "/seeding-data")
    private void seedingProject(@RequestParam(name = "owner_id") Long id) {
        projectService.seedingProject(id);
    }

    @PostMapping(path = "/add-user")
    private void addUserToProject(@RequestParam(name = "user_id") Long userId,
                                  @RequestParam(name = "role") String role,
                                  @RequestParam(name = "project_id") Long projectId) {
        projectService.addUserToProject(userId, role, projectId);
    }

    @PostMapping(path = "/add-task", produces = "application/json")
    private void addTaskToProject(@RequestBody TaskModel task,
                                  @RequestParam(name = "project_id") Long projectId) {
        System.out.println(projectId);
        projectService.addTaskToProject( task, projectId);
    }

    @GetMapping(path = "/{id}/ownership")
    private List<MemberModel> getMembers(@PathVariable(name = "id") Long projectId) {
        return this.getProjectInfoById(projectId).getMembers();
    }

}
