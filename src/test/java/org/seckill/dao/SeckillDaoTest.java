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
 * ����spring��junit����,junit����ʱ����springIOC����
 * spring-test,junit
 * Created by theking on 2017/1/3.
 */
@RunWith(SpringJUnit4ClassRunner.class)
//����junit spring�����ļ�
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {

    //ע��Daoʵ��������
    @Resource
    private SeckillDao seckillDao;

    @Test
    public void testQueryById() throws Exception {
        long id = 1000;
        Seckill seckill = seckillDao.queryById(id);
        System.out.println(seckill.getName());
        System.out.println(seckill);
        /** ������
         * 1000��ɱiphone6
         Seckill{seckillId=1000,
         name='1000��ɱiphone6',
         number=100,
         startTime=Sun Nov 01 00:00:00 CST 2015,
         endTime=Mon Nov 02 00:00:00 CST 2015,
         createTime=Sun Sep 04 02:22:14 CST 2016}
         */
    }

    @Test
    public void testQueryAll() throws Exception {
        /** ����
         * Caused by: org.apache.ibatis.binding.BindingException:
         * Parameter 'offset' not found. Available parameters are [0, 1, param1, param2]
         */
        //List<Seckill> queryAll(int offet,int limit);
        //javaû�б����βεļ�¼��queryAll(int offet,int limit) �����ö�Ӧ��arg0,arg1��ȡ��ǰ��ģ�offet��limit��->queryAll(arg0,arg01);
        //���ж��������ʱ����Ҫ����mybatis����󶨵����ĸ�ֵ
        //�������:��Dao�еĲ���ǰ�����ע��
        List<Seckill> seckills = seckillDao.queryAll(0, 100);
        for (Seckill seckill : seckills) {
            System.out.println(seckill);
        }
        /** ������
         * Seckill{seckillId=1000, name='1000��ɱiphone6', number=100, startTime=Sun Nov 01 00:00:00 CST 2015, endTime=Mon Nov 02 00:00:00 CST 2015, createTime=Sun Sep 04 02:22:14 CST 2016}
         Seckill{seckillId=1001, name='500��ɱipad2', number=200, startTime=Sun Nov 01 00:00:00 CST 2015, endTime=Mon Nov 02 00:00:00 CST 2015, createTime=Sun Sep 04 02:22:14 CST 2016}
         Seckill{seckillId=1002, name='300��ɱС��4', number=300, startTime=Sun Nov 01 00:00:00 CST 2015, endTime=Mon Nov 02 00:00:00 CST 2015, createTime=Sun Sep 04 02:22:14 CST 2016}
         Seckill{seckillId=1003, name='200��ɱ����note', number=400, startTime=Sun Nov 01 00:00:00 CST 2015, endTime=Mon Nov 02 00:00:00 CST 2015, createTime=Sun Sep 04 02:22:14 CST 2016}
         */
    }

    @Test
    public void testReduceNumber() throws Exception {
        /** mybatis����־
         * --��˼�����JDBCû�б�Spring���йܣ����Ǵ�C3P0�õ��ġ�û���йܵ�ԭ���ǣ�����ʽ����δ���ͣ�
         * 16:39:01.763 [main] DEBUG o.m.s.t.SpringManagedTransaction -
         * JDBC Connection [com.mchange.v2.c3p0.impl.NewProxyConnection@25be7b63]
         * will not be managed by Spring
         16:39:01.770 [main] DEBUG o.s.dao.SeckillDao.reduceNumber - ==>
         ----Preparing:׼������
         UPDATE seckill set number = number - 1
         where seckill_id = ?
         and start_time <= ?
         and end_time >= ?
         and number > 0
         16:39:01.868 [main] DEBUG o.s.dao.SeckillDao.reduceNumber - ==>
         ----Parameters:�������
         1000(Long),
         2017-01-07 16:39:01.413(Timestamp),
         2017-01-07 16:39:01.413(Timestamp)
         16:39:01.955 [main] DEBUG o.s.dao.SeckillDao.reduceNumber - <==
         ----Updates: ���ز���
         0
         */
        Date killTime = new Date();
        int updateCount = seckillDao.reduceNumber(1000L, killTime);
        System.out.println("updateCount=" + updateCount);
    }

}