package com.linbingcheng.example.easemob.dao;

import com.linbingcheng.example.easemob.model.EasemobGroups;
import com.linbingcheng.example.easemob.model.EasemobUser;

import java.util.List;

public interface EasemobGroupsMapper {
    int deleteByPrimaryKey(String id);

    int insert(EasemobGroups record);

    int insertSelective(EasemobGroups record);

    EasemobGroups selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EasemobGroups record);

    int updateByPrimaryKey(EasemobGroups record);

    List<EasemobUser> selectAffiliationsByPrimaryKey(String id);
}