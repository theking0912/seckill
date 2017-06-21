package org.seckill.service;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;

import java.util.List;

/**
 * ҵ��ӿڣ�վ�ڡ�ʹ���ߡ��Ƕ���ƽӿ�
 * �������棺
 * 1�������������ȣ�
 * 2��������������ֱ�ӣ���
 * 3���������ͣ�return ���ͣ�һ��Ҫ�Ѻã���Ӧ���ظ�map������entity/�쳣��
 * Created by theking on 2017/1/12.
 */
public interface SeckillService {

    /**
     * ��ѯ������ɱ��¼
     *
     * @return
     */
    List<Seckill> getSeckillList();

    /**
     * ��ѯ������ɱ��¼
     *
     * @param seckillId
     * @return
     */
    Seckill getById(long seckillId);

    /**
     * ��ɱ����ʱ�����ɱ�ӿڵ�ַ��
     * �������ϵͳʱ�����ɱʱ��
     *
     * @param seckillId
     */
    Exposer exportSeckillUrl(long seckillId);

    /**
     * ִ����ɱ
     * ��ִ����ɱ�쳣���������Ҫ�׳�������ӿ�ʹ�÷�
     *
     * @param seckillId
     * @param userPhone
     * @param md5
     */
    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
            throws SeckillException, RepeatKillException, SeckillCloseException;
}
