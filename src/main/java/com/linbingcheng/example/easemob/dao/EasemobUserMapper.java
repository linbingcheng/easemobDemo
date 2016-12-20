package com.linbingcheng.example.easemob.dao;

import com.linbingcheng.example.easemob.model.EasemobUser;

public interface EasemobUserMapper {
    int deleteByPrimaryKey(String id);

    int insert(EasemobUser record);

    int insertSelective(EasemobUser record);

    EasemobUser selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EasemobUser record);

    int updateByPrimaryKey(EasemobUser record);
}