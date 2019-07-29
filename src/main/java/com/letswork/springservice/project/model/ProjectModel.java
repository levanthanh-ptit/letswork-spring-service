package com.letswork.springservice.project.model;

import com.letswork.springservice.repositories.entities.ProjectEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProjectModel {
    Long id;

    String name;

    String description;

    public ProjectModel() {
    }

    public ProjectModel(ProjectEntity projectEntity){
        this.id = projectEntity.getId();
        this.name = projectEntity.getName();
        this.description = projectEntity.getDescription();
    }

    public static List<ProjectModel> toProjectModelList(Iterable<ProjectEntity> projectEntities) {
        List<ProjectModel> projectModels = new ArrayList<>();
        for (ProjectEntity e : projectEntities) {
            projectModels.add(new ProjectModel(e));
        }
        return projectModels;
    }

}
