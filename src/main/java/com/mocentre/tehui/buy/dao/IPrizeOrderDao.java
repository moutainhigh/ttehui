package com.mocentre.tehui.buy.dao;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.buy.model.PrizeOrder;
import com.mocentre.tehui.core.service.support.paging.PageInfo;
import com.mocentre.tehui.core.service.support.query.Requirement;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 实物礼品订单dao接口
 *
 * Created by wangxueying on 2017/8/10.
 */
public interface IPrizeOrderDao {

    PrizeOrder getById(Long id);

    /**
     * 分页查询
     */
    ListJsonResult<PrizeOrder> queryDatatablesPage(Requirement require);

    List<PrizeOrder> queryList(Map<String, Object> paramMap);

    Long saveEntity(PrizeOrder ntcMail);

    Long updateEntity(PrizeOrder ntcMail);

    int logicRemoveById(Serializable id);

    PageInfo<PrizeOrder> queryPageInfo(Long customerId, Integer start, Integer length);
}
