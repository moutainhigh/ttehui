package com.mocentre.gift.buy.dao.impl;

import com.mocentre.gift.buy.dao.IGiftOrderDetailDao;
import com.mocentre.gift.buy.model.GiftOrderDetail;
import com.mocentre.tehui.core.dao.BaseDao;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单详情dao
 * Created by 王雪莹 on 2017/4/10.
 */
@Repository
public class GiftOrderDetailDao extends BaseDao<GiftOrderDetail> implements IGiftOrderDetailDao {
    @Override
    public List<GiftOrderDetail> queryByOrder(String orderNum) {
        Assert.notNull(orderNum);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("orderNum", orderNum);
        return this.queryList(paramMap);
    }

    @Override
    public void saveOrderDetail(List<GiftOrderDetail> orderDetailList) {
        super.getSqlSession().insert("GiftOrderDetail_insert_batch", orderDetailList);
    }

    @Override
    public Integer deleteByOrderNum(String orderNum) {
        GiftOrderDetail orderDetail = new GiftOrderDetail();
        orderDetail.setOrderNum(orderNum);
        return super.getSqlSession().update("GiftOrderDetail_logicDelete", orderDetail);
    }

    @Override
    public Integer deleteByOrderId(Long orderId) {
        GiftOrderDetail orderDetail = new GiftOrderDetail();
        orderDetail.setOrderId(orderId);
        return super.getSqlSession().update("GiftOrderDetail_logicDelete", orderDetail);
    }

    
    @Override
    public void updateDetail(GiftOrderDetail detail) {
        super.getSqlSession().update("GiftOrderDetail_update", detail);
    }

    @Override
    public Integer logicDelete(Long id) {
        GiftOrderDetail orderDetail = new GiftOrderDetail();
        orderDetail.setId(id);
        return super.getSqlSession().update("GiftOrderDetail_logicDelete", orderDetail);
    }
}
