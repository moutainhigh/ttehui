package com.mocentre.gift.buy.dao;

import com.mocentre.common.ListJsonResult;
import com.mocentre.gift.buy.model.GiftOrder;
import com.mocentre.tehui.core.service.support.paging.PageInfo;
import com.mocentre.tehui.core.service.support.query.Requirement;

/**
 * 订单dao接口
 * Created by 王雪莹 on 2017/4/10.
 */
public interface IGiftOrderDao {

    /**
     * 新增订单
     * @param order
     */
    void saveOrder(GiftOrder order);

    /**
     * 分页查询
     * @param require
     * @return
     */
    ListJsonResult<GiftOrder> queryDatatablesPage(Requirement require);

    /**
     * 修改订单
     * @param order
     */
    void upateById(GiftOrder order);


    /**
     * 更新订单状态
     *
     * @param id
     * @param status
     */
    void updateOrderStatus(Long id, String status);

    /**
     * 更新订单取消状态
     *
     * @param id
     * @param status
     */
    void updateCancelStatus(Long id, String status);

    /**
     * 逻辑删除
     * @param orderNum
     * @return
     */
    Integer logicDelete(String orderNum);

    /**
     * 逻辑删除
     * @param id
     * @return
     */
    Integer logicDelete(Long id);

    /**
     * 订单分页查询
     * @param customerId 用户id
     * @return
     */
    PageInfo<GiftOrder> queryPageInfo(Long customerId, int start, int length);

    /**
     * 查询订单
     * @param orderNum 订单编号
     * @param customerId 用户id
     * @return
     */
    GiftOrder queryByNumCustomer(String orderNum, Long customerId);

    /**
     * 按订单编号查询
     * @param orderNum
     * @return
     */
    GiftOrder queryByNum(String orderNum);

    /**
     * 按订单id查询
     * @param id
     * @return
     */
    GiftOrder queryById(Long id);



}
