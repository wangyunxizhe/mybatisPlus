package com.yuan.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * t_mp_user_evo的实体类
 */
@Data//该注解在编译时会自动加入get/set方法
@TableName(value = "t_mp_user_evo")
public class MPUserEvo {

    @TableId
    private Long id;

    private String name;

    private Integer age;

    private String email;

    private Long managerId;//直属上级id

    @TableField(fill = FieldFill.INSERT)//执行插入时，自动填充该字段，具体逻辑写在MyMetaObjHandler
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.UPDATE)//执行更新时，自动填充该字段，具体逻辑写在MyMetaObjHandler
    private LocalDateTime updateTime;

    @Version//乐观锁注解
    private Integer version;

    @TableLogic//逻辑删除注解。注意3.1.3以下版本，需要先完成bean的注册再使用该注解，详见MybatisPlusEvoConfig
    @TableField(select = false)//查询时不查询该字段
    private Integer deleted;//做假删除用：0 未删，1 已删
}
