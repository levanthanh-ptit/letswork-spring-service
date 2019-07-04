package com.letswork.springservice.project.model;

import com.letswork.springservice.repositories.entities.ProjectEntity;
import com.letswork.springservice.repositories.entities.RoleEntity;

import java.util.ArrayList;
import java.util.List;

public class ProjectInfoModel extends ProjectModel{

    private List<MemberInfoModel> members = new ArrayList<>();

    public ProjectInfoModel(ProjectEntity projectEntity) {
        this.id = projectEntity.getId();
        this.name = projectEntity.getName();
        this.description = projectEntity.getDescription();
        for (RoleEntity e : projectEntity.getUsers()) {
            if (e.getProject().equals(projectEntity)) {
                members.add(new MemberInfoModel(e.getUser(), e.getRole()));
            }
        }
    }

    public List<MemberInfoModel> getMembers() {
        return members;
    }

    public void setMembers(List<MemberInfoModel> members) {
        this.members = members;
    }

    public static List<ProjectInfoModel> convertFromProjectEntity(Iterable<ProjectEntity> projectEntities) {
        List<ProjectInfoModel> projectInfoModels = new ArrayList<>();
        for (ProjectEntity e : projectEntities) {
            projectInfoModels.add(new ProjectInfoModel(e));
        }
        return projectInfoModels;
    }
}
