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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
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
    private List<RoleEntity> users = new ArrayList<>();

    public List<RoleEntity> getUsers() {
        return users;
    }

    public void addUser(UserEntity userEntity, String role) {
        RoleEntity roleEntity = new RoleEntity(this, userEntity, role);
        users.add(roleEntity);
        userEntity.getProjects().add(roleEntity);
    }

    public void removeUsers(UserEntity userEntity) {
        for (RoleEntity e : users) {
            if (e.getProject().equals(this) && e.getUser().equals(userEntity)) {
                users.remove(e);
                e.getUser().getProjects().remove(e);
                e.getProject().getUsers().remove(e);
                e.setUser(null);
                e.setProject(null);
            }
        }
    }

    // Bidirectional mapping to TaskEntity
    @NotFound(action = NotFoundAction.IGNORE)
    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "project",
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            targetEntity = TaskEntity.class
    )
    List<TaskEntity> tasks = new ArrayList<>();

    public List<TaskEntity> getTasks() {
        return tasks;
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
