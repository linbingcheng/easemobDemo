package com.linbingcheng.example.easemob.dao;

import com.linbingcheng.example.easemob.model.EasemobMessage;

public interface EasemobMessageMapper {
    int deleteByPrimaryKey(String id);

    int insert(EasemobMessage record);

    int insertSelective(EasemobMessage record);

    EasemobMessage selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EasemobMessage record);

    int updateByPrimaryKeyWithBLOBs(EasemobMessage record);

    int updateByPrimaryKey(EasemobMessage record);
}