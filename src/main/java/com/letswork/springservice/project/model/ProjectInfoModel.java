package com.letswork.springservice.project.model;

import com.letswork.springservice.repositories.entities.ProjectEntity;
import com.letswork.springservice.repositories.entities.RoleEntity;
import com.letswork.springservice.repositories.entities.TaskEntity;
import com.letswork.springservice.task.model.TaskModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProjectInfoModel extends ProjectModel {

    private List<MemberModel> members = new ArrayList<>();

    private List<TaskModel> tasks = new ArrayList<>();

    public ProjectInfoModel(ProjectEntity projectEntity) {
        this.id = projectEntity.getId();
        this.name = projectEntity.getName();
        this.description = projectEntity.getDescription();
        for (RoleEntity e : projectEntity.getOwnership()) {
            if (e.getProject().equals(projectEntity)) {
                members.add(new MemberModel(e.getUser(), e.getRole()));
            }
        }
        for(TaskEntity e : projectEntity.getTasks()){
            tasks.add(new TaskModel(e));
        }
    }

    public static List<ProjectInfoModel> convertFromProjectEntities(Iterable<ProjectEntity> projectEntities) {
        List<ProjectInfoModel> projectInfoModels = new ArrayList<>();
        for (ProjectEntity e : projectEntities) {
            projectInfoModels.add(new ProjectInfoModel(e));
        }
        return projectInfoModels;
    }
}
