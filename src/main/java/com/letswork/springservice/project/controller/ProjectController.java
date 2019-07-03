package com.letswork.springservice.project.controller;

import com.letswork.springservice.genneralexception.NoContentException;
import com.letswork.springservice.genneralexception.model.ExceptionModel;
import com.letswork.springservice.project.model.MemberInfoModel;
import com.letswork.springservice.project.model.ProjectInfoModel;
import com.letswork.springservice.repositories.CRUD.ProjectCrud;
import com.letswork.springservice.repositories.CRUD.UserCrud;
import com.letswork.springservice.repositories.entities.ProjectEntity;
import com.letswork.springservice.repositories.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/project")
public class ProjectController {

    @Autowired
    private ProjectCrud projectCrud;
    @Autowired
    private UserCrud userCrud;

    @ExceptionHandler(NoContentException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    private ExceptionModel handleUserException(NoContentException e){
        return new ExceptionModel(e.getMessage());
    }

    @GetMapping(path = "/all", produces = "application/json")
    private Iterable<ProjectEntity> getAllProjects() {
        Iterable<ProjectEntity> allProject = projectCrud.findAll();
        return allProject;
    }

    @GetMapping(path = "/info", produces = "application/json")
    private ProjectInfoModel getProjectInfoById(@RequestParam Long id, @RequestHeader String Authorization) {
        Optional<ProjectEntity> project = projectCrud.findById(id);
        return new ProjectInfoModel(project.get());
    }

    @GetMapping(path = "/seeding-data")
    private void seedingProject(@RequestParam(name = "owner_id") Long id) {
        for (int i = 1; i <= 5; i++) {
            ProjectEntity projectEntity = new ProjectEntity("name "+i, "description "+i);
            Optional<UserEntity> userEntity = userCrud.findById(id);
            if(!userEntity.isPresent()) throw new NoContentException("owner id not found");
            projectEntity.addUsers(userEntity.get(),"owner");
            projectCrud.save(projectEntity);
        }
    }

    @PostMapping(path = "/add-user")
    private void addUserToProject(@RequestParam(name = "user_id") Long userId,
                                  @RequestParam(name = "role") String role,
                                  @RequestParam(name = "project_id") Long projectId){
        Optional<UserEntity> userEntity = userCrud.findById(userId);
        if(!userEntity.isPresent()) throw new NoContentException("owner id not found");
        UserEntity user = userEntity.get();

        Optional<ProjectEntity> projectEntity = projectCrud.findById(projectId);
        if(!projectEntity.isPresent()) throw new NoContentException("project id not found");
        ProjectEntity project = projectEntity.get();

        project.addUsers(user, role);
        user.setBio("i dont know");
        projectCrud.save(project);
    }

    @GetMapping(path = "/{id}/users")
    private List<MemberInfoModel> getMembers(@RequestPart(name = "id") Long id){
        return null;
    }

}
