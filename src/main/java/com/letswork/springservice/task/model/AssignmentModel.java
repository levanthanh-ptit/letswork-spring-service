package com.letswork.springservice.task.model;

import com.letswork.springservice.repositories.entities.AssignmentEntity;
import com.letswork.springservice.utils.ObjectUtil;
import lombok.Data;

import java.util.List;

@Data
public class AssignmentModel {
    Long assignerId;
    Long userId;

    public AssignmentModel() {
    }

    public AssignmentModel(AssignmentEntity entity){
        assignerId = entity.getAssignerId();
        userId = entity.getUser().getId();
    }

    public static List<AssignmentModel> toAssignmentModelList(List<AssignmentEntity> taskEntity){
        ObjectUtil<AssignmentEntity, AssignmentModel> converter = AssignmentModel::new;
        return converter.convertToList(taskEntity);
    }
}
