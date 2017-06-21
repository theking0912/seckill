package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.Seckill;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * 配置spring和junit整合,junit启动时加载springIOC容器
 * spring-test,junit
 * Created by theking on 2017/1/3.
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {

    //注入Dao实现类依赖
    @Resource
    private SeckillDao seckillDao;

    @Test
    public void testQueryById() throws Exception {
        long id = 1000;
        Seckill seckill = seckillDao.queryById(id);
        System.out.println(seckill.getName());
        System.out.println(seckill);
        /** 输出结果
         * 1000秒杀iphone6
         Seckill{seckillId=1000,
         name='1000秒杀iphone6',
         number=100,
         startTime=Sun Nov 01 00:00:00 CST 2015,
         endTime=Mon Nov 02 00:00:00 CST 2015,
         createTime=Sun Sep 04 02:22:14 CST 2016}
         */
    }

    @Test
    public void testQueryAll() throws Exception {
        /** 报错
         * Caused by: org.apache.ibatis.binding.BindingException:
         * Parameter 'offset' not found. Available parameters are [0, 1, param1, param2]
         */
        //List<Seckill> queryAll(int offet,int limit);
        //java没有保存形参的记录：queryAll(int offet,int limit) （会用对应的arg0,arg1来取代前面的，offet，limit）->queryAll(arg0,arg01);
        //当有多个参数的时候需要告诉mybatis具体绑定的是哪个值
        //解决方法:在Dao中的参数前面添加注解
        List<Seckill> seckills = seckillDao.queryAll(0, 100);
        for (Seckill seckill : seckills) {
            System.out.println(seckill);
        }
        /** 输出结果
         * Seckill{seckillId=1000, name='1000秒杀iphone6', number=100, startTime=Sun Nov 01 00:00:00 CST 2015, endTime=Mon Nov 02 00:00:00 CST 2015, createTime=Sun Sep 04 02:22:14 CST 2016}
         Seckill{seckillId=1001, name='500秒杀ipad2', number=200, startTime=Sun Nov 01 00:00:00 CST 2015, endTime=Mon Nov 02 00:00:00 CST 2015, createTime=Sun Sep 04 02:22:14 CST 2016}
         Seckill{seckillId=1002, name='300秒杀小米4', number=300, startTime=Sun Nov 01 00:00:00 CST 2015, endTime=Mon Nov 02 00:00:00 CST 2015, createTime=Sun Sep 04 02:22:14 CST 2016}
         Seckill{seckillId=1003, name='200秒杀红米note', number=400, startTime=Sun Nov 01 00:00:00 CST 2015, endTime=Mon Nov 02 00:00:00 CST 2015, createTime=Sun Sep 04 02:22:14 CST 2016}
         */
    }

    @Test
    public void testReduceNumber() throws Exception {
        /** mybatis的日志
         * --意思是这个JDBC没有被Spring所托管，而是从C3P0拿到的。没有托管的原因是：声明式事务（未解释）
         * 16:39:01.763 [main] DEBUG o.m.s.t.SpringManagedTransaction -
         * JDBC Connection [com.mchange.v2.c3p0.impl.NewProxyConnection@25be7b63]
         * will not be managed by Spring
         16:39:01.770 [main] DEBUG o.s.dao.SeckillDao.reduceNumber - ==>
         ----Preparing:准备工作
         UPDATE seckill set number = number - 1
         where seckill_id = ?
         and start_time <= ?
         and end_time >= ?
         and number > 0
         16:39:01.868 [main] DEBUG o.s.dao.SeckillDao.reduceNumber - ==>
         ----Parameters:传入参数
         1000(Long),
         2017-01-07 16:39:01.413(Timestamp),
         2017-01-07 16:39:01.413(Timestamp)
         16:39:01.955 [main] DEBUG o.s.dao.SeckillDao.reduceNumber - <==
         ----Updates: 返回参数
         0
         */
        Date killTime = new Date();
        int updateCount = seckillDao.reduceNumber(1000L, killTime);
        System.out.println("updateCount=" + updateCount);
    }

}