/*
 * Copyright 2017 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.td.mapper;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.mocentre.tehui.core.utils.DateUtils;
import com.mocentre.tehui.frontend.param.ThirdOrderParam;
import com.mocentre.tehui.frontend.param.ThirdOrderProductParam;
import com.mocentre.tehui.td.emnus.ThirdOrderStatus;
import com.mocentre.tehui.td.model.ThirdOrder;

/**
 * 类ThirdOrderMapper.java的实现描述：第三方订单转换
 * 
 * @author sz.gong 2017年6月21日 上午11:26:50
 */
public class ThirdOrderMapper {

    public static ThirdOrder toThirdOrder(ThirdOrderParam orderParam) {
        if (orderParam == null) {
            return null;
        }
        String orderDate = orderParam.getOrderDate();
        String orderTime = orderParam.getOrderTime();
        ThirdOrder order = new ThirdOrder();
        order.setOrderNum(orderParam.getOrderNum());
        order.setOrderTimes(DateUtils.parseTime3(orderDate + " " + orderTime));
        order.setOrderDate(orderDate);
        order.setOrderTime(orderTime);
        order.setOrderAmount(orderParam.getOrderAmount());
        order.setOrderSource("");
        order.setAbcaid(orderParam.getAbcaid());
        order.setNotifyUrl(orderParam.getNotifyUrl());
        order.setOrderStatus(ThirdOrderStatus.WAIT.getCodeValue());
        order.setAppKey(orderParam.getAppKey());
        List<ThirdOrderProductParam> productList = orderParam.getProductList();
        String products = "";
        if (productList != null) {
            products = JSON.toJSONString(productList, SerializerFeature.WriteMapNullValue);
        }
        order.setProducts(products);
        return order;
    }

}
