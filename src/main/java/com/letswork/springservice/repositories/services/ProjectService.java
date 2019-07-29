package com.letswork.springservice.repositories.services;

import com.letswork.springservice.generalexception.BadRequestException;
import com.letswork.springservice.repositories.CRUD.ProjectCrud;
import com.letswork.springservice.repositories.entities.GroupEntity;
import com.letswork.springservice.repositories.entities.ProjectEntity;
import com.letswork.springservice.repositories.entities.RoleEntity;
import com.letswork.springservice.repositories.entities.UserEntity;
import com.letswork.springservice.group.model.GroupModel;
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

    public ProjectEntity findProjectById(Long projectId) throws BadRequestException {
        Optional<ProjectEntity> projectEntity = projectCrud.findById(projectId);
        if (!projectEntity.isPresent()) throw new BadRequestException("project id not found");
        return projectEntity.get();
    }

    public List<ProjectEntity> findAll() throws BadRequestException {
        List<ProjectEntity> allProject = (List<ProjectEntity>) projectCrud.findAll();
        if (allProject.isEmpty()) throw new BadRequestException("project id not found");
        return allProject;
    }

    public void addUserToProject(Long userId, String role, Long projectId) throws BadRequestException {
        UserEntity user = userService.findUserById(userId);
        ProjectEntity project = findProjectById(projectId);
        project.addUser(user, role);
        projectCrud.save(project);
    }

    public GroupEntity addTaskGroupToProject(String title, Long projectId) throws BadRequestException{
        if(title == null ) throw new BadRequestException("title is null");
        ProjectEntity project = findProjectById(projectId);
        GroupEntity groupEntity = project.addTaskGroup(title);
        projectCrud.save(project);
        return groupEntity;
    }

    public void seedingProject(Long id) throws BadRequestException {
        for (int i = 1; i <= 5; i++) {
            ProjectEntity projectEntity = new ProjectEntity("name " + i, "description " + i);
            UserEntity user = userService.findUserById(id);
            projectEntity.addUser(user, "owner");
            projectCrud.save(projectEntity);
        }
    }

    public List<RoleEntity> findProjectMember(Long id){
        ProjectEntity projectEntity = this.findProjectById(id);
        return  projectEntity.getOwnerships();
    }
}
