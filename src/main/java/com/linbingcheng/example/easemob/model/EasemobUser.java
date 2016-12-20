package com.linbingcheng.example.easemob.model;

import java.util.Date;
import java.util.List;

public class EasemobUser {
    private String id;

    private String userId;

    private String username;

    private String password;

    private String nickName;

    private Date createTime;

    private List<EasemobChatRoom> chatRooms;

    private List<EasemobGroups> groupses;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<EasemobChatRoom> getChatRooms() {
        return chatRooms;
    }

    public void setChatRooms(List<EasemobChatRoom> chatRooms) {
        this.chatRooms = chatRooms;
    }

    public List<EasemobGroups> getGroupses() {
        return groupses;
    }

    public void setGroupses(List<EasemobGroups> groupses) {
        this.groupses = groupses;
    }
}