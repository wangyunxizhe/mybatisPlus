package com.yuan.tx;

import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TxServiceTest {

    @Autowired
    private TxService txService;

    @Test
    public void setDataInSameTX() {
        txService.setDataInSameTX();
    }

    @Test
    public void setDataInDiffTX() throws InterruptedException {
        //测试在事务一对数据A进行更改的同时（但并未完成事务提交），事务二也对数据A进行更改
        final CountDownLatch latch = new CountDownLatch(1);
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                try {
                    txService.setDataInTX1();
                    latch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();
        Thread.sleep(1000);
        Thread t2 = new Thread(new Runnable() {
            public void run() {
                txService.setDataInTX2();
                latch.countDown();
            }
        });
        t2.start();
        latch.await();
    }

}