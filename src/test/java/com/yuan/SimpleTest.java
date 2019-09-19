package com.yuan;

import com.yuan.dao.MPUserMapper;
import com.yuan.entity.MPUser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SimpleTest {

    @Autowired
    private MPUserMapper mpUserMapper;

    @Test
    public void select() {
        //不加条件，查询全部
        List<MPUser> users = mpUserMapper.selectList(null);
        Assert.assertEquals(5, users.size());
        users.forEach(System.out::println);
    }

}
