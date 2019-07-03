package com.letswork.springservice.project.model;

public class MemberInfoModel {
    private Long id;

    private String fullName;

    private String ownership;

    public MemberInfoModel(Long id, String fullName, String ownership) {
        this.id = id;
        this.fullName = fullName;
        this.ownership = ownership;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getOwnership() {
        return ownership;
    }

    public void setOwnership(String ownership) {
        this.ownership = ownership;
    }
}
