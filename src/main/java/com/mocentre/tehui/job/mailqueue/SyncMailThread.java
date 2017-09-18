/*
 * Copyright 2017 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.job.mailqueue;

import org.springframework.util.StopWatch;

import com.mocentre.tehui.core.utils.SpringContextUtil;
import com.mocentre.tehui.ntc.service.INtcMailService;

/**
 * 类SyncCouponThread.java的实现描述：执行发送邮件任务
 * 
 * @author sz.gong 2017年7月27日 下午2:04:54
 */
public class SyncMailThread implements Runnable {

    private MailMsgVo       mailMsg;

    private INtcMailService ntcMailService;

    public SyncMailThread(MailMsgVo mailMsg) {
        super();
        this.mailMsg = mailMsg;
        ntcMailService = SpringContextUtil.getBean("ntcMailService");
    }

    @Override
    public void run() {
        StopWatch watch = new StopWatch();
        watch.start();
        String type = mailMsg.getType();
        String title = mailMsg.getTitle();
        String content = mailMsg.getContent();
        ntcMailService.sendNotice(type, title, content);
        watch.stop();
    }

}
