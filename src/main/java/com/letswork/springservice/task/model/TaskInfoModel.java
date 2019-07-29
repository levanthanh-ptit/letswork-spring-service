package com.letswork.springservice.task.model;

import com.letswork.springservice.repositories.entities.TaskEntity;
import com.letswork.springservice.group.model.GroupModel;
import com.letswork.springservice.utils.ObjectUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class TaskInfoModel extends TaskModel {

    GroupModel groupModel;

    public TaskInfoModel(TaskEntity entity) {
        super(entity);
    }

    public static List<TaskInfoModel> toTaskInfoModelList(List<TaskEntity> taskEntity){
        ObjectUtil<TaskEntity, TaskInfoModel> converter = TaskInfoModel::new;
        return converter.convertToList(taskEntity);
    }
}
