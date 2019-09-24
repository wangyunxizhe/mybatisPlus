package com.yuan.select;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
public class CustomSqlTest {

    @Autowired
    private MPUserMapper mpUserMapper;

    @Test
    public void selectCustom() {
        LambdaQueryWrapper<MPUser> lambdaQW = new LambdaQueryWrapper<>();
        lambdaQW.likeRight(MPUser::getName, "王")
                .and(lqw -> lqw.lt(MPUser::getAge, 40).or().isNotNull(MPUser::getEmail));
        List<MPUser> users = mpUserMapper.selectAllDIY(lambdaQW);
        users.forEach(System.out::println);
    }

    @Test
    public void selectCustomByXML() {
        LambdaQueryWrapper<MPUser> lambdaQW = new LambdaQueryWrapper<>();
        lambdaQW.likeRight(MPUser::getName, "王")
                .and(lqw -> lqw.lt(MPUser::getAge, 40).or().isNotNull(MPUser::getEmail));
        List<MPUser> users = mpUserMapper.selectAllByXML(lambdaQW);
        users.forEach(System.out::println);
    }

}
