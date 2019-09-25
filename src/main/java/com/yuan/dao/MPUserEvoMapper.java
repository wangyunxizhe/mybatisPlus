package com.yuan.dao;

import com.baomidou.mybatisplus.annotation.SqlParser;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.yuan.entity.MPUserEvo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 如果要使用mybatis-plus中的内置方法，就需要继承BaseMapper，其中的泛型就是需要操作的实体类（表）
 */
public interface MPUserEvoMapper extends BaseMapper<MPUserEvo> {

    /**
     * 自定义sql测试逻辑删除
     */
    @SqlParser(filter = true)//不使用多租户配置
    @Select("SELECT*FROM t_mp_user_evo ${ew.customSqlSegment} ")
    List<MPUserEvo> mySelectList(@Param(Constants.WRAPPER) Wrapper<MPUserEvo> wrapper);

}
