package com.genomic.Fibersmart.model;


import javax.persistence.*;

@Entity
@Table(name = "user_groups")
public class UserGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "group_name")
    private String groupName;

    @Column(name = "is_activated")
    private Boolean isActivated;

    public UserGroup(final Long id, final String groupName, final Boolean isActivated) {
        this.id = id;
        this.groupName = groupName;
        this.isActivated = isActivated;
    }

    public UserGroup() {

    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(final String groupName) {
        this.groupName = groupName;
    }

    public Boolean getActivated() {
        return isActivated;
    }

    public void setActivated(final Boolean activated) {
        isActivated = activated;
    }
}