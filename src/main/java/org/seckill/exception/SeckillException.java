package org.seckill.exception;

/**
 * ��ɱ���ҵ���쳣
 * Created by theking on 2017/1/13.
 */
public class SeckillException extends RuntimeException {

    public SeckillException(String message) {
        super(message);
    }

    public SeckillException(String message, Throwable cause) {
        super(message, cause);
    }
}