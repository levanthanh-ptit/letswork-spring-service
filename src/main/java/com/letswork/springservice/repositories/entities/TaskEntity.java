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

    public TaskEntity() {
    }

    public TaskEntity(String title, String description) {
        this.title = title;
        this.description = description;
    }

    @ManyToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            targetEntity = ProjectEntity.class
    )
    @JoinColumn(name = "project_id")
    ProjectEntity project;

    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "task",
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            targetEntity = AssignmentEntity.class
    )
    private List<AssignmentEntity> assignment = new ArrayList<>();

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

    @Override
    public String toString() {
        return "TaskEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", project=" + project +
                '}';
    }
}
