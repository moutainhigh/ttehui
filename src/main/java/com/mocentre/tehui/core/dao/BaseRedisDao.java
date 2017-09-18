/*
 * Copyright 2017 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.core.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.Assert;

import com.mocentre.tehui.core.model.BaseEntity;

/**
 * 类BaseRedisDao.java的实现描述：缓存Dao
 * 
 * @author sz.gong 2017年1月12日 下午7:27:18
 */
public abstract class BaseRedisDao<T extends BaseEntity, K, V> extends BaseDao<T> {

    /**
     * 日志记录
     */
    private Logger                logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected RedisTemplate<K, V> redisTemplate;

    /**
     * 缓存value操作
     * 
     * @param key
     * @param value
     * @param time
     * @return
     */
    protected boolean cacheValue(K key, V value, long time) {
        try {
            ValueOperations<K, V> valueOps = redisTemplate.opsForValue();
            valueOps.set(key, value);
            if (time > 0)
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            return true;
        } catch (Throwable e) {
            logger.error("缓存[" + key + "]失败, value[" + value + "]", e);
        }
        return false;
    }

    /**
     * 缓存value操作
     * 
     * @param key
     * @param value
     * @return
     */
    protected boolean cacheValue(K key, V value) {
        return cacheValue(key, value, -1);
    }

    /**
     * 缓存value，自增操作
     * 
     * @param key
     * @param value
     * @return
     */
    protected boolean cacheValueIncr(K key, long value) {
        try {
            Long count = redisTemplate.opsForValue().increment(key, value);
            if (count <= 0) {
                return false;
            }
            return true;
        } catch (Exception e) {
            logger.error("更新缓存[" + key + "]失败," + e);
            return false;
        }
    }

    /**
     * 获取缓存
     * 
     * @param key
     * @return
     */
    protected V getCacheValue(K key) {
        try {
            ValueOperations<K, V> valueOps = redisTemplate.opsForValue();
            return valueOps.get(key);
        } catch (Throwable e) {
            logger.error("获取缓存失败key[" + key + "], error[" + e + "]");
        }
        return null;
    }

    /**
     * 获取自增value
     * 
     * @param key
     * @return
     */
    protected Long getIncrValue(final String key) {

        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] rowkey = serializer.serialize(key);
                byte[] rowval = connection.get(rowkey);
                try {
                    String val = serializer.deserialize(rowval);
                    return Long.parseLong(val);
                } catch (Exception e) {
                    logger.error("获取缓存失败key[" + key + "], error[" + e + "]");
                }
                return null;
            }
        });
    }

    /**
     * 移除缓存
     * 
     * @param key
     * @return
     */
    protected boolean removeCacheValue(K key) {
        return removeCache(key);
    }

    /**
     * 移除缓存
     * 
     * @param key
     * @return
     */
    protected boolean removeCacheSet(K key) {
        return removeCache(key);
    }

    /**
     * 移除缓存
     * 
     * @param key
     * @return
     */
    protected boolean removeCacheList(K key) {
        return removeCache(key);
    }

    /**
     * 移除缓存
     * 
     * @param key
     * @param hashKeys
     * @return
     */
    protected boolean removeCacheHash(K key, Object... hashKeys) {
        try {
            redisTemplate.opsForHash().delete(key, hashKeys);
            return true;
        } catch (Exception e) {
            logger.error("移除缓存失败key[" + key + "], object[" + hashKeys + "], error[" + e + "]");
        }
        return false;
    }

    /**
     * 移除缓存
     * 
     * @param key
     * @return
     */
    protected boolean removeCache(K key) {
        try {
            redisTemplate.delete(key);
            return true;
        } catch (Throwable e) {
            logger.error("获取缓存失败key[" + key + ", error[" + e + "]");
        }
        return false;
    }

    /**
     * 缓存set操作
     * 
     * @param k
     * @param v
     * @param time
     * @return
     */
    protected boolean cacheSet(K key, V value, long time) {
        try {
            SetOperations<K, V> valueOps = redisTemplate.opsForSet();
            valueOps.add(key, value);
            if (time > 0)
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            return true;
        } catch (Throwable e) {
            logger.error("缓存[" + key + "]失败, value[" + value + "]", e);
        }
        return false;
    }

    /**
     * 缓存set
     * 
     * @param k
     * @param v
     * @return
     */
    protected boolean cacheSet(K key, V value) {
        return cacheSet(key, value, -1);
    }

    /**
     * 缓存set
     * 
     * @param k
     * @param v
     * @param time
     * @return
     */
    protected boolean cacheSet(K key, Set<V> value, long time) {
        try {
            SetOperations<K, V> setOps = redisTemplate.opsForSet();
            //setOps.add(key, value);
            if (time > 0)
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            return true;
        } catch (Throwable e) {
            logger.error("缓存[" + key + "]失败, value[" + value + "]", e);
        }
        return false;
    }

    /**
     * 缓存set
     * 
     * @param k
     * @param v
     * @return
     */
    protected boolean cacheSet(K key, Set<V> value) {
        return cacheSet(key, value, -1);
    }

    /**
     * 获取缓存set数据
     * 
     * @param k
     * @return
     */
    protected Set<V> getCacheSet(K key) {
        try {
            SetOperations<K, V> setOps = redisTemplate.opsForSet();
            return setOps.members(key);
        } catch (Throwable e) {
            logger.error("获取set缓存失败key[" + key + ", error[" + e + "]");
        }
        return null;
    }

    /**
     * list缓存，压栈
     * 
     * @param key
     * @param value
     * @param time
     * @return
     */
    protected boolean cacheListL(K key, V value, long time) {
        try {
            ListOperations<K, V> listOps = redisTemplate.opsForList();
            listOps.leftPush(key, value);
            if (time > 0)
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            return true;
        } catch (Throwable e) {
            logger.error("缓存[" + key + "]失败, value[" + value + "]", e);
        }
        return false;
    }

    /**
     * 缓存list，压栈
     * 
     * @param k
     * @param v
     * @return
     */
    protected boolean cacheListL(K key, V value) {
        return cacheListL(key, value, -1);
    }

    /**
     * 缓存list，压栈
     * 
     * @param k
     * @param v
     * @param time
     * @return
     */
    protected boolean cacheListL(K key, List<V> value, long time) {
        try {
            ListOperations<K, V> listOps = redisTemplate.opsForList();
            long l = listOps.leftPushAll(key, value);
            if (time > 0)
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            return true;
        } catch (Throwable e) {
            logger.error("缓存[" + key + "]失败, value[" + value + "]", e);
        }
        return false;
    }

    /**
     * 缓存list，压栈
     * 
     * @param k
     * @param v
     * @return
     */
    protected boolean cacheListL(K key, List<V> value) {
        return cacheListL(key, value, -1);
    }

    /**
     * list缓存，入列
     * 
     * @param k
     * @param v
     * @param time
     * @return
     */
    protected boolean cacheListR(K key, V value, long time) {
        try {
            ListOperations<K, V> listOps = redisTemplate.opsForList();
            listOps.rightPush(key, value);
            if (time > 0)
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            return true;
        } catch (Throwable e) {
            logger.error("缓存[" + key + "]失败, value[" + value + "]", e);
        }
        return false;
    }

    /**
     * 缓存list，入列
     * 
     * @param k
     * @param v
     * @return
     */
    protected boolean cacheListR(K key, V value) {
        return cacheListR(key, value, -1);
    }

    /**
     * 缓存list，入列
     * 
     * @param k
     * @param v
     * @param time
     * @return
     */
    protected boolean cacheListR(K key, List<V> value, long time) {
        try {
            ListOperations<K, V> listOps = redisTemplate.opsForList();
            long l = listOps.rightPushAll(key, value);
            if (time > 0)
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            return true;
        } catch (Throwable e) {
            logger.error("缓存[" + key + "]失败, value[" + value + "]", e);
        }
        return false;
    }

    /**
     * 缓存list，入列
     * 
     * @param k
     * @param v
     * @return
     */
    protected boolean cacheListR(K key, List<V> value) {
        return cacheListR(key, value, -1);
    }

    /**
     * 获取list缓存
     * 
     * @param key
     * @param start
     * @param end
     * @return
     */
    protected List<V> getCacheList(K key, long start, long end) {
        try {
            ListOperations<K, V> listOps = redisTemplate.opsForList();
            return listOps.range(key, start, end);
        } catch (Throwable e) {
            logger.error("获取list缓存失败key[" + key + ", error[" + e + "]");
        }
        return null;
    }

    /**
     * 获取总条数, 可用于分页
     * 
     * @param key
     * @return
     */
    protected long getCacheListSize(K key) {
        try {
            ListOperations<K, V> listOps = redisTemplate.opsForList();
            return listOps.size(key);
        } catch (Throwable e) {
            logger.error("获取list长度失败key[" + key + "], error[" + e + "]");
        }
        return 0;
    }

    /**
     * 获取总条数, 可用于分页
     * 
     * @param listOps
     * @param key
     * @return
     */
    protected long getCacheListSize(ListOperations<K, V> listOps, K key) {
        try {
            return listOps.size(key);
        } catch (Throwable e) {
            logger.error("获取list长度失败key[" + key + "], error[" + e + "]");
        }
        return 0;
    }

    /**
     * 出栈
     * 
     * @param key
     * @return
     */
    protected V getCacheListLPop(K key) {
        try {
            ListOperations<K, V> listOps = redisTemplate.opsForList();
            return listOps.leftPop(key);
        } catch (Throwable e) {
            logger.error("获取list缓存失败key[" + key + ", error[" + e + "]");
        }
        return null;
    }

    /**
     * 出列
     * 
     * @param key
     * @return
     */
    protected V getCacheListRPop(K key) {
        try {
            ListOperations<K, V> listOps = redisTemplate.opsForList();
            return listOps.rightPop(key);
        } catch (Throwable e) {
            logger.error("获取list缓存失败key[" + key + ", error[" + e + "]");
        }
        return null;
    }

    /**
     * 移除list缓存
     * 
     * @param key
     * @return
     */
    protected boolean removeCacheOneOfList(K key) {
        try {
            ListOperations<K, V> listOps = redisTemplate.opsForList();
            listOps.rightPop(key);
            return true;
        } catch (Throwable e) {
            logger.error("移除list缓存失败key[" + key + ", error[" + e + "]");
        }
        return false;
    }

    /**
     * 缓存hash操作
     * 
     * @param key
     * @param hashKey
     * @param value
     * @return
     */
    protected boolean cacheHash(K key, Object hashKey, Object value) {
        try {
            redisTemplate.opsForHash().put(key, hashKey, value);
            return true;
        } catch (Exception e) {
            logger.error("缓存[" + key + "]失败, object[" + hashKey + "], value[" + value + "]", e);
        }
        return false;
    }

    /**
     * 缓存hash操作
     * 
     * @param key
     * @param map
     * @return
     */
    protected boolean cacheHash(K key, Map<? extends Object, ? extends Object> map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            logger.error("缓存[" + key + "]失败, value[" + map + "]", e);
        }
        return false;
    }

    /**
     * 获取缓存
     * 
     * @param key
     * @return
     */
    protected List<Object> getCacheHash(K key) {
        try {
            return redisTemplate.opsForHash().values(key);
        } catch (Exception e) {
            logger.error("获取缓存失败key[" + key + ", error[" + e + "]");
        }
        return null;
    }

    /**
     * 获取值
     * 
     * @param key
     * @param hashKey
     * @return
     */
    protected Object getCacheHash(K key, Object hashKey) {
        try {
            return redisTemplate.opsForHash().get(key, hashKey);
        } catch (Exception e) {
            logger.error("获取缓存失败key[" + key + "],object[" + hashKey + "], error[" + e + "]");
        }
        return null;
    }

    /**
     * 缓存ZSet操作
     *
     * @param key key
     * @param value value
     * @param sort 排序
     * @return
     */
    protected boolean cacheZSet(K key, V value, Long sort) {
        try {
            redisTemplate.opsForZSet().add(key, value, sort);
            return true;
        } catch (Throwable e) {
            logger.error("缓存[" + key + "]失败, value[" + value + "]", e);
            return false;
        }
    }

    /**
     * 获取ZSet缓存操作
     *
     * @param key key
     * @param start 起始
     * @param stop 结束
     * @return
     */
    protected Set<V> getCacheZSet(K key, Integer start, Integer stop) {
        try {
            return redisTemplate.opsForZSet().range(key, start, stop);
        } catch (Throwable e) {
            logger.error("获取缓存[" + key + "]失败, start[" + start + "]--stop[" + stop + "]", e);
            return null;
        }
    }

    /**
     * 删除ZSet缓存操作
     *
     * @param key key
     * @return
     */
    protected boolean removeCacheZSet(K key, Long score) {
        try {
            Long count = redisTemplate.opsForZSet().removeRangeByScore(key, score, score);
            if (count <= 0) {
                return false;
            }
            return true;
        } catch (Throwable e) {
            logger.error("删除缓存[" + key + "]失败," + e);
            return false;
        }
    }

    /**
     * 缓存hash，自增操作
     * 
     * @param key
     * @param hashKey
     * @param value
     * @return
     */
    protected boolean cacheHashIncr(K key, Object hashKey, long value) {
        try {
            Long count = redisTemplate.opsForValue().increment(key, value);
            if (count <= 0) {
                return false;
            }
            return true;
        } catch (Exception e) {
            logger.error("更新缓存[" + key + "]失败, object[" + hashKey + "]" + e);
            return false;
        }
    }

    protected Long getIncrHash(final String key, final Object hashKey) {

        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                final byte[] rawKey = rawKey(key);
                final byte[] rawHashKey = rawHashKey(hashKey);
                try {
                    byte[] rowval = connection.hGet(rawKey, rawHashKey);
                    String val = serializer.deserialize(rowval);
                    return Long.parseLong(val);
                } catch (Exception e) {
                    logger.error("获取缓存失败key[" + key + "], error[" + e + "]");
                }
                return null;
            }
        });
    }

    /**
     * 判断缓存是否存在
     * 
     * @param key
     * @return
     */
    protected boolean containsValueKey(K key) {
        return containsKey(key);
    }

    /**
     * 判断缓存是否存在
     * 
     * @param key
     * @return
     */
    protected boolean containsSetKey(K key) {
        return containsKey(key);
    }

    /**
     * 判断缓存是否存在
     * 
     * @param key
     * @return
     */
    protected boolean containsListKey(K key) {
        return containsKey(key);
    }

    /**
     * 判断缓存是否存在
     * 
     * @param key
     * @return
     */
    protected boolean containsKey(K key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Throwable e) {
            logger.error("判断缓存存在失败key[" + key + "], error[" + e + "]");
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    protected byte[] rawKey(Object key) {
        Assert.notNull(key, "non null key required");
        if (getRedisKeySerializer() == null && key instanceof byte[]) {
            return (byte[]) key;
        }
        return getRedisKeySerializer().serialize(key);

    }

    @SuppressWarnings("unchecked")
    protected byte[] rawHashKey(Object hashKey) {
        Assert.notNull(hashKey, "non null hash key required");
        if (hashKeySerializer() == null && hashKey instanceof byte[]) {
            return (byte[]) hashKey;
        }
        return hashKeySerializer().serialize(hashKey);
    }

    @SuppressWarnings("unchecked")
    protected byte[] rawValue(Object value) {
        Assert.notNull(value, "non null key required");
        if (getRedisValueSerializer() == null && value instanceof byte[]) {
            return (byte[]) value;
        }
        return getRedisValueSerializer().serialize(value);

    }

    protected RedisSerializer<String> getRedisStringSerializer() {
        return redisTemplate.getStringSerializer();
    }

    @SuppressWarnings("rawtypes")
    protected RedisSerializer getRedisValueSerializer() {
        return redisTemplate.getValueSerializer();
    }

    @SuppressWarnings("rawtypes")
    protected RedisSerializer getRedisKeySerializer() {
        return redisTemplate.getKeySerializer();
    }

    @SuppressWarnings("rawtypes")
    protected RedisSerializer hashKeySerializer() {
        return redisTemplate.getHashKeySerializer();
    }

    /**
     * 按通配符删除
     * 
     * @param pattern
     */
    protected void clean(K pattern) {
        Set<K> keys = redisTemplate.keys(pattern);
        if (!keys.isEmpty()) {
            redisTemplate.delete(keys);
        }
    }

    public void setRedisTemplate(RedisTemplate<K, V> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

}
