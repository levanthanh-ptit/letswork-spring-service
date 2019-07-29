package com.letswork.springservice.group.model;

import com.letswork.springservice.repositories.entities.GroupEntity;
import com.letswork.springservice.utils.ObjectUtil;
import lombok.Data;

import java.util.List;

@Data
public class GroupModel {

    Long id;

    String title;

    public GroupModel() {
    }

    public GroupModel(GroupEntity entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
    }

    public static List<GroupModel> toTaskGroupModelList(List<GroupEntity> taskEntity){
        ObjectUtil<GroupEntity, GroupModel> converter = GroupModel::new;
        return converter.convertToList(taskEntity);
    }
}
