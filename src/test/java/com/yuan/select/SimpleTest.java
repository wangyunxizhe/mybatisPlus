package com.yuan.select;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuan.dao.MPUserMapper;
import com.yuan.entity.MPUser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SimpleTest {

    @Autowired
    private MPUserMapper mpUserMapper;

    @Test
    public void select() {
        //不加条件，查询全部
        List<MPUser> users = mpUserMapper.selectList(null);
        Assert.assertNotNull(users);
        users.forEach(System.out::println);
    }

    @Test
    public void insert() {
        MPUser mpUser = new MPUser();
        mpUser.setName("王渊");
        mpUser.setAge(32);
        mpUser.setManagerId(1088248166370832385L);
        mpUser.setCreateTime(LocalDateTime.now());
        int rows = mpUserMapper.insert(mpUser);
        System.out.println("影响行数为：" + rows);
    }

    @Test
    public void selectByIds() {
        List<Long> ids = Arrays.asList(1088248166370832385L, 1088250446457389058L, 1094590409767661570L);
        List<MPUser> users = mpUserMapper.selectBatchIds(ids);
        users.forEach(System.out::println);
    }

    @Test
    public void selectByMap() {
        Map<String, Object> whereAnd = new HashMap<>();
        //注意：map中的key指的是数据库中的列名，而不是属性名
        whereAnd.put("name", "王渊");
        whereAnd.put("age", 32);
        List<MPUser> users = mpUserMapper.selectByMap(whereAnd);
        users.forEach(System.out::println);
    }

    /**
     * 默认全字段查询
     */
    @Test
    public void selectByWrapper() {
        //查询条件1：名字中包含“雨”，年龄小于40
        System.out.println("查询条件1：名字中包含“雨”，年龄小于40===》");
        QueryWrapper<MPUser> qw = new QueryWrapper<>();
        //同上个测试方法：key为列名而非变量名
        qw.like("name", "雨").lt("age", 40);
        List<MPUser> users = mpUserMapper.selectList(qw);
        users.forEach(System.out::println);

        //查询条件2：名字中包含“雨”，年龄大于等于20，小于等于40，邮箱不为空
        System.out.println("查询条件2：名字中包含“雨”，年龄大于等于20，小于等于40，邮箱不为空===》");
        qw = new QueryWrapper<>();
        qw.like("name", "雨").between("age", 20, 40).isNotNull("email");
        users = mpUserMapper.selectList(qw);
        users.forEach(System.out::println);

        //查询条件3：姓“王”或者年龄大于等于25，按年龄降序排列，若年龄相同则按id升序排列
        System.out.println("查询条件3：姓“王”或者年龄大于等于25，按年龄降序排列，若年龄相同则按id升序排列===》");
        qw = new QueryWrapper<>();
        qw.likeRight("name", "王").or().ge("age", 25)
                .orderByDesc("age").orderByAsc("id");
        users = mpUserMapper.selectList(qw);
        users.forEach(System.out::println);

        //查询条件4：创建日期为2019-2-14并且直属上级姓王
        System.out.println("查询条件4：创建日期为2019-2-14并且直属上级姓王===》");
        qw = new QueryWrapper<>();
        //apply语法可参照官方文档，inSql代表manager_id in (select xxx from ....)
        qw.apply("date_format(create_time,'%Y-%m-%d') = {0}", "2019-02-14")
                .inSql("manager_id", "select id from t_mp_user where name like '王%'");
        users = mpUserMapper.selectList(qw);
        users.forEach(System.out::println);

        //查询条件5：姓王并且（年龄小于40或者邮箱不为空）
        System.out.println("查询条件5：姓王并且（年龄小于40或者邮箱不为空）===》");
        qw = new QueryWrapper<>();
        //使用函数式接口
        qw.likeRight("name", "王")
                .and(qwr -> qwr.lt("age", 40).or().isNotNull("email"));
        users = mpUserMapper.selectList(qw);
        users.forEach(System.out::println);

        //查询条件6：姓王或者（年龄小于40并且大于20，并且邮箱不为空）
        System.out.println("查询条件6：姓王或者（年龄小于40并且大于20，并且邮箱不为空）===》");
        qw = new QueryWrapper<>();
        //使用函数式接口
        qw.likeRight("name", "王")
                .or(qwr -> qwr.lt("age", 40).gt("age", 20).isNotNull("email"));
        users = mpUserMapper.selectList(qw);
        users.forEach(System.out::println);

        //查询条件7：（年龄小于40或者邮箱不为空）并且姓王
        System.out.println("查询条件7：（年龄小于40或者邮箱不为空）并且姓王===》");
        qw = new QueryWrapper<>();
        //使用函数式接口
        qw.nested(qwr -> qwr.lt("age", 40).or().isNotNull("email"))
                .likeRight("name", "王");
        users = mpUserMapper.selectList(qw);
        users.forEach(System.out::println);

        //查询条件8：年龄为30，31，34，35
        System.out.println("查询条件8：年龄为30，32，34，35===》");
        qw = new QueryWrapper<>();
        qw.in("age", Arrays.asList(30, 31, 32, 35));
        users = mpUserMapper.selectList(qw);
        users.forEach(System.out::println);

        //查询条件9：只返回满足条件的其中一条结果即可
        System.out.println("查询条件8：只返回满足条件的其中一条结果即可===》");
        qw = new QueryWrapper<>();
        qw.in("age", Arrays.asList(30, 31, 32, 35)).last("limit 1");
        users = mpUserMapper.selectList(qw);
        users.forEach(System.out::println);
    }

    /**
     * 只查询需要显示的字段
     */
    @Test
    public void selectByWrapperSuper() {
        //查询条件1：名字中包含“雨”，年龄小于40
        System.out.println("查询条件1：名字中包含“雨”，年龄小于40===》");
        QueryWrapper<MPUser> qw = new QueryWrapper<>();
        qw.select("id", "name").like("name", "雨").lt("age", 40);
        List<MPUser> users = mpUserMapper.selectList(qw);
        users.forEach(System.out::println);

        //当需要显示的字段过多时
        System.out.println("当需要显示的字段过多时===》");
        qw = new QueryWrapper<>();
        qw.select(MPUser.class, info -> !info.getColumn().equals("create_time")
                && !info.getColumn().equals("manager_id"))//不显示create_time，manager_id，其余列都显示
                .like("name", "雨").lt("age", 40);
        users = mpUserMapper.selectList(qw);
        users.forEach(System.out::println);
    }

    /**
     * 带校验的条件查询
     */
    @Test
    public void selectByCondition() {
        String name = "王";
        String email = "";
        QueryWrapper<MPUser> qw = new QueryWrapper<>();
        qw.like(!StringUtils.isEmpty(name), "name", name)
                .like(!StringUtils.isEmpty(email), "email", email);
        List<MPUser> users = mpUserMapper.selectList(qw);
        users.forEach(System.out::println);
    }

    @Test
    public void selectByWrapperEntity() {
        MPUser whereUser = new MPUser();
        whereUser.setEmail("wtf");
        whereUser.setAge(32);
        QueryWrapper<MPUser> qw = new QueryWrapper<>(whereUser);
        //还可以继续写查询条件，互不影响，都成立
        //qw.like("name","王").lt("age",40);
        List<MPUser> users = mpUserMapper.selectList(qw);
        users.forEach(System.out::println);
    }

    @Test
    public void selectByWrapperAlleq() {
        QueryWrapper<MPUser> qw = new QueryWrapper<>();
        Map<String, Object> params = new HashMap<>();
        params.put("name", "王渊");
        params.put("age", 32);
        qw.allEq(params, false);//false表示入参中如果有值为null的，就忽略掉，不加入sql
        List<MPUser> users = mpUserMapper.selectList(qw);
        users.forEach(System.out::println);
    }

    /**
     * selectMaps以键值对形式返回结果
     */
    @Test
    public void selectByWrapperMaps() {
        //查询条件1
        System.out.println("查询条件1===》");
        QueryWrapper<MPUser> qw = new QueryWrapper<>();
        qw.select("id", "name").like("name", "雨").lt("age", 40);
        List<Map<String, Object>> users = mpUserMapper.selectMaps(qw);
        users.forEach(System.out::println);

        //查询条件2：按直属上级分组，查询每组的平均年龄，最大年龄，最小年龄。并且只取年龄总和小于500的组
        System.out.println("查询条件2：按直属上级分组，查询每组的平均年龄，" +
                "最大年龄，最小年龄。并且只取年龄总和小于500的组===》");
        qw = new QueryWrapper<>();
        qw.select("avg(age) avg_age", "min(age) min_age", "max(age) max_age")
                .groupBy("manager_id").having("sum(age)<{0}", 500);
        users = mpUserMapper.selectMaps(qw);
        users.forEach(System.out::println);
    }

    /**
     * 结果只返回1列（第一列），注意是使用场景
     */
    @Test
    public void selectByWrapperObjs() {
        QueryWrapper<MPUser> qw = new QueryWrapper<>();
        qw.select("id", "name").like("name", "雨").lt("age", 40);
        List<Object> users = mpUserMapper.selectObjs(qw);
        users.forEach(System.out::println);
    }

    @Test
    public void selectByWrapperCount() {
        QueryWrapper<MPUser> qw = new QueryWrapper<>();
        qw.like("name", "王").lt("age", 40);
        Integer count = mpUserMapper.selectCount(qw);
        System.out.println("总记录数：" + count);
    }

    /**
     * selectOne方法只返回一条记录，也可以是空。但结果大于1条会报错
     */
    @Test
    public void selectByWrapperOne() {
        QueryWrapper<MPUser> qw = new QueryWrapper<>();
        qw.like("name", "王渊").lt("age", 40);
        MPUser user = mpUserMapper.selectOne(qw);
        System.out.println(user);
    }

}
