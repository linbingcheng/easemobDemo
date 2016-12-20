package com.linbingcheng.example.easemob.dao;

import com.linbingcheng.example.easemob.model.EasemobChatRoom;
import com.linbingcheng.example.easemob.model.EasemobGroups;
import com.linbingcheng.example.easemob.model.EasemobUser;

import java.util.List;

public interface EasemobUserMapper {
    int deleteByPrimaryKey(String id);

    int insert(EasemobUser record);

    int insertSelective(EasemobUser record);

    EasemobUser selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EasemobUser record);

    int updateByPrimaryKey(EasemobUser record);

    List<EasemobGroups> selectGroupsesByPrimaryKey(String id);

    List<EasemobChatRoom> selectChatRoomsByPrimaryKey(String id);
}