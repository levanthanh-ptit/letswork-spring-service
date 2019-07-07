package com.letswork.springservice.repositories.entities;

import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "user")
@Table(name = "user")
@Data
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String userName;

    private String firstName;

    private String lastName;

    private String email;

    private String company;

    private String bio;

    private String country;

    private String hashPassword;

    public UserEntity() {
    }

    public UserEntity(String userName,
                      String password,
                      String firstName,
                      String lastName,
                      String email,
                      String company,
                      String bio,
                      String country) {
        this.userName = userName;
        this.hashPassword = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.company = company;
        this.bio = bio;
        this.country = country;
    }

    // Bidirectional mapping to ProjectEntity
    @NotFound(action = NotFoundAction.IGNORE)
    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "user",
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            targetEntity = RoleEntity.class
    )
    private List<RoleEntity> ownership = new ArrayList<>();

    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "user",
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            targetEntity = AssignmentEntity.class
    )
    private List<AssignmentEntity> assignment = new ArrayList<>();

    //
    // Override Methods
    //
    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", company='" + company + '\'' +
                ", bio='" + bio + '\'' +
                ", country='" + country + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return userName.equals(that.userName) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                email.equals(that.email) &&
                Objects.equals(company, that.company) &&
                Objects.equals(bio, that.bio) &&
                Objects.equals(country, that.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, firstName, lastName, email, company, bio, country);
    }
}
