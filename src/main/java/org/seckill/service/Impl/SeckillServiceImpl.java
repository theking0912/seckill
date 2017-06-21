package org.seckill.service.Impl;

import org.seckill.dao.SeckillDao;
import org.seckill.dao.SuccessKilledDao;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.entity.SuccessKilled;
import org.seckill.enums.SeckillStatEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by theking on 2017/1/15.
 */
//@Dao��@Service��Controller
// @Component:��������ע�⣬����֪����Service��Controller��ʱ�����ʹ�ô�ע��
//@Controller   @Service    @Dao
@Service
public class SeckillServiceImpl implements SeckillService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //ע��Service����
    @Autowired //@Resource , @Inject
    private SeckillDao seckillDao;

    @Autowired
    private SuccessKilledDao successKilledDao;

    //md5��ֵ�ַ��������ڻ���md5
    private final String salt = "234^*&$%#^*&asdh#^*jah*&^$%$#&#$%^eba";

    //��ѯ����
    @Override
    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0, 4);
    }

    //ͨ��ID��ѯ
    @Override
    public Seckill getById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    //��¶��ɱ�ӿ�
    @Override
    public Exposer exportSeckillUrl(long seckillId) {
        Seckill seckill = seckillDao.queryById(seckillId);
        //���ͨ��ID��ѯ��������򷵻ش˽��
        if (seckill == null) {
            return new Exposer(false, seckillId);
        }
        //�����ѯ���˽������Ҫ��ȡ����¼�и�����ɱ��ϸ�еĿ�ʼʱ��
        //�����ɱδ��ʼ������������,���ڵ����Ϊ����ǰʱ�䲻����ɱʱ�䷶Χ��
        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        Date nowTime = new Date();
        if (nowTime.getTime() < startTime.getTime()
                || nowTime.getTime() > endTime.getTime()) {
            return new Exposer(false, seckillId, nowTime.getTime(), startTime.getTime(), endTime.getTime());
        }
        //ת���ض��ַ����Ĺ��̣������ص��ǲ�����
        String md5 = getMD5(seckillId);
        return new Exposer(true, md5, seckillId);
    }

    //��ȡmd5����
    private String getMD5(long seckillId) {
        String base = seckillId + "/" + salt;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    //ִ����ɱ
    @Override
    @Transactional
    /**
     * ʹ��ע��������񷽷����е㣺
     * 1�������ŶӴ��һ��Լ������ȷ��ע���񷽷��ı�̷��
     * 2����֤���񷽷�ִ�е�ʱ�価���̣ܶ���Ҫ�������������������RPC/HTTP������߰��뵽���񷽷��ⲿ��
     * 3���������еķ�������Ҫ������ֻ��һ���޸Ĳ���������ֻ����������Ҫ�������
     *
     */
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillException, RepeatKillException, SeckillCloseException {
        if (md5 == null || !md5.equals(getMD5(seckillId))) {
            throw new SeckillException("seckill data rewrite");
        }
        //ִ����ɱ�߼�������� + ��¼������Ϊ
        Date nowTime = new Date();

        try {
            //�����
            int updateCount = seckillDao.reduceNumber(seckillId, nowTime);
            if (updateCount <= 0) {
                //û�и��µ���¼
                throw new SeckillCloseException("seckill is closed");
            } else {
                //�����ɹ�����¼������Ϊ
                int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
                //��Ϊֻ����ɱһ�Σ�ͨ��seckillId��userPhone��ȷ��Ψһһ��ֵ������ظ���ɱ��Ϊ��Dao���е�SQLʹ�õ���ignore������Ҫ�������ѡ�
                if (insertCount <= 0) {
                    //�ظ���ɱ
                    throw new RepeatKillException("seckill repeated");
                } else {
                    //��ɱ�ɹ�
                    SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
                    return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS, successKilled);
                }
            }

        } catch (SeckillCloseException e1) {
            throw e1;
        } catch (RepeatKillException e2) {
            throw e2;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            //���еı������쳣ת��Ϊ�������쳣
            throw new SeckillException("seckill inner error" + e.getMessage());
        }
    }
}
