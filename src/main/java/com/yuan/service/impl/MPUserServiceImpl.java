package com.yuan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuan.dao.MPUserMapper;
import com.yuan.entity.MPUser;
import com.yuan.service.MPUserService;
import org.springframework.stereotype.Service;

/**
 * 通用Service实现类
 * 注意：不是直接implements，而是先extends指定规则
 */
@Service
public class MPUserServiceImpl extends ServiceImpl<MPUserMapper, MPUser> implements MPUserService{
}
