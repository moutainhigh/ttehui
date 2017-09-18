/*
 * Copyright 2017 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.job;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.mocentre.tehui.buy.model.Order;
import com.mocentre.tehui.buy.model.OrderDetail;
import com.mocentre.tehui.buy.service.IOrderDetailService;
import com.mocentre.tehui.buy.service.IOrderService;
import com.mocentre.tehui.common.utils.LoggerUtil;

/**
 * 类OrderCloseJob.java的实现描述：未支付订单自动关闭任务。30分钟未支付的订单，自动关闭
 * 
 * @author sz.gong 2017年3月3日 上午10:22:23
 */
public class OrderCloseJob extends QuartzJobBean {

    private IOrderService       orderService;
    private IOrderDetailService orderDetailService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        try {
            Integer times = 30 * 60;
            SchedulerContext ctx = context.getScheduler().getContext();
            orderService = (IOrderService) ctx.get("orderService");
            orderDetailService = (IOrderDetailService) ctx.get("orderDetailService");
            Map<String, List<OrderDetail>> orderDetailMap = new HashMap<String, List<OrderDetail>>();
            List<Order> orderList = orderService.queryOrderListWaitPay(times);
            if (orderList != null) {
                for (Order order : orderList) {
                    List<OrderDetail> orderDetailList = orderDetailService.queryOrderDetail(order.getOrderNum());
                    orderDetailMap.put(order.getOrderNum(), orderDetailList);
                }
                orderService.updateOrderCloseAndToCache(orderList, orderDetailMap);
            }
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("订单自动关闭任务异常", e);
        }
    }

}
