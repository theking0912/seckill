package org.seckill.exception;

/**
 * ��ɱ�ر��쳣
 * Created by theking on 2017/1/13.
 */
public class SeckillCloseException extends SeckillException {

    public SeckillCloseException(String message) {
        super(message);
    }

    public SeckillCloseException(String message, Throwable cause) {
        super(message, cause);
    }
}
