package com.letswork.springservice.project.controller;

import com.letswork.springservice.genneralexception.NoContentException;
import com.letswork.springservice.genneralexception.model.ExceptionModel;
import com.letswork.springservice.project.model.MemberInfoModel;
import com.letswork.springservice.project.model.ProjectInfoModel;
import com.letswork.springservice.repositories.entities.ProjectEntity;
import com.letswork.springservice.repositories.entities.UserEntity;
import com.letswork.springservice.repositories.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/project")
public class ProjectController {

    @Autowired
    ProjectService projectService;

    @ExceptionHandler(NoContentException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    private ExceptionModel handleUserException(NoContentException e) {
        return new ExceptionModel(e.getMessage());
    }

    @GetMapping(path = "/all", produces = "application/json")
    private List<ProjectInfoModel> getAllProjects() {
        return ProjectInfoModel.convertFromProjectEntity(projectService.findAll());
    }

    @GetMapping(path = "/info", produces = "application/json")
    private ProjectInfoModel getProjectInfoById(@RequestParam(name = "id") Long projectId) {
        return new ProjectInfoModel(projectService.findProjectById(projectId));
    }

    @GetMapping(path = "/seeding-data")
    private void seedingProject(@RequestParam(name = "owner_id") Long id) {
        for (int i = 1; i <= 5; i++) {
            ProjectEntity projectEntity = new ProjectEntity("name " + i, "description " + i);
            Optional<UserEntity> userEntity = projectService.userCrud.findById(id);
            if (!userEntity.isPresent()) throw new NoContentException("owner id not found");
            projectEntity.addUser(userEntity.get(), "owner");
            projectService.projectCrud.save(projectEntity);
        }
    }

    @PostMapping(path = "/add-user")
    private void addUserToProject(@RequestParam(name = "user_id") Long userId,
                                  @RequestParam(name = "role") String role,
                                  @RequestParam(name = "project_id") Long projectId) {
        projectService.addUserToProject(userId, role, projectId);
    }

    @GetMapping(path = "/{id}/users")
    private List<MemberInfoModel> getMembers(@PathVariable(name = "id") Long projectId) {
        return this.getProjectInfoById(projectId).getMembers();
    }

}
