package com.yuan.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * t_mp_user的实体类
 */
@Data//该注解在编译时会自动加入get/set方法
@TableName(value = "t_mp_user")
public class MPUser {

    private Long id;
    private String name;
    private Integer age;
    private String email;
    private Long managerId;//直属上级id
    private LocalDateTime createTime;

}
