package com.genomic.Fibersmart.model;


import javax.persistence.*;
import java.util.List;

/**
 * {@code userGroups} have a {@code @ManyToMany} mapping with {@code users}. {@code userGroups} are made up of a
 * {@code @ManyToMany} mapping of {@link GroupPermission permissions}, which define user group access rights to
 * different areas of the application via the controllers.
 */
@Entity
@Table(name = "usergroups")
public class UserGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    /**
     * Name of the {@link UserGroup userGroups}.
     */
    @Column(name = "group_name")
    private String groupName;

    @Column(name = "is_activated")
    private Boolean isActivated;

    /**
     * {@link UserGroup userGroups} are made up of a {@code @ManyToMany} mapping of {@link GroupPermission permissions}.
     * Different {@code userGroups} can share the same predefined {@code permissions}.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usergroups_grouppermissions",
            joinColumns = {@JoinColumn(name = "usergroup_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "grouppermission_id", referencedColumnName = "id")})
    List<GroupPermission> groupPermissions;


    public UserGroup() {

    }

    public UserGroup(final String groupName, final Boolean isActivated, final List<GroupPermission> groupPermissions) {
        this.groupName = groupName;
        this.isActivated = isActivated;
        this.groupPermissions = groupPermissions;
    }

    public UserGroup(final Long id, final String groupName, final Boolean isActivated,
                     final List<GroupPermission> groupPermissions) {
        this.id = id;
        this.groupName = groupName;
        this.isActivated = isActivated;
        this.groupPermissions = groupPermissions;
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

    public List<GroupPermission> getGroupPermissions() {
        return groupPermissions;
    }

    public void setGroupPermissions(final List<GroupPermission> groupPermissions) {
        this.groupPermissions = groupPermissions;
    }
}
