package com.letswork.springservice.repositories.entities;

import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "project")
@Table(name = "project")
@Data
public class ProjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String description;

    public ProjectEntity() {
    }

    public ProjectEntity(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // Bidirectional mapping to UserEntity
    @NotFound(action = NotFoundAction.IGNORE)
    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "project",
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            targetEntity = RoleEntity.class
    )
    private List<RoleEntity> ownership = new ArrayList<>();

    public ProjectEntity addUser(UserEntity userEntity, String role) {
        RoleEntity roleEntity = new RoleEntity(this, userEntity, role);
        ownership.add(roleEntity);
        userEntity.getOwnership().add(roleEntity);
        return this;
    }

    public void removeUser(UserEntity userEntity) {
        for (RoleEntity e : ownership) {
            if (e.getProject().equals(this) && e.getUser().equals(userEntity)) {
                ownership.remove(e);
                e.getUser().getOwnership().remove(e);
                e.getProject().getOwnership().remove(e);
                e.setUser(null);
                e.setProject(null);
            }
        }
    }

    // Bidirectional mapping to TaskEntity
    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "project"
    )
    List<TaskEntity> tasks = new ArrayList<>();

    public ProjectEntity addTask(String title, String description) {
        TaskEntity taskEntity = new TaskEntity(title, description);
        taskEntity.setProject(this);
        tasks.add(taskEntity);
        return this;
    }

    public void removeTask(TaskEntity taskEntity) {

    }

    @Override
    public String toString() {
        return "ProjectEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectEntity that = (ProjectEntity) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }
}
