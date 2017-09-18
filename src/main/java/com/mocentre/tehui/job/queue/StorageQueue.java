/*
 * Copyright 2017 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.job.queue;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

import com.mocentre.tehui.common.utils.LoggerUtil;

/**
 * 类StorageQueue.java的实现描述：库存队列
 * 
 * @author sz.gong 2017年3月9日 上午10:08:27
 */
public class StorageQueue<T> implements InitializingBean, DisposableBean {

    private RedisTemplate                  redisTemplate;
    private int                            cap  = Short.MAX_VALUE;    //最大阻塞的容量，超过容量将会导致清空旧数据
    private byte[]                         rawKey;
    private RedisConnectionFactory         factory;
    private RedisConnection                connection;
    private BoundListOperations<String, T> listOperations;
    private String                         key;
    private Lock                           lock = new ReentrantLock(); //基于底层IO阻塞考虑  
    private ThreadPoolManage               threadPool;
    private Thread                         listenerThread;

    @Override
    public void afterPropertiesSet() throws Exception {
        factory = redisTemplate.getConnectionFactory();
        connection = RedisConnectionUtils.getConnection(factory);
        rawKey = redisTemplate.getKeySerializer().serialize(key);
        listOperations = redisTemplate.boundListOps(key);
        threadPool = new ThreadPoolManage();
        listenerThread = new ListenerThread();
        listenerThread.setDaemon(true);
        listenerThread.start();
    }

    @SuppressWarnings("unchecked")
    public T takeFromTail(int timeout) throws InterruptedException {
        lock.lockInterruptibly();
        try {
            List<byte[]> results = connection.bRPop(timeout, rawKey);
            if (CollectionUtils.isEmpty(results)) {
                return null;
            }
            return (T) redisTemplate.getValueSerializer().deserialize(results.get(1));
        } finally {
            lock.unlock();
        }
    }

    public T takeFromTail() throws InterruptedException {
        return takeFromTail(0);
    }

    @SuppressWarnings("unchecked")
    public T takeFromHead(int timeout) throws InterruptedException {
        lock.lockInterruptibly();
        try {
            List<byte[]> results = connection.bLPop(timeout, rawKey);
            if (CollectionUtils.isEmpty(results)) {
                return null;
            }
            return (T) redisTemplate.getValueSerializer().deserialize(results.get(1));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return null;
    }

    public T takeFromHead() throws InterruptedException {
        return takeFromHead(0);
    }

    public void pushFromHead(T value) {
        listOperations.leftPush(value);
    }

    public void pushFromTail(T value) {
        listOperations.rightPush(value);
    }

    public T removeFromHead() {
        return listOperations.leftPop();
    }

    public T removeFromTail() {
        return listOperations.rightPop();
    }

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setKey(String key) {
        this.key = key;
    }

    class ListenerThread extends Thread {

        @Override
        public void run() {
            try {
                while (true) {
                    T value = takeFromHead();
                    if (value != null) {
                        StorageMsgVo msg = (StorageMsgVo) value;
                        threadPool.process(msg);
                    } else {
                        sleep(1000);
                    }
                }
            } catch (Exception e) {
                LoggerUtil.tehuiLog.error("Storage ListenerThread close", e);
                e.printStackTrace();
            }
        }
    }

    @Override
    public void destroy() throws Exception {
        shutdown();
        RedisConnectionUtils.releaseConnection(connection, factory);
    }

    private void shutdown() {
        try {
            listenerThread.interrupt();
            threadPool.destory();
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("Storage 关闭线程", e);
        }
    }

}
