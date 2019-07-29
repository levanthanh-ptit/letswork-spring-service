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

    Long estimateTime;

    Long spendTime;

    public TaskModel() {
    }

    public TaskModel(TaskEntity entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.description = entity.getDescription();
        this.estimateTime = entity.getEstimateTime();
        this.spendTime = entity.getSpendTime();
    }

    public static List<TaskModel> toTaskModelList(List<TaskEntity> taskEntity){
        ObjectUtil<TaskEntity, TaskModel> converter = TaskModel::new;
        return converter.convertToList(taskEntity);
    }
}
