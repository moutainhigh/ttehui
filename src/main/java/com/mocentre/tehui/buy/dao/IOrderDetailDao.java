/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.buy.dao;

import java.util.List;

import com.mocentre.tehui.buy.model.OrderDetail;

/**
 * 类IOrderDetailDao.java的实现描述：订单详情接口
 * 
 * @author sz.gong 2016年11月15日 下午3:52:07
 */
public interface IOrderDetailDao {

    /**
     * 通过订单详情编号查询
     * 
     * @param orderDetailNum
     * @return
     */
    OrderDetail getByDetailNum(String orderDetailNum);

    /**
     * 通过订单编号和订单详情编号查询
     * 
     * @param orderNum
     * @param orderDetailNum
     * @return
     */
    OrderDetail getByNums(String orderNum, String orderDetailNum);

    /**
     * 通过订单编号查询
     * 
     * @param orderNum
     * @return
     */
    List<OrderDetail> queryByOrder(String orderNum);

    /**
     * 批量新增订单详情
     * 
     * @param detailList
     */
    void saveBatchDetail(List<OrderDetail> detailList);

    /**
     * 新增订单详情
     * 
     * @param detail
     */
    void saveDetail(OrderDetail detail);

    /**
     * 更新优惠券
     * 
     * @param id
     * @param couponSn
     * @param couponMoney
     */
    void updateCoupon(Long id, String couponSn, Long couponMoney);

    /**
     * 撤销退款申请，更新退款状态为无需退款
     * 
     * @param orderDetailNum
     * @return
     */
    int updateRefundStatusBack(String orderDetailNum);

    /**
     * 更新退款状态为退款中
     * 
     * @param orderDetailNum
     * @param refundReason
     * @param refundDes
     * @return
     */
    int updateRefundStatusIng(String orderDetailNum, String refundReason, String refundDes);

    /**
     * 更新退款状态为退款成功
     * 
     * @param orderDetailNum 订单详情编号
     * @param refundMoney 退款金额
     */
    int updateRefundStatusSucByDnum(String orderDetailNum, Long refundMoney);

    /**
     * 更新为退款成功
     * 
     * @param orderNum 订单编号
     */
    int updateRefundStatusSucByOnum(String orderNum);

}
