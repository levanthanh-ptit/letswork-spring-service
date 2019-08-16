package com.letswork.springservice.project.controller;

import com.letswork.springservice.auth.JwtTokenProvider;
import com.letswork.springservice.generalexception.AuthenticationException;
import com.letswork.springservice.project.model.MemberModel;
import com.letswork.springservice.project.model.ProjectCreationModel;
import com.letswork.springservice.project.model.ProjectModel;
import com.letswork.springservice.repositories.entities.ProjectEntity;
import com.letswork.springservice.repositories.services.ProjectService;
import com.letswork.springservice.repositories.services.GroupService;
import com.letswork.springservice.group.model.GroupInfoModel;
import com.letswork.springservice.group.model.GroupModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/project")
public class ProjectController {
    @Autowired
    JwtTokenProvider tokenProvider;
    @Autowired
    ProjectService projectService;
    @Autowired
    GroupService groupService;

    @PostMapping
    private ProjectModel createProject(@RequestBody ProjectCreationModel body) {
        return new ProjectModel(projectService.createProject(body.getUserId(), body.getName()));
    }

    @GetMapping(path = "/all", produces = "application/json")
    private List<ProjectModel> getAllProjects() {
        return ProjectModel.toProjectModelList(projectService.findAll());
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    private ProjectModel getProjectInfoById(@PathVariable(name = "id") Long projectId) {

        return new ProjectModel(projectService.findProjectById(projectId));
    }

    @PatchMapping(path = "/{id}")
    private void updateProject(@PathVariable Long id, @RequestBody ProjectModel project) {
        projectService.updateProject(id, project.getName(), project.getDescription());
    }

    @DeleteMapping(path = "/{id}")
    private void deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
    }

    @PostMapping(path = "/{project_id}/add-user")
    private void addUserToProject(@RequestParam(name = "user_id") Long userId,
                                  @RequestParam(name = "role") String role,
                                  @PathVariable(name = "project_id") Long projectId) {
        projectService.addUserToProject(userId, role, projectId);
    }

    @GetMapping(path = "/{id}/groups")
    private List<GroupInfoModel> findTaskGroupByProjectId(@PathVariable(name = "id") Long projectId) {
        return GroupInfoModel.toTaskGroupInfoModelList(groupService.findAllByProjectId(projectId));
    }

    @PostMapping(path = "/{project_id}/add-group", produces = "application/json")
    private ResponseEntity<GroupModel> addGroupToProject(
            @RequestBody GroupModel groupModel,
            @PathVariable(name = "project_id") Long projectId,
            @RequestHeader(name = "Authorization") String auth)
            throws AuthenticationException {
        ProjectEntity projectEntity = projectService.findProjectById(projectId);
        Long userId = tokenProvider.getUserIdFromAuthenticationString(auth);
        boolean isOwner = projectEntity.getRoleByMemberId(userId).compareTo("owner") == 0;
        if(!isOwner) throw new AuthenticationException("You have no permission to add a group.");
        GroupModel createdGroup = new GroupModel(projectService.addTaskGroupToProject(groupModel.getTitle(), projectId));
        return new ResponseEntity<>(createdGroup, HttpStatus.CREATED);
    }

    @GetMapping(path = "/{id}/ownerships")
    private List<MemberModel> getMembers(@PathVariable(name = "id") Long projectId) {
        return MemberModel.toMemberModelList(projectService.findProjectMember(projectId));
    }

    @GetMapping(path = "/seeding-data")
    private void seedingProject(@RequestParam(name = "owner_id") Long id) {
        projectService.seedingProject(id);
    }
}
