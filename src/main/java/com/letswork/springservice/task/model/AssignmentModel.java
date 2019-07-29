package com.letswork.springservice.task.model;

import lombok.Data;

@Data
public class AssignmentModel {
    Long taskId;
    Long assignerId;
    Long userId;

    public AssignmentModel() {
    }

    public AssignmentModel(Long taskId, Long assignerId, Long userId) {
        this.taskId = taskId;
        this.assignerId = assignerId;
        this.userId = userId;
    }
}
