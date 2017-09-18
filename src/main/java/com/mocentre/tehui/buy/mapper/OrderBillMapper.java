/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.buy.mapper;

import java.util.Date;

import com.mocentre.tehui.buy.enums.OrderBillType;
import com.mocentre.tehui.buy.model.OrderBill;
import com.mocentre.tehui.frontend.model.OrderBillFTInstance;
import com.mocentre.tehui.frontend.param.OrderBillParam;

/**
 * 类OrderBillMapper.java的实现描述：发票转换
 * 
 * @author sz.gong 2016年12月29日 下午2:59:18
 */
public class OrderBillMapper {

    public static OrderBillFTInstance toOrderBillFTInstance(OrderBill orderBill) {
        OrderBillFTInstance billIns = new OrderBillFTInstance();
        billIns.setBillHeader(orderBill.getBillHeader());
        billIns.setBillRemark(orderBill.getBillRemark());
        billIns.setBillStatus(orderBill.getBillStatus());
        billIns.setBillType(orderBill.getBillType());
        billIns.setOrderNum(orderBill.getOrderNum());
        return billIns;
    }

    public static OrderBill toOrderBill(OrderBillParam billParam, Long customerId, String orderNum) {
        OrderBill bill = new OrderBill();
        bill.setBillHeader(billParam.getBillHeader());
        bill.setBillRemark(billParam.getBillRemark());
        if ("1".equals(billParam.getBillType())) {
            bill.setBillType(OrderBillType.PERSONAL.getCodeValue());
        } else if ("2".equals(billParam.getBillType())) {
            bill.setBillType(OrderBillType.COMPAY.getCodeValue());
        }
        bill.setCustomerId(customerId);
        bill.setOrderNum(orderNum);
        bill.setGmtCreated(new Date());
        bill.setGmtModified(new Date());
        return bill;
    }

}
