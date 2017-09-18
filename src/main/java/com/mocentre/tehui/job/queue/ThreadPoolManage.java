/*
 * Copyright 2017 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.job.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.mocentre.tehui.common.utils.LoggerUtil;

/**
 * 类ThreadPoolManage.java的实现描述：线程池
 * 
 * @author sz.gong 2017年3月13日 下午5:22:26
 */
public class ThreadPoolManage {

    private ThreadPoolExecutor pool            = null;

    //线程池维护线程的最小数量
    private static int         corePoolSize    = 1;
    //线程池维护线程的最大数量
    private static int         maximumPoolSize = 3;
    //空闲线程的存活时间
    private static long        keepAliveTime   = 30;
    //时间单位,现有纳秒,微秒,毫秒,秒枚举值
    private TimeUnit           unit            = TimeUnit.MINUTES;

    /**
     * 线程池初始化方法
     * <p>
     * corePoolSize 核心线程池大小----1
     * <p>
     * maximumPoolSize 最大线程池大小----3
     * <p>
     * keepAliveTime 线程池中超过corePoolSize数目的空闲线程最大存活时间----30+单位TimeUnit
     * <p>
     * TimeUnit keepAliveTime 时间单位----TimeUnit.MINUTES
     * <p>
     * workQueue 阻塞队列----new ArrayBlockingQueue<Runnable>(5)====5容量的阻塞队列
     * <p>
     * threadFactory 新建线程工厂----new CustomThreadFactory()====定制的线程工厂
     * <p>
     * rejectedExecutionHandler
     * 当提交任务数超过maxmumPoolSize+workQueue之和时,即当提交第8个任务时(前面线程都没有执行完
     * ),任务会交给RejectedExecutionHandler来处理
     */
    public ThreadPoolManage() {
        pool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit,
                new ArrayBlockingQueue<Runnable>(5), new CustomThreadFactory(), new CustomRejectedExecutionHandler());
    }

    private class CustomThreadFactory implements ThreadFactory {

        private AtomicInteger count = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            String threadName = ThreadPoolManage.class.getSimpleName() + count.addAndGet(1);
            LoggerUtil.tehuiLog.info("newThread:" + threadName);
            //t.setName(threadName);
            return t;
        }
    }

    private class CustomRejectedExecutionHandler implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            try {
                // 核心改造点，由blockingqueue的offer改成put阻塞方法  
                executor.getQueue().put(r);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //运行任务
    public void process(StorageMsgVo storageMsg) {
        pool.execute(new SyncStorageThread(storageMsg));
    }

    //销毁
    public void destory() {
        if (pool != null) {
            pool.shutdownNow();
        }
    }

}
