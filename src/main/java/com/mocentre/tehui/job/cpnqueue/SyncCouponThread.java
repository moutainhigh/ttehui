/*
 * Copyright 2017 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.job.cpnqueue;

import org.springframework.util.StopWatch;

import com.mocentre.tehui.buy.service.IPrizeOrderService;
import com.mocentre.tehui.common.utils.LoggerUtil;
import com.mocentre.tehui.core.utils.SpringContextUtil;
import com.mocentre.tehui.goods.service.ICouponDetailService;

/**
 * 类SyncCouponThread.java的实现描述：执行同步优惠券任务
 * 
 * @author sz.gong 2017年7月27日 下午2:04:54
 */
public class SyncCouponThread implements Runnable {

    private CouponMsgVo          couponMsg;

    private ICouponDetailService couponDetailService;

    private IPrizeOrderService   prizeOrderService;

    public SyncCouponThread(CouponMsgVo couponMsg) {
        super();
        this.couponMsg = couponMsg;
        couponDetailService = SpringContextUtil.getBean("couponDetailService");
        prizeOrderService = SpringContextUtil.getBean("prizeOrderService");
    }

    @Override
    public void run() {
        StopWatch watch = new StopWatch();
        try {
            watch.start();
            String type = couponMsg.getType();
            String telephone = couponMsg.getTelephone();
            String couponSn = couponMsg.getCouponCode();
            String prizeName = couponMsg.getPrizeName();
            String prizeImg = couponMsg.getPrizeImg();
            String startTime = couponMsg.getStartTime();
            String endTime = couponMsg.getEndTime();
            LoggerUtil.tehuiLog.info("SyncCouponThread=type=" + type + ",telephone:" + telephone + ",prizeName:"
                    + prizeName + ",endTime:" + endTime + ",couponSn:" + couponSn);
            if ("2".equals(type)) {
                couponDetailService.updateCouponDetailCustomer(couponSn, telephone);
            } else if ("1".equals(type)) {
                prizeOrderService.addPrizeOrder(telephone, prizeName, prizeImg, startTime, endTime);
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("SyncCouponThread：同步抽奖线程异常");
        } finally {
            watch.stop();
        }
    }

}
