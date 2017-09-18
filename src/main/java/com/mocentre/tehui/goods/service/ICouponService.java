package com.mocentre.tehui.goods.service;

import java.util.List;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.goods.model.Coupon;

/**
 * 优惠券接口 Created by yukaiji on 2016/11/24.
 */
public interface ICouponService {

    /**
     * 获取所有的优惠券信息
     * 
     * @return 发现页List
     */
    ListJsonResult<Coupon> queryCouponPage(Requirement require);

    /**
     * 根据id获取
     * 
     * @param id
     * @return
     */
    Coupon getCouponById(Long id);

    /**
     * 根据id获取优惠券
     * 
     * @return 发现页
     */
    Coupon getCoupon(Long id, Long shopId);

    /**
     * 查询未过期且是商城内部领取的优惠券
     * 
     * @return
     */
    List<Coupon> getNotExpiredAndNotOuterCouponList();

    /**
     * 添加一个优惠券
     * 
     * @param coupon
     * @return
     */
    Coupon addCoupon(Coupon coupon);

    /**
     * 修改一个优惠券
     * 
     * @param coupon
     */
    Long updateCouponAndDetail(Coupon coupon);

    /**
     * 根据id列表删除一个优惠券
     * 
     * @param idList id列表
     * @return
     */
    void deleteCoupon(List<Long> idList);
}
