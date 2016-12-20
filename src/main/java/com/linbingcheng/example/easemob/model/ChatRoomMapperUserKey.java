package com.linbingcheng.example.easemob.model;

public class ChatRoomMapperUserKey {
    private String chatRoomId;

    private String userId;

    public String getChatRoomId() {
        return chatRoomId;
    }

    public void setChatRoomId(String chatRoomId) {
        this.chatRoomId = chatRoomId == null ? null : chatRoomId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }
}