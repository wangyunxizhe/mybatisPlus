package com.yuan.tx;

import com.yuan.dao.MPUserMapper;
import com.yuan.entity.MPUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TxService {

    @Autowired
    private MPUserMapper mpUserMapper;

    @Transactional
    public void setDataInSameTX() {
        //测试在同一事务中先插入后更新同一条数据
        MPUser mpUser = new MPUser();
        mpUser.setName("B哥");
        mpUser.setAge(20);
        mpUser.setManagerId(1L);
        mpUserMapper.insert(mpUser);
        mpUser.setAge(25);
        mpUserMapper.updateById(mpUser);
    }

    @Transactional
    public void setDataInTX1() throws InterruptedException {
        System.out.println("执行 事务一");
        MPUser mpUser = new MPUser();
        mpUser.setId(1L);
        mpUser.setAge(11);
        mpUserMapper.updateById(mpUser);
        Thread.sleep(60000);
    }

    @Transactional
    public void setDataInTX2() {
        System.out.println("执行 事务二");
        MPUser mpUser = new MPUser();
        mpUser.setId(1L);
        mpUser.setAge(22);
        mpUserMapper.updateById(mpUser);
    }

}
