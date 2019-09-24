package com.yuan.delete;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yuan.dao.MPUserMapper;
import com.yuan.entity.MPUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DeleteTest {

    @Autowired
    private MPUserMapper mpUserMapper;

    @Test
    public void deleteById() {
        int rows = mpUserMapper.deleteById(1);
        System.out.println("删除条数：" + rows);
    }

    @Test
    public void deleteByMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "aaa");
        map.put("age", 20);
        int rows = mpUserMapper.deleteByMap(map);
        System.out.println("删除条数：" + rows);
    }

    @Test
    public void deleteByIds() {
        int rows = mpUserMapper.deleteBatchIds(Arrays.asList(1, 2, 3));
        System.out.println("删除条数：" + rows);
    }

    @Test
    public void deleteByWrapper() {
        LambdaQueryWrapper<MPUser> lqw = new LambdaQueryWrapper<>();
        lqw.eq(MPUser::getAge, 44).or().eq(MPUser::getAge, 55);
        int rows = mpUserMapper.delete(lqw);
        System.out.println("删除条数：" + rows);
    }

}
