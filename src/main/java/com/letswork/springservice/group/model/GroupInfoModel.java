package com.letswork.springservice.group.model;

import com.letswork.springservice.repositories.entities.GroupEntity;
import com.letswork.springservice.task.model.TaskModel;
import com.letswork.springservice.utils.ObjectUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class GroupInfoModel extends GroupModel {

    List<TaskModel> tasks = new ArrayList<>();

    public GroupInfoModel(){}

    public GroupInfoModel(GroupEntity groupEntity){
        super(groupEntity);
        tasks = TaskModel.toTaskModelList(groupEntity.getTasks());
    }

    public static List<GroupInfoModel> toTaskGroupInfoModelList(List<GroupEntity> taskEntity){
        ObjectUtil<GroupEntity, GroupInfoModel> converter = GroupInfoModel::new;
        return converter.convertToList(taskEntity);
    }
}
