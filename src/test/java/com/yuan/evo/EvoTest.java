package com.yuan.evo;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yuan.dao.MPUserEvoMapper;
import com.yuan.entity.MPUserEvo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EvoTest {

    @Autowired
    private MPUserEvoMapper mpUserEvoMapper;

    /**
     * 配置逻辑删除后，该id的记录并未被真的删除
     */
    @Test
    public void deleteById() {
        int rows = mpUserEvoMapper.deleteById(1094592041087729666L);
        System.out.println("删除条数：" + rows);
        //查询时也不会查出逻辑已删除的数据，更新同理
        List<MPUserEvo> users = mpUserEvoMapper.selectList(null);
        users.forEach(System.out::println);
    }

    /**
     * 使用自定义sql，就不能享受配置化的逻辑删除，必须要自己手动写
     */
    @Test
    public void mySelect() {
        List<MPUserEvo> users = mpUserEvoMapper.mySelectList(
                Wrappers.<MPUserEvo>lambdaQuery().gt(MPUserEvo::getAge, 25));
        users.forEach(System.out::println);
    }

    /**
     * 自动填充测试
     */
    @Test
    public void fillTest() {
        MPUserEvo user = new MPUserEvo();
        user.setName("路飞");
        user.setAge(20);
        user.setEmail("xxxxx");
        int row = mpUserEvoMapper.insert(user);
        System.out.println("影响的行数：" + row);
        MPUserEvo user2 = new MPUserEvo();
        user.setId(1094592041087729666L);
        user.setAge(20);
        int row2 = mpUserEvoMapper.updateById(user2);
        System.out.println("影响的行数：" + row2);
    }

    /**
     * 乐观锁测试
     * 注意：mp官方文档中，乐观锁只在updateById和update中有用，其余情况需要自己写
     */
    @Test
    public void versionTest() {
        LambdaQueryWrapper<MPUserEvo> lqw = new LambdaQueryWrapper<>();
        lqw.eq(MPUserEvo::getName, "刘红雨");
        MPUserEvo user = mpUserEvoMapper.selectOne(lqw);
        Integer version = user.getVersion();
        MPUserEvo user1 = new MPUserEvo();
        user1.setId(1094592041087729666L);
        user1.setName("刘红雨");
        user1.setVersion(version);
        int row = mpUserEvoMapper.updateById(user1);
        System.out.println("影响的行数：" + row);
    }

}
