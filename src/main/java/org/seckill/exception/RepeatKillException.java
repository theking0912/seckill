package org.seckill.exception;

/**
 * �ظ���ɱ�쳣���������쳣��
 * �쳣�֣�
 * 1���������쳣��
 * 2���������쳣��
 * 1��������Ҫ���ֶ���try catch��
 * 2����Spring������ʽ����ֻ�����������쳣�Ļع����ԡ����׳�һ�����������쳣���ǲ�������ǻع��ġ�
 * Created by theking on 2017/1/13.
 */
public class RepeatKillException extends SeckillException {

    public RepeatKillException(String message) {
        super(message);
    }

    public RepeatKillException(String message, Throwable cause) {
        super(message, cause);
    }
}
