package org.seckill.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by theking on 2017-06-15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"
})
public class SeckillServiceTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @Test
    public void getSeckillList() throws Exception {
        List<Seckill> list = seckillService.getSeckillList();
        logger.info("list={}",list);
    }

    @Test
    public void getById() throws Exception {
        long id = 1000;
        Seckill seckill = seckillService.getById(id);
        logger.info("seckill={}",seckill);
    }

//    @Test
//    public void exportSeckillUrl() throws Exception {
//        long id = 1002;
//        Exposer exposer = seckillService.exportSeckillUrl(id);
//        logger.info("exposer={}",exposer);
//        //exposer=Exposer{
//        // exposed=true,
//        // md5='7b1a7e20c211ad30a625b825f99a3e56',
//        // seckillId=1002, now=0, start=0, end=0}
//    }

//    @Test
//    public void executeSeckill() throws Exception {
//        long id = 1002;
//        long phone = 18518411043L;
//        String md5 = "7b1a7e20c211ad30a625b825f99a3e56";
//        try {
//            SeckillExecution execution = seckillService.executeSeckill(id,phone,md5);
//            logger.info("exposer={}",execution);
//        } catch (RepeatKillException e){
//            logger.error(e.getMessage());
//        } catch (SeckillCloseException e){
//            logger.error(e.getMessage());
//        }
//        //exposer=SeckillExecution{
//        // seckillId=1002,
//        // state=1,
//        // stateInfo='秒杀成功',
//        // successKilled=SuccessKilled{
//        //              seckillId=1002,
//        //              userPhone=18518411043,
//        //              state=0,
//        //              createTime=Thu Jun 15 22:49:17 CST 2017}}
//    }

    //集成测试代码完整逻辑，注意可重复执行
    @Test
    public void seckillLogic(){
        long id = 1003;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        if (exposer.isExposed()){
            logger.info("exposer={}",exposer);
            long phone = 18518411044L;
            String md5 = exposer.getMd5();
            try {
                SeckillExecution execution = seckillService.executeSeckill(id,phone,md5);
                logger.info("exposer={}",execution);
            } catch (RepeatKillException e){
                logger.error(e.getMessage());
            } catch (SeckillCloseException e){
                logger.error(e.getMessage());
            }
            //exposer=SeckillExecution{
            // seckillId=1002,
            // state=1,
            // stateInfo='秒杀成功',
            // successKilled=SuccessKilled{
            //              seckillId=1002,
            //              userPhone=18518411044,
            //              state=0, createTime=Thu Jun 15 23:19:03 CST 2017}}
        } else {
            //秒杀未开启
            //id = 1001
            //exposer = Exposer{
            // exposed=false,
            // md5='null',
            // seckillId=1001,
            // now=1497539850383,
            // start=1485878400000,
            // end=1493568000000}
            logger.warn("exposer = {}",exposer);
        }
    }


}