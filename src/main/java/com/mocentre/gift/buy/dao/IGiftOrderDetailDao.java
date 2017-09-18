package com.mocentre.gift.buy.dao;

import com.mocentre.gift.buy.model.GiftOrderDetail;

import java.util.List;

/**
 * 订单详情dao接口
 * Created by 王雪莹 on 2017/4/10.
 */
public interface IGiftOrderDetailDao {
    /**
     * 根据订单号获取订单项
     * @param orderNum
     * @return
     */
    List<GiftOrderDetail> queryByOrder(String orderNum);

    /**
     * 保存订单项
     * @param orderDetailList
     */
    void saveOrderDetail(List<GiftOrderDetail> orderDetailList);

    /**
     * 根据订单号删除订单项（逻辑删除）
     * @param orderNum
     */
    Integer deleteByOrderNum(String orderNum);

    /**
     * 根据订单详情id删除
     * @param Id
     * @return
     */
    Integer logicDelete(Long Id);

    /**
     * 根基订单id删除订单详情
     * @param orderId
     * @return
     */
    Integer deleteByOrderId(Long orderId);

    /**
     * 更新订单项
     * @param detail
     */
    void updateDetail(GiftOrderDetail detail);
}
