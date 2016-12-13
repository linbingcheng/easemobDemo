package com.linbingcheng.util;

import com.linbingcheng.example.test.service.interfaces.ITestSV;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by bingchenglin on 2016/12/12.
 */
public class SpringTest {

    private ITestSV testSV;

    /**
     * 这个before方法在所有的测试方法之前执行，并且只执行一次
     * 所有做Junit单元测试时一些初始化工作可以在这个方法里面进行
     * 比如在before方法里面初始化ApplicationContext和userService
     */
    @Before
    public void before(){
        //使用"spring.xml"和"spring-mybatis.xml"这两个配置文件创建Spring上下文
        ApplicationContext ac = new ClassPathXmlApplicationContext(new String[]{"system/applicationContext-main.xml","system/applicationContext-mybatis.xml"});
        testSV = (ITestSV) ac.getBean("testSV");
    }

    @Test
    public void testAddTest(){
        com.linbingcheng.example.test.model.Test t = new com.linbingcheng.example.test.model.Test();
        t.setId(3);
        t.setName("测试");
        testSV.insert(t);
    }

}
