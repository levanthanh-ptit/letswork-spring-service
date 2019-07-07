package com.letswork.springservice.repositories.services;

import com.letswork.springservice.generalexception.NoContentException;
import com.letswork.springservice.repositories.CRUD.ProjectCrud;
import com.letswork.springservice.repositories.CRUD.UserCrud;
import com.letswork.springservice.repositories.entities.ProjectEntity;
import com.letswork.springservice.repositories.entities.UserEntity;
import com.letswork.springservice.task.model.TaskModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    @Autowired
    ProjectCrud projectCrud;
    @Autowired
    UserService userService;

    public ProjectEntity findProjectById(Long projectId) throws NoContentException {
        Optional<ProjectEntity> projectEntity = projectCrud.findById(projectId);
        if (!projectEntity.isPresent()) throw new NoContentException("project id not found");
        return projectEntity.get();
    }

    public List<ProjectEntity> findAll() throws NoContentException {
        List<ProjectEntity> allProject = (List<ProjectEntity>) projectCrud.findAll();
        if (allProject.isEmpty()) throw new NoContentException("project id not found");
        return allProject;
    }

    public void addUserToProject(Long userId, String role, Long projectId) throws NoContentException{
//        Optional<UserEntity> userEntity = userCrud.findById(userId);
//        if(!userEntity.isPresent()) throw new NoContentException("owner id not found");
        UserEntity user = userService.findUserById(userId);
        ProjectEntity project = findProjectById(projectId);
        project.addUser(user, role);
        projectCrud.save(project);
    }

    public void addTaskToProject(TaskModel task, Long projectId){
        ProjectEntity project = findProjectById(projectId);
        System.out.println(project.toString());
        project.addTask(task.getTitle(),task.getDescription());
        projectCrud.save(project);
    }

    public void seedingProject(Long id) throws NoContentException{
        for (int i = 1; i <= 5; i++) {
            ProjectEntity projectEntity = new ProjectEntity("name " + i, "description " + i);
            UserEntity user = userService.findUserById(id);
            projectEntity.addUser(user, "owner");
            projectCrud.save(projectEntity);
        }
    }
}
