package com.mocentre.tehui.goods.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mocentre.tehui.core.dao.BaseDao;
import com.mocentre.tehui.goods.dao.ICouponDao;
import com.mocentre.tehui.goods.model.Coupon;

/**
 * 优惠券Dao接口实现 Created by yukaiji on 2016/11/24.
 */
@Repository
public class CouponDao extends BaseDao<Coupon> implements ICouponDao {

    @Override
    public Coupon queryByIdShop(Long id, Long shopId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", id);
        paramMap.put("shopId", shopId);
        return super.queryUniquely(paramMap);
    }

    @Override
    public List<Coupon> getInnerNotExpiredList() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("notExpired", true);
        paramMap.put("isOuter", "0");
        return super.queryList(paramMap);
    }

}
