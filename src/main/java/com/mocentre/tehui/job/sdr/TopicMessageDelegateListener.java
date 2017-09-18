/*
 * Copyright 2017 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.job.sdr;

import java.io.Serializable;

/**
 * 类TopicMessageListener.java的实现描述：TODO 类实现描述
 * 
 * @author sz.gong 2017年3月12日 下午4:01:54
 */
public class TopicMessageDelegateListener {

    public void handleMessage(Serializable message) {
        if (message instanceof StorageMsgVo) {
            StorageMsgVo msg = (StorageMsgVo) message;
            System.out.println(msg.getGoodsId());
        }
        System.out.println(message);
    }

}
