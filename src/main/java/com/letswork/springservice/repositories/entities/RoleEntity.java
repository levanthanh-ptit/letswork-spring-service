package com.letswork.springservice.repositories.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity(name = "ownership")
@Data
@Table(name = "ownership")
public class RoleEntity {

    private String role;

    @EmbeddedId
    private RolePK rolePK;

    @ManyToOne(
            targetEntity = ProjectEntity.class,
            fetch = FetchType.LAZY
    )
    @MapsId("projectId")
    @JoinColumn(name = "project_id")
    private ProjectEntity project;

    @ManyToOne(
            targetEntity = UserEntity.class,
            fetch = FetchType.LAZY
    )
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public RoleEntity() {
    }

    public RoleEntity(ProjectEntity project, UserEntity user, String role) {
        this.project = project;
        this.user = user;
        this.role = role;
        this.rolePK = new RolePK(user.getId(), project.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleEntity that = (RoleEntity) o;
        return project.equals(that.project) &&
                user.equals(that.user) &&
                role.equals(that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(project, user, role);
    }
}

// Composite primary key class

@Embeddable
@Data
class RolePK implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "project_id")
    private Long projectId;

    public RolePK() {
    }

    public RolePK(Long userId, Long projectId) {
        this.userId = userId;
        this.projectId = projectId;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        RolePK that = (RolePK) o;
//        return userId.equals(that.userId) &&
//                projectId.equals(that.projectId);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(userId, projectId);
//    }
}