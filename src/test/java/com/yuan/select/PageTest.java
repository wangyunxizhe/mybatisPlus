package com.yuan.select;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuan.dao.MPUserMapper;
import com.yuan.entity.MPUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PageTest {

    @Autowired
    private MPUserMapper mpUserMapper;

    @Test
    public void selectPage() {
        QueryWrapper<MPUser> qw = new QueryWrapper<>();
        qw.ge("age", 26);
        //参数1：当前页数。参数2：每页几条
        Page<MPUser> page = new Page<>(1, 2);
        IPage<MPUser> iPage = mpUserMapper.selectPage(page, qw);
        System.out.println("总页数：" + iPage.getPages());
        System.out.println("总记录数：" + iPage.getTotal());
        List<MPUser> users = iPage.getRecords();
        users.forEach(System.out::println);
    }

    @Test
    public void selectPageByMap() {
        QueryWrapper<MPUser> qw = new QueryWrapper<>();
        qw.ge("age", 26);
        //参数1：当前页数。参数2：每页几条，参数3：false之后将不会单独执行一条查询总记录数的sql，降低性能损耗
        //可与上个单测执行的sql对比
        Page<MPUser> page = new Page<>(1, 2, false);
        IPage<Map<String, Object>> iPage = mpUserMapper.selectMapsPage(page, qw);
        System.out.println("总页数：" + iPage.getPages());
        System.out.println("总记录数：" + iPage.getTotal());
        List<Map<String, Object>> users = iPage.getRecords();
        users.forEach(System.out::println);
    }

    @Test
    public void selectMyPage() {
        QueryWrapper<MPUser> qw = new QueryWrapper<>();
        qw.ge("age", 26);
        //参数1：当前页数。参数2：每页几条
        Page<MPUser> page = new Page<>(1, 2);
        IPage<MPUser> iPage = mpUserMapper.selectMyPage(page, qw);
        System.out.println("总页数：" + iPage.getPages());
        System.out.println("总记录数：" + iPage.getTotal());
        List<MPUser> users = iPage.getRecords();
        users.forEach(System.out::println);
    }

}
