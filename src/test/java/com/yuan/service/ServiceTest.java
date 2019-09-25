package com.yuan.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yuan.entity.MPUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceTest {

    @Autowired
    private MPUserService mpUserService;

    @Test
    public void getOne() {
        //getOne：默认只返回一行结果，当不加boolean类型入参时，返回多行结果回报错
        //这里的getOne方法与SimpleTest中selectOne方法的区别：
        //getOne可以传入一个boolean类型的入参，若传false当返回结果为多行时不会报错，而是返回第一行
        MPUser user = mpUserService.getOne(Wrappers.<MPUser>lambdaQuery().like(MPUser::getName, "王渊"), false);
        System.out.println(user);
    }

    @Test
    public void saveOrUpdate() {
        MPUser user1 = new MPUser();
        user1.setName("路飞");
        user1.setAge(20);

        MPUser user2 = new MPUser();
        user2.setId(1174607751544946689L);
        user2.setAge(22);

        List<MPUser> users = Arrays.asList(user1, user2);
        //saveOrUpdateBatch方法：有主键则先查询该主键是否存在，存在则更新，无则插入
        boolean flag = mpUserService.saveOrUpdateBatch(users);
        System.out.println("是否成功：" + flag);
    }

}
