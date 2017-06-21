package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.SuccessKilled;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * 配置spring和junit整合,junit启动时加载springIOC容器
 * spring-test,junit
 * Created by theking on 2017/1/7.
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledDaoTest {

    @Resource
    private SuccessKilledDao successKilledDao;

    @Test
    public void insertSuccessKilled() throws Exception {
        long id = 1001;
        long phone = 18518411040L;
        int insertCount = successKilledDao.insertSuccessKilled(id, phone);
        System.out.println("insertCount=" + insertCount);
        /**
         * 16:59:47.401 [main] DEBUG o.m.s.t.SpringManagedTransaction - JDBC Connection [com.mchange.v2.c3p0.impl.NewProxyConnection@45d84a20]
         * will not be managed by Spring
         16:59:47.406 [main] DEBUG o.s.d.S.insertSuccessKilled - ==>
         Preparing:
         insert ignore into success_killed(seckill_id,user_phone) values(?,?)
         16:59:47.476 [main] DEBUG o.s.d.S.insertSuccessKilled - ==>
         Parameters:
         1000(Long), 18518411040(Long)
         16:59:47.612 [main] DEBUG o.s.d.S.insertSuccessKilled - <==
         第一次执行：Updates: 1 insertCount=1
         第二次执行：Updates: 0 insertCount=1
         */
    }

    @Test
    public void queryByIdWithSeckill() throws Exception {
        long id = 1001;
        long phone = 18518411040L;
        SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(id, phone);
        System.out.println(successKilled);
        System.out.println(successKilled.getSeckill());
        /**
         * 17:18:39.035 [main] DEBUG o.m.s.t.SpringManagedTransaction - JDBC Connection [com.mchange.v2.c3p0.impl.NewProxyConnection@536f2a7e] will not be managed by Spring
         17:18:39.044 [main] DEBUG o.s.d.S.queryByIdWithSeckill - ==>  Preparing: select sk.seckill_id, sk.user_phone, sk.create_time, sk.state, s.seckill_id "seckill.seckill_id", s.name "seckill.name", s.number "seckill.number", s.end_time "seckill.end_time", s.start_time "seckill.start_time", s.create_time "seckill.create_time" from success_killed sk inner join seckill s on sk.seckill_id = s.seckill_id where sk.seckill_id = ? and sk.user_phone = ?
         17:18:39.121 [main] DEBUG o.s.d.S.queryByIdWithSeckill - ==> Parameters: 1000(Long), 18518411040(Long)
         17:18:39.144 [main] DEBUG o.s.d.S.queryByIdWithSeckill - <==      Total: 1
         17:18:39.148 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@72035809]
         SuccessKilled{seckillId=1000,
         userPhone=18518411040,
         state=-1,
         createTime=Sat Jan 07 16:59:47 CST 2017}

         Seckill{seckillId=1000,
         name='1000秒杀iphone6',
         number=100,
         startTime=Sun Nov 01 00:00:00 CST 2015,
         endTime=Mon Nov 02 00:00:00 CST 2015,
         createTime=Sun Sep 04 02:22:14 CST 2016}
         */
        /**
         * SuccessKilled{seckillId=1001,
         * userPhone=18518411040,
         * state=0,
         * createTime=Sat Jan 07 17:24:34 CST 2017}
         *
         Seckill{seckillId=1001,
         name='500秒杀ipad2',
         number=200,
         startTime=Sun Nov 01 00:00:00 CST 2015,
         endTime=Mon Nov 02 00:00:00 CST 2015,
         createTime=Sun Sep 04 02:22:14 CST 2016}
         */
    }

}