package org.seckill.dto;

import org.seckill.entity.SuccessKilled;
import org.seckill.enums.SeckillStatEnum;

/**
 * ��װ��ɱִ�к�Ľ����
 * Created by theking on 2017/1/13.
 */
public class SeckillExecution {

    private long seckillId;

    //��ɱִ�н��״̬
    private int state;

    //״̬��ʾ
    private String stateInfo;

    //��ɱ�ɹ���ʱ�򣬷�����ɱ�ɹ��Ķ���
    private SuccessKilled successKilled;

    //��дtoString����

    @Override
    public String toString() {
        return "SeckillExecution{" +
                "seckillId=" + seckillId +
                ", state=" + state +
                ", stateInfo='" + stateInfo + '\'' +
                ", successKilled=" + successKilled +
                '}';
    }

    //��ɱ�ɹ��󷵻صĽ��,��Ҫ������ɱ��ʵ�����
    public SeckillExecution(long seckillId, SeckillStatEnum statEnum, SuccessKilled successKilled) {
        this.seckillId = seckillId;
        this.state = statEnum.getState();
        this.stateInfo = statEnum.getStateInfo();
        this.successKilled = successKilled;
    }

    //��ɱʧ�ܺ󷵻صĽ��
    public SeckillExecution(long seckillId, SeckillStatEnum statEnum) {
        this.seckillId = seckillId;
        this.state = statEnum.getState();
        this.stateInfo = statEnum.getStateInfo();
    }

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public SuccessKilled getSuccessKilled() {
        return successKilled;
    }

    public void setSuccessKilled(SuccessKilled successKilled) {
        this.successKilled = successKilled;
    }
}
