package com.yuan.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuan.entity.MPUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 如果要使用mybatis-plus中的内置方法，就需要继承BaseMapper，其中的泛型就是需要操作的实体类（表）
 */
public interface MPUserMapper extends BaseMapper<MPUser> {

    /**
     * 加入一个使用自定义sql的方法
     */
    //这里的“ew.”实际上就是Constants.WRAPPER的值，自定义的sql将添加在占位符中，示例见CustomSqlTest
    //注意：customSqlSegment是固定写法，不能自定义
    @Select("select * from t_mp_user ${ew.customSqlSegment}")
    List<MPUser> selectAllDIY(@Param(Constants.WRAPPER) Wrapper<MPUser> wrapper);

    /**
     * 使用类似mybatis xml文件的方式
     */
    List<MPUser> selectAllByXML(@Param(Constants.WRAPPER) Wrapper<MPUser> wrapper);

    /**
     * 自定义分页（适用于联表查询时的分页，因为可以自己写sql）
     */
    IPage<MPUser> selectMyPage(Page<MPUser> page, @Param(Constants.WRAPPER) Wrapper<MPUser> wrapper);

}
