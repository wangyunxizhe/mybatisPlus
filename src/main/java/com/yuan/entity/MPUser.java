package com.yuan.entity;

import com.baomidou.mybatisplus.annotation.SqlCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * t_mp_user的实体类
 */
@Data//该注解在编译时会自动加入get/set方法
@TableName(value = "t_mp_user")
public class MPUser {

    @TableId//该注解标明主键
    private Long id;

    @TableField("name")//明确指明与数据库表的对应列名
    private String name;

    private Integer age;

    //当使用QueryWrapper注入该MPUser对象的方式查询时，sql会自动解析成 where email like '%XXX%'
    //示例：见SimpleTest->selectByWrapperEntity方法
    //也可以模仿源码的写法，直接写自定义的条件，如condition ="%s LIKE CONCAT(#{%s},'%%')"
    @TableField(condition = SqlCondition.LIKE)
    private String email;

    private Long managerId;//直属上级id

    private LocalDateTime createTime;

    @TableField(exist = false)//表示该属性不是数据库中的表字段，没有映射关系
    private String remark;//备注。注意：该属性不存在于数据库中

}
