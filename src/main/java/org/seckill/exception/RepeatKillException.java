package org.seckill.exception;

/**
 * 重复秒杀异常（运行期异常）
 * 异常分：
 * 1、编译期异常：
 * 2、运行期异常：
 * 1）、不需要，手动的try catch，
 * 2）、Spring的声明式事务只接收运行期异常的回滚策略。当抛出一个非运行期异常，是不会帮我们回滚的。
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
