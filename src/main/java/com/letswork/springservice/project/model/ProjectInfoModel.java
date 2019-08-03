package com.letswork.springservice.project.model;

import com.letswork.springservice.repositories.entities.ProjectEntity;
import com.letswork.springservice.repositories.entities.RoleEntity;
import com.letswork.springservice.repositories.entities.GroupEntity;
import com.letswork.springservice.group.model.GroupModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProjectInfoModel extends ProjectModel {

    private List<MemberModel> members = new ArrayList<>();

    private List<GroupModel> taskGroup = new ArrayList<>();

    public ProjectInfoModel(){}

    public ProjectInfoModel(ProjectEntity projectEntity) {
        super(projectEntity);
        for (RoleEntity e : projectEntity.getOwnerships()) {
            members.add(new MemberModel(e.getUser(), e.getRole()));

        }
        for (GroupEntity e : projectEntity.getGroups()) {
            taskGroup.add(new GroupModel(e));
        }
    }

    public static List<ProjectInfoModel> toProjectInfoModelList(Iterable<ProjectEntity> projectEntities) {
        List<ProjectInfoModel> projectInfoModels = new ArrayList<>();
        for (ProjectEntity e : projectEntities) {
            projectInfoModels.add(new ProjectInfoModel(e));
        }
        return projectInfoModels;
    }
}
