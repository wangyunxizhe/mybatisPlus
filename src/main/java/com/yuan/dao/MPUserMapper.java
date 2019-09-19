package com.yuan.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yuan.entity.MPUser;

/**
 * 如果要使用mybatis-plus中的内置方法，就需要继承BaseMapper，其中的泛型就是需要操作的实体类（表）
 */
public interface MPUserMapper extends BaseMapper<MPUser>{
}
