package com.letswork.springservice.repositories.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity(name = "group")
@Table(name = "`group`")
public class GroupEntity {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    Long id;

    String title;

    @ManyToOne(
            fetch = FetchType.LAZY,
            targetEntity = ProjectEntity.class
    )
    @JoinColumn(name = "project_id")
    ProjectEntity project;

    public GroupEntity() {
    }

    public GroupEntity(String title) {
        this.title = title;
    }

    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "group"
    )
    List<TaskEntity> tasks = new ArrayList<>();

    public TaskEntity addTask(String title, String description) {
        TaskEntity taskEntity = new TaskEntity(title, description);
        taskEntity.setGroup(this);
        tasks.add(taskEntity);
        return taskEntity;
    }

    public void addTask(TaskEntity task){
        tasks.add(task);
    }

    public void removeTask(TaskEntity taskEntity) {
        tasks.remove(taskEntity);
    }
}
