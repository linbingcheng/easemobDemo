package com.linbingcheng.util;

import com.linbingcheng.example.activemq.common.Receiver;
import com.linbingcheng.example.activemq.dao.ActiveMQMappingMapper;
import com.linbingcheng.example.easemob.common.enumtype.TargetType;
import com.linbingcheng.example.easemob.dao.*;
import com.linbingcheng.example.easemob.model.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.*;

/**
 * Created by bingchenglin on 2016/12/12.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration({"/system/applicationContext-*.xml"})
public class SpringTest {


    private EasemobUserMapper mapper;
    private EasemobMessageMapper msgMapper;
    private EasemobChatRoomMapper chatRoomMapper;
    private ChatRoomMapperUserMapper chatRoomMapperUserMapper;
    private EasemobGroupsMapper groupsMapper;
    private GroupsMapperUserMapper groupsMapperUserMapper;
    private ActiveMQMappingMapper activeMQMappingMapper;


    /**
     * 这个before方法在所有的测试方法之前执行，并且只执行一次
     * 所有做Junit单元测试时一些初始化工作可以在这个方法里面进行
     * 比如在before方法里面初始化ApplicationContext和mapper
     */

    @Test
    public void testAddUser(){
        EasemobUser user = new EasemobUser();
        user.setId(UUID.randomUUID().toString());
        user.setPassword("111111");
        user.setUsername("￥￥￥￥￥1");
        user.setNickName("TTT");
        user.setUserId("YYYYYYY1");
        mapper.insert(user);
    }

    @Test
    public void testAddMsg(){
        EasemobMessage msg = new EasemobMessage();
        msg.setId(UUID.randomUUID().toString());
        msg.setFromUser("ddddddd");
        msg.setMsg("yyy");
        msg.setTargetType(TargetType.chatgroups);
        msg.setSendDate(new Date());
        msgMapper.insert(msg);
    }

    @Test
    public void testgetMsg(){
        EasemobMessage msg = msgMapper.selectByPrimaryKey("bab604ea-f926-4d50-8a9d-9ee24bd050fb");
        System.out.println(msg);
    }

    @Test
    public void testaddChatRoom(){
        EasemobChatRoom chatRoom = new EasemobChatRoom();
        String id = UUID.randomUUID().toString();
        chatRoom.setId(id);
        chatRoom.setName("测试聊天室1");
        chatRoom.setAffiliationsCount(1);
        chatRoom.setCreateTime(new Date());
        chatRoom.setOwner("942126c1-85de-45f5-8dc3-ee7bd4420b72");
        chatRoomMapper.insert(chatRoom);
        ChatRoomMapperUserKey roomuser = new ChatRoomMapperUserKey();
        roomuser.setChatRoomId(id);
        roomuser.setUserId("942126c1-85de-45f5-8dc3-ee7bd4420b72");
        chatRoomMapperUserMapper.insert(roomuser);
    }


    @Test
    public void testAddChatUser(){
        ChatRoomMapperUserKey roomuser = new ChatRoomMapperUserKey();
        roomuser.setChatRoomId("72be48f8-c996-4cc6-b590-599e34037c6f");
        roomuser.setUserId("1d32dd20-0d98-4d77-b9f2-aa2e1e30ecaf");
        chatRoomMapperUserMapper.insert(roomuser);
    }


    @Test
    public void testGetChatRoom(){
        EasemobChatRoom chatRoom = chatRoomMapper.selectByPrimaryKey("72be48f8-c996-4cc6-b590-599e34037c6f");
        List<EasemobUser> users =  chatRoom.getAffiliations();
        for (EasemobUser user : users){
            System.out.println(user.getId());
            System.out.println(user.getNickName());
        }
    }


    @Test
    public void testaddGroups(){
        EasemobGroups groups = new EasemobGroups();
        String id = UUID.randomUUID().toString();
        groups.setId(id);
        groups.setName("测试群组1");
        groups.setAffiliationsCount(1);
        groups.setCreateTime(new Date());
        groups.setOwner("942126c1-85de-45f5-8dc3-ee7bd4420b72");
        groupsMapper.insert(groups);
        GroupsMapperUserKey gu = new GroupsMapperUserKey();
        gu.setGroupsId(id);
        gu.setUserId("942126c1-85de-45f5-8dc3-ee7bd4420b72");
        groupsMapperUserMapper.insert(gu);
    }

    @Test
    public void testAddGroupsUser(){
        GroupsMapperUserKey gu = new GroupsMapperUserKey();
        gu.setGroupsId("0c743fcc-f717-4fe3-b297-167a16e14c56");
        gu.setUserId("1d32dd20-0d98-4d77-b9f2-aa2e1e30ecaf");
        groupsMapperUserMapper.insert(gu);
    }


    @Test
    public void testGetGroups(){
        EasemobGroups g = groupsMapper.selectByPrimaryKey("0c743fcc-f717-4fe3-b297-167a16e14c56");
        List<EasemobUser> users =  g.getAffiliations();
        for (EasemobUser user : users){
            System.out.println(user.getId());
            System.out.println(user.getNickName());
        }
    }

    @Test
    public void testGetUser(){
        EasemobUser user = mapper.selectByPrimaryKey("942126c1-85de-45f5-8dc3-ee7bd4420b72");
        List<EasemobGroups> groupses = user.getGroupses();
        List<EasemobChatRoom> chatRooms = user.getChatRooms();
        for (EasemobGroups g :groupses){
            System.out.println(g.getName());
        }
        System.out.println("++++++++++++++++++");
        for (EasemobChatRoom r : chatRooms){
            System.out.println(r.getName());
        }
    }

    @Test
    public void testFindActice(){
        String[] sss = activeMQMappingMapper.getAllQueueName();
        for (String s:sss){
            System.out.println(s);
        }
    }

    @Test
    public void testMap(){
        List<Map<String, Object>> regionMap = activeMQMappingMapper.getConfigMap("test");
        Map<String, String> resultMap = new HashMap<String, String>();
        for (Map<String, Object> map : regionMap) {
            String region = null;
            String id = null;
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if ("mapping_name".equals(entry.getKey())) {
                    region = (String) entry.getValue();
                } else if ("mapping_value".equals(entry.getKey())) {
                    id =  (String) entry.getValue();
                }
            }
            resultMap.put(region, id);
        }
        for (String key : resultMap.keySet()){
            System.out.println(key+":"+resultMap.get(key));
        }
    }




}
