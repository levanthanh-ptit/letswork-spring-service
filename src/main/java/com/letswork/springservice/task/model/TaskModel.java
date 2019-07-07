package com.letswork.springservice.task.model;

import com.letswork.springservice.repositories.entities.TaskEntity;
import com.letswork.springservice.utils.ObjectUtil;
import lombok.Data;

import java.util.List;

@Data
public class TaskModel {
    Long id;

    String title;

    String description;

    public TaskModel() {
    }

    public TaskModel(TaskEntity entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.description = entity.getDescription();
    }

    public static List<TaskModel> convertFromTaskEntities(List<TaskEntity> taskEntity){
        ObjectUtil<TaskEntity, TaskModel> converter = TaskModel::new;
        return converter.convertToList(taskEntity);
    }
}
