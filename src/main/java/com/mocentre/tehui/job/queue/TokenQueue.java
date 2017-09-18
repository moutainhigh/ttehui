/*
 * Copyright 2017 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.job.queue;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;

import com.mocentre.tehui.common.utils.LoggerUtil;

/**
 * 类TokenQueue.java的实现描述：令牌队列
 * 
 * @author sz.gong 2017年5月26日 下午3:01:01
 */
public class TokenQueue {

    private RedisTemplate<String, String> redisTemplate;
    private String                        prefixKey;    //缓存前缀

    public Boolean pushFromHead(String key, Integer count) {
        try {
            key = getRealKey(key);
            //BoundListOperations<String, String> listOperations = redisTemplate.boundListOps(key);
            ListOperations<String, String> listOperations = redisTemplate.opsForList();
            List<String> tokenList = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                tokenList.add(i + "");
            }
            if (tokenList.size() > 0) {
                listOperations.leftPushAll(key, tokenList);
            }
            return true;
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("缓存令牌[" + key + "]失败", e);
        }
        return false;
    }

    public String removeFromHead(String key) {
        try {
            key = getRealKey(key);
            ListOperations<String, String> listOperations = redisTemplate.opsForList();
            return listOperations.leftPop(key);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("获取令牌[" + key + "]失败", e);
        }
        return null;
    }

    public boolean checkIfExists(String key) {
        key = getRealKey(key);
        return redisTemplate.hasKey(key);
    }

    public boolean clean(String key) {
        key = getRealKey(key);
        try {
            redisTemplate.delete(key);
            return true;
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("获取缓存失败key[" + key + ", error[" + e + "]");
        }
        return false;
    }

    private String getRealKey(String key) {
        return prefixKey + ":" + key;
    }

    public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setPrefixKey(String prefixKey) {
        this.prefixKey = prefixKey;
    }

}
