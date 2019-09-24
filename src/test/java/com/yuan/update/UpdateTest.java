package com.yuan.update;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.additional.update.impl.LambdaUpdateChainWrapper;
import com.yuan.dao.MPUserMapper;
import com.yuan.entity.MPUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UpdateTest {

    @Autowired
    private MPUserMapper mpUserMapper;

    @Test
    public void updateById() {
        MPUser user = new MPUser();
        user.setId(1174607751544946689L);
        user.setEmail("543456229@qq.com");
        int rows = mpUserMapper.updateById(user);
        System.out.println("影响的行数：" + rows);
    }

    @Test
    public void updateByWrapper() {
        UpdateWrapper<MPUser> uw = new UpdateWrapper<>();
        uw.eq("name", "王渊").eq("age", 31);
        MPUser user = new MPUser();
        user.setAge(32);
        int rows = mpUserMapper.update(user, uw);
        System.out.println("影响的行数：" + rows);
    }

    @Test
    public void updateByLambda() {
        LambdaUpdateWrapper<MPUser> luw = new LambdaUpdateWrapper<>();
        luw.eq(MPUser::getName, "王渊").eq(MPUser::getAge, 32).set(MPUser::getAge, 31);
        int rows = mpUserMapper.update(null, luw);
        System.out.println("影响的行数：" + rows);
    }

    @Test
    public void updateByLambdaChain() {
        boolean isUpdate = new LambdaUpdateChainWrapper<>(mpUserMapper)
                .eq(MPUser::getName, "王渊").eq(MPUser::getAge, 31).set(MPUser::getAge, 32).update();
        System.out.println("更新是否成：" + isUpdate);
    }
}
