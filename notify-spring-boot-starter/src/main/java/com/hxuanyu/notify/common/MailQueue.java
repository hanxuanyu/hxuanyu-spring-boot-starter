package com.hxuanyu.notify.common;



import com.hxuanyu.notify.model.Mail;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 邮件队列
 *
 * @author 22521
 */
public class MailQueue {
    /**
     * 队列大小
     */
    static final int QUEUE_MAX_SIZE = 1000;

    static BlockingQueue<Mail> blockingQueue = new LinkedBlockingQueue<>(QUEUE_MAX_SIZE);

    /**
     * 私有的默认构造子，保证外界无法直接实例化
     */
    private MailQueue() {
    }


    /**
     * 类级的内部类，也就是静态的成员式内部类，该内部类的实例与外部类的实例
     * 没有绑定关系，而且只有被调用到才会装载，从而实现了延迟加载
     */
    private static class SingletonHolder {
        /**
         * 静态初始化器，由JVM来保证线程安全
         */
        private static final MailQueue MAIL_QUEUE = new MailQueue();
    }

    /**
     * 单例队列
     *
     * @return 队列实例
     */
    public static MailQueue getMailQueue() {
        return SingletonHolder.MAIL_QUEUE;
    }

    /**
     * 生产者入队
     *
     * @param mail 邮件
     * @throws InterruptedException 异常
     */
    public void produce(Mail mail) throws InterruptedException {
        blockingQueue.put(mail);
    }

    /**
     * 消费出队
     *
     * @return 邮件
     * @throws InterruptedException 异常
     */
    public Mail consume() throws InterruptedException {
        return blockingQueue.take();
    }

    /**
     * 获取队列大小
     *
     * @return 队列大小
     */
    public int size() {
        return blockingQueue.size();
    }
}