package com.letswork.springservice.repositories.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Entity(name = "task")
@Table(name = "task")
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String title;

    String description;

    @Column(columnDefinition = "BIGINT default 0")
    Long estimateTime;

    @Column(columnDefinition = "BIGINT default 0")
    Long spendTime;

    public TaskEntity() {
    }

    public TaskEntity(String title, String description) {
        this.title = title;
        this.description = description;
    }

    @ManyToOne(
            fetch = FetchType.LAZY,
            targetEntity = GroupEntity.class
    )
    @JoinColumn(name = "group_id")
    GroupEntity group;

    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "task",
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            targetEntity = AssignmentEntity.class
    )
    private List<AssignmentEntity> assignment = new ArrayList<>();

    public void addAssignment(Long assignerId, UserEntity userEntity) {
        AssignmentEntity assignmentEntity = new AssignmentEntity(assignerId, userEntity, this);
        this.assignment.add(assignmentEntity);
    }

    public void removeAssignment(UserEntity userEntity) {
        for (AssignmentEntity e : assignment) {
            if (e.getAssignmentPK().getUserId().equals(userEntity.getId())
                    && e.getAssignmentPK().getTaskId().equals(this.id)) {
                userEntity.getAssignment().remove(e);
                assignment.remove(e);
                e.setAssignerId(null);
                e.setAssignmentPK(null);
                e.setTask(null);
                e.setUser(null);
                break;
            }
        }
    }
    public void removeAssignmentById(Long id) {
        for (AssignmentEntity e : assignment) {
            if (e.getUser().getId().compareTo(id) == 0)
                assignment.remove(e);
            break;
        }
    }
    //
    // Override Methods
    //
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskEntity that = (TaskEntity) o;
        return Objects.equals(title, that.title) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description);
    }
}
