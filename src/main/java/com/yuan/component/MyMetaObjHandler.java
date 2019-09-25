package com.yuan.component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 加入该Bean后，工程中所有实体在执行增/改操作时，都会被该类预处理，
 * 为避免性能的浪费可在各实现方法中做优化
 */
@Component
public class MyMetaObjHandler implements MetaObjectHandler {

    /**
     * 插入时需要如何自动填充
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        boolean hasSetter = metaObject.hasSetter("createTime");
        if (hasSetter) {//该实体中有createTime属性，再做语句块中的操作
            System.err.println("~~~执行insertFill~~~");
            //这里的createTime是属性名，不是表字段名！！！
            setInsertFieldValByName("createTime", LocalDateTime.now(), metaObject);
        }
    }

    /**
     * 更新时需要如何自动填充
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        //校验是否手动set了值，若set了则以手动set的值为准，若没有set则正常自动填充
        Object val = getFieldValByName("updateTime", metaObject);
        if (val == null) {
            System.err.println("~~~执行updateFill~~~");
            setUpdateFieldValByName("updateTime", LocalDateTime.now(), metaObject);
        }
    }
}
