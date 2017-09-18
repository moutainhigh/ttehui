package com.mocentre.gift.buy.dao;

import com.mocentre.gift.buy.model.GiftOrderBill;

import java.util.List;

/**
 * 发票dao接口
 * Created by 王雪莹 on 2017/4/10.
 */
public interface IGiftOrderBillDao {
    /**
     * 新增发票信息
     * @param bill
     */
    void saveOrderBill(GiftOrderBill bill);

    /**
     * 修改发票信息
     * @param bill
     */
    void updateOrderBill(GiftOrderBill bill);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    GiftOrderBill getById(Long id);

    /**
     * 根据用户id查询
     * @param customerId
     * @return
     */
    List<GiftOrderBill> getByCustomerId(Long customerId);

   /**
    * 根据id删除
    * @param Id
    * @return
    */
    Integer logicDelete(Long Id);

    /**
     * 根据订单Num查询
     * @param orderNum
     * @return
     */
    GiftOrderBill queryByOrderNum(String orderNum);

    /**
     * 根据订单id删除
     * @param orderId
     * @return
     */
    GiftOrderBill queryByOrderId(Long orderId);
}
