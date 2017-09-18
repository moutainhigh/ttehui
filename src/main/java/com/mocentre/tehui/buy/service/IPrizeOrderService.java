package com.mocentre.tehui.buy.service;

import java.util.List;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.backend.model.PrizeOrderInstance;
import com.mocentre.tehui.backend.param.PrizeOrderParam;
import com.mocentre.tehui.buy.model.PrizeOrder;
import com.mocentre.tehui.core.service.support.paging.PageInfo;
import com.mocentre.tehui.core.service.support.query.Requirement;

/**
 * 实物礼品订单Service接口 Created by wangxueying on 2017/8/10.
 */
public interface IPrizeOrderService {

    /**
     * 分页获取所有的实物礼品订单
     *
     * @return 所有的实物礼品订单
     */
    ListJsonResult<PrizeOrderInstance> queryPrizeOrderPage(Requirement require);

    /**
     * 根据用户id获取实物礼品订单列表
     *
     * @return
     */
    List<PrizeOrder> getPrizeOrderList(String customerId);

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    PrizeOrder getPrizeOrderById(Long id);

    /**
     * 添加一个礼品订单
     * 
     * @param telephone
     * @param prizeName
     * @param prizeImg
     * @param startTime yyyy-MM-dd HH:mm:ss
     * @param endTime yyyy-MM-dd HH:mm:ss
     * @return
     */
    Long addPrizeOrder(String telephone, String prizeName, String prizeImg, String startTime, String endTime);

    /**
     * 更新一个礼品订单
     *
     * @param prizeOrder
     * @return id
     */
    Long updatePrizeOrder(PrizeOrderParam prizeOrder);

    /**
     * 更新一个礼品订单
     *
     * @param prizeOrder
     * @return id
     */
    Long updatePrizeOrder(PrizeOrder prizeOrder);

    /**
     * 分页查询
     *
     * @return 分页查询
     */
    PageInfo<PrizeOrder> queryPrizeOrderPage(Long customerId, Integer start, Integer length);

}
