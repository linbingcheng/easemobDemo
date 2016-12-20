package com.linbingcheng.example.easemob.dao;

import com.linbingcheng.example.easemob.model.GroupsMapperUserKey;

public interface GroupsMapperUserMapper {
    int deleteByPrimaryKey(GroupsMapperUserKey key);

    int insert(GroupsMapperUserKey record);

    int insertSelective(GroupsMapperUserKey record);
}