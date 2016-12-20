package com.linbingcheng.example.easemob.dao;

import com.linbingcheng.example.easemob.model.ChatRoomMapperUserKey;

public interface ChatRoomMapperUserMapper {
    int deleteByPrimaryKey(ChatRoomMapperUserKey key);

    int insert(ChatRoomMapperUserKey record);

    int insertSelective(ChatRoomMapperUserKey record);
}