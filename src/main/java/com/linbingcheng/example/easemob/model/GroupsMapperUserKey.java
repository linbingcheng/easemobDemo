package com.linbingcheng.example.easemob.model;

public class GroupsMapperUserKey {
    private String groupsId;

    private String userId;

    public String getGroupsId() {
        return groupsId;
    }

    public void setGroupsId(String groupsId) {
        this.groupsId = groupsId == null ? null : groupsId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }
}