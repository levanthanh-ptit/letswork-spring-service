package com.letswork.springservice.task.model;

import com.letswork.springservice.project.model.ProjectModel;
import lombok.Data;

@Data
public class TaskInfoModel extends TaskModel {

    ProjectModel project;

}
