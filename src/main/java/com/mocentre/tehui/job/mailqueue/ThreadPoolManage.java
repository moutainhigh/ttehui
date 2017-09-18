/*
 * Copyright 2017 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.job.mailqueue;

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

    private static int         corePoolSize    = 1;

    private static int         maximumPoolSize = 2;

    private static long        keepAliveTime   = 30;

    private TimeUnit           unit            = TimeUnit.MINUTES;

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
            return t;
        }
    }

    private class CustomRejectedExecutionHandler implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            try {
                executor.getQueue().put(r);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //运行任务
    public void process(MailMsgVo mallMsg) {
        pool.execute(new SyncMailThread(mallMsg));
    }

    //销毁
    public void destory() {
        if (pool != null) {
            pool.shutdownNow();
        }
    }

}
