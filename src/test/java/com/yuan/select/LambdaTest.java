package com.yuan.select;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.LambdaQueryChainWrapper;
import com.yuan.dao.MPUserMapper;
import com.yuan.entity.MPUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LambdaTest {

    @Autowired
    private MPUserMapper mpUserMapper;

    @Test
    public void selectLambda() {
        LambdaQueryWrapper<MPUser> lambdaQW = new LambdaQueryWrapper<>();
        lambdaQW.like(MPUser::getName, "王").lt(MPUser::getAge, 40);
        List<MPUser> users = mpUserMapper.selectList(lambdaQW);
        users.forEach(System.out::println);
    }

    @Test
    public void selectLambdaChain() {
        //LambdaQueryChainWrapper支持一直连写
        List<MPUser> users = new LambdaQueryChainWrapper<>(mpUserMapper)
                .like(MPUser::getName, "王").lt(MPUser::getAge, 40).list();
        users.forEach(System.out::println);
    }
}
