package com.linbingcheng.example.easemob.dao;

import com.linbingcheng.example.easemob.model.EasemobChatRoom;
import com.linbingcheng.example.easemob.model.EasemobUser;

import java.util.List;

public interface EasemobChatRoomMapper {
    int deleteByPrimaryKey(String id);

    int insert(EasemobChatRoom record);

    int insertSelective(EasemobChatRoom record);

    EasemobChatRoom selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EasemobChatRoom record);

    int updateByPrimaryKey(EasemobChatRoom record);

    List<EasemobUser> selectAffiliationsByPrimaryKey(String id);
}