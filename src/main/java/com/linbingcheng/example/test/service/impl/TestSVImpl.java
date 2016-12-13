package com.linbingcheng.example.test.service.impl;

import com.linbingcheng.example.test.dao.TestMapper;
import com.linbingcheng.example.test.model.Test;
import com.linbingcheng.example.test.service.interfaces.ITestSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by bingchenglin on 2016/12/12.
 */
@Service("testSV")
public class TestSVImpl implements ITestSV {

    private TestMapper testMapper;

    public void insert(Test test) {
        testMapper.insert(test);
    }

    public Test getTest(Integer id) {
        return testMapper.selectByPrimaryKey(id);
    }

    public TestMapper getTestMapper() {
        return testMapper;
    }

    @Autowired
    public void setTestMapper(TestMapper testMapper) {
        this.testMapper = testMapper;
    }
}
