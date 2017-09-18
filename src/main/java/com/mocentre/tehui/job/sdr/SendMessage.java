/*
 * Copyright 2017 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.job.sdr;

import org.springframework.data.redis.core.RedisTemplate;

/**
 * 类SendMessage.java的实现描述：TODO 类实现描述
 * 
 * @author sz.gong 2017年3月12日 下午4:14:23
 */
public class SendMessage<T> {

    private RedisTemplate<String, T> redisTemplate;

    public void setRedisTemplate(RedisTemplate<String, T> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void sendMessage(String channel, T message) {
        redisTemplate.convertAndSend(channel, message);
    }

}
