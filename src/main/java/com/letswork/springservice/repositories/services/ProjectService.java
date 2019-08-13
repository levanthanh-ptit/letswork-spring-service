package com.letswork.springservice.repositories.services;

import com.letswork.springservice.generalexception.BadRequestException;
import com.letswork.springservice.repositories.CRUD.ProjectCrud;
import com.letswork.springservice.repositories.entities.GroupEntity;
import com.letswork.springservice.repositories.entities.ProjectEntity;
import com.letswork.springservice.repositories.entities.RoleEntity;
import com.letswork.springservice.repositories.entities.UserEntity;
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
    @Autowired
    GroupService groupService;

    public ProjectEntity createProject(Long userId, String name){
        UserEntity userEntity = userService.findUserById(userId);
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setName(name);
        projectEntity.addUser(userEntity, "owner");
        return projectCrud.save(projectEntity);
    }

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

    public void deleteProject(Long id) {
        ProjectEntity project = findProjectById(id);
        projectCrud.delete(project);
    }

    public void updateProject(Long id, String name, String description){
        ProjectEntity project = findProjectById(id);
        project.setName(name);
        project.setDescription(description);
        projectCrud.save(project);
    }

    public void addUserToProject(Long userId, String role, Long projectId) throws BadRequestException {
        UserEntity user = userService.findUserById(userId);
        ProjectEntity project = findProjectById(projectId);
        boolean isMember = false;
        for (RoleEntity e: project.getOwnerships()) {
            if(e.getUser().equals(user)) throw new BadRequestException("User already member");
        }
        project.addUser(user, role);
        projectCrud.save(project);
    }

    public GroupEntity addTaskGroupToProject(String title, Long projectId) throws BadRequestException{
        if(title == null ) throw new BadRequestException("name is null");
        ProjectEntity project = findProjectById(projectId);
        GroupEntity groupEntity = new GroupEntity(title);
        groupEntity.setProject(project);
        return groupService.groupCrud.save(groupEntity);
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
