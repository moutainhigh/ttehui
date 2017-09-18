package com.mocentre.tehui.goods.service;

import java.util.List;
import java.util.Map;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.core.service.support.paging.PageInfo;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.goods.model.CouponDetail;

/**
 * 优惠券详细信息接口 Created by yukaiji on 2016/11/24.
 */
public interface ICouponDetailService {

    /**
     * 获取所有的优惠券详细信息
     * 
     * @return 发现页List
     */
    ListJsonResult<CouponDetail> queryCouponDetailPage(Requirement require);

    /**
     * 根据id获取优惠券详细信息
     * 
     * @return 发现页
     */
    CouponDetail getCouponDetail(Long id);

    /**
     * 通过优惠码查询
     * 
     * @param couponSn
     * @return
     */
    CouponDetail getCouponDetailBySn(String couponSn);

    /**
     * 分页查询用户未使用的优惠券
     * 
     * @param customerId 用户id
     * @param start
     * @param length
     * @return
     */
    PageInfo<CouponDetail> getUnusedCouponDetailPage(Long customerId, Integer start, Integer length);

    /**
     * 分页查询用户已使用的优惠券
     * 
     * @param customerId 用户id
     * @param start
     * @param length
     * @return
     */
    PageInfo<CouponDetail> getUsedCouponDetailPage(Long customerId, Integer start, Integer length);

    /**
     * 分页查询用户已过期的优惠券
     * 
     * @param customerId 用户id
     * @param start
     * @param length
     * @return
     */
    PageInfo<CouponDetail> getExpriedCouponDetailPage(Long customerId, Integer start, Integer length);

    /**
     * 批量添加优惠券详细信息
     * 
     * @param couponDetailList
     * @return
     */
    int addCouponDetail(List<CouponDetail> couponDetailList);

    /**
     * 根据券码删除
     * 
     * @param coupon_sn 券码
     * @return
     */
    void deleteCouponDetail(String couponSn);

    /**
     * 根据优惠券id删除
     * 
     * @param coupon_id id
     * @return
     */
    void deleteAllCouponDetail(List<Long> couponIdList);

    /**
     * 按优惠券编码修改优惠券的用户
     * 
     * @param couponSn
     * @param customerId
     * @return
     */
    int updateCouponDetailCustomer(String couponSn, Long customerId);

    /**
     * 查询用户未使用未过期的优惠券
     * 
     * @param customerId
     * @return
     */
    List<CouponDetail> getCouponDetailNotExpiredUnusedList(Long customerId);

    /**
     * 通过用户id和优惠券编号查询未使用过的优惠券
     * 
     * @param customerId
     * @param couponSn
     * @return
     */
    CouponDetail queryUnusedCoupon(Long customerId, String couponSn);

    /**
     * 查询用户指定的优惠券
     * 
     * @param customerId
     * @param couponId
     * @return
     */
    List<CouponDetail> getCouponDetailList(Long customerId, Long couponId);

    /**
     * 通过优惠券id查询一张未领取的优惠券
     * 
     * @param couponId
     * @return
     */
    CouponDetail getOneUnReceiveCouponDetail(Long couponId);

    /**
     * 更新优惠券指定到用户，缓存此优惠券到可以或者不可用优惠券队列
     * 
     * @param couponDetail 优惠券
     * @param customerId 用户id
     * @param goodsMapList 购买商品集合map（map对应指定key->goodsId,stcode,buyNum）
     * @return
     */
    int updateCouponDetailCustomerToCache(CouponDetail couponDetail, Long customerId,
                                          List<Map<String, Object>> goodsMapList);

    /**
     * 更新用户指定商品的可用优惠券和不可用优惠券到缓存，返回可用优惠券的数量
     * 
     * @param customerId 用户id
     * @param goodsMapList 购买商品集合map（map对应指定key->goodsId,stcode,buyNum）
     * @return 可用优惠券的数量
     */
    Integer updateCustomerCouponToCache(Long customerId, List<Map<String, Object>> goodsMapList);

    /**
     * 计算使用优惠券后商品总价格
     * 
     * @param couponDetail 使用的优惠券
     * @param goodsMapList 购买商品集合map（map对应指定key->goodsId,stcode,buyNum）
     * @return 商品总价格
     */
    Long calcTotalPrice(CouponDetail couponDetail, List<Map<String, Object>> goodsMapList);

    /**
     * 缓存中查询用户可用的优惠券
     * 
     * @param customerId 用户id
     * @param start
     * @param end
     * @return
     */
    Map<String, Object> queryUseFromCache(Long customerId, long start, long end);

    /**
     * 缓存中查询用户不可用的优惠券
     * 
     * @param customerId 用户id
     * @param start
     * @param end
     * @return
     */
    Map<String, Object> queryUnusedFromCache(Long customerId, long start, long end);

    /**
     * 添加一张优惠券
     *
     * @param couponDetail 优惠券信息
     * @return
     */
    Long addOneCouponDetail(CouponDetail couponDetail);

    /**
     * 更新优惠券为指定的用户
     * 
     * @param couponSn
     * @param telephone
     * @return
     */
    int updateCouponDetailCustomer(String couponSn, String telephone);
}
