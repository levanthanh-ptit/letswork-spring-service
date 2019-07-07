package com.letswork.springservice.repositories.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Data
@Entity(name = "assignment")
@Table(name = "assignment")
public class AssignmentEntity{
    private Long assignerId;

    @EmbeddedId
    private AssignmentPK assignmentPK;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @MapsId("taskId")
    @JoinColumn(name = "task_id")
    private TaskEntity task;

    public AssignmentEntity() {
    }

    public AssignmentEntity(Long assignerId, UserEntity user, TaskEntity task) {
        this.assignerId = assignerId;
        this.user = user;
        this.task = task;
        this.assignmentPK = new AssignmentPK(user.getId(), task.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AssignmentEntity that = (AssignmentEntity) o;
        return assignerId.equals(that.assignerId) &&
                assignmentPK.equals(that.assignmentPK);
    }

    @Override
    public int hashCode() {
        return Objects.hash(assignerId, assignmentPK);
    }
}

@Embeddable
@Data
class AssignmentPK  implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "task_id")
    private Long taskId;

    public AssignmentPK() {
    }

    public AssignmentPK(Long userId, Long taskId) {
        this.userId = userId;
        this.taskId = taskId;
    }
}