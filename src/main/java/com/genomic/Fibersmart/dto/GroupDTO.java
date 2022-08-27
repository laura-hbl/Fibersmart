package com.genomic.Fibersmart.dto;

import com.genomic.Fibersmart.model.GroupPermission;

import java.util.List;

public class GroupDTO {

    private Long id;

    private String groupName;

    private Boolean isActivated;

    List<GroupPermission> groupPermissions;


    public GroupDTO(final Long id, final String groupName, final Boolean isActivated,
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
