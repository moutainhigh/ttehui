package com.mocentre.tehui.goods.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.core.service.support.paging.PageInfo;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.goods.model.CouponDetail;

/**
 * 优惠券详情dao接口 Created by yukaiji on 2016/11/24.
 */
public interface ICouponDetailDao {

    /**
     * 分页查询已使用优惠券
     * 
     * @param customerId
     * @param start
     * @param length
     * @return
     */
    PageInfo<CouponDetail> getUsedPage(Long customerId, Integer start, Integer length);

    /**
     * 分页查询未使用优惠券
     * 
     * @param customerId
     * @param start
     * @param length
     * @return
     */
    PageInfo<CouponDetail> getUnusedPage(Long customerId, Integer start, Integer length);

    /**
     * 分页查询已过期优惠券
     * 
     * @param customerId
     * @param start
     * @param length
     * @return
     */
    PageInfo<CouponDetail> getExpiredPage(Long customerId, Integer start, Integer length);

    /**
     * 获取所有的优惠券
     * 
     * @return 发现页List
     */
    ListJsonResult<CouponDetail> queryDatatablesPage(Requirement require);

    /**
     * 通过优惠券id查询一张未领取的优惠券
     * 
     * @param couponId
     * @return
     */
    CouponDetail getOneByCoupon(Long couponId);

    /**
     * 根据id获取优惠券详情
     * 
     * @return 发现页
     */
    CouponDetail get(Serializable id);

    /**
     * 查询未过期未使用的优惠券
     * 
     * @param customerId
     * @return
     */
    List<CouponDetail> getNotExpiredUnusedList(Long customerId);

    /**
     * 通过编码查询
     * 
     * @param couponSn
     * @return
     */
    CouponDetail getBySn(String couponSn);

    /**
     * 批量添加优惠券
     * 
     * @param couponList
     * @return
     */
    int saveBatchEntity(List<CouponDetail> couponList);

    /**
     * 添加优惠券
     *
     * @param couponDetail
     * @return
     */
    Long saveEntity(CouponDetail couponDetail);

    /**
     * 根据id删除优惠券
     * 
     * @param couponId
     * @return
     */
    int removeByCid(Long couponId);

    /**
     * 根据编码删除
     * 
     * @param couponSn
     * @return
     */
    int removeBySn(String couponSn);

    /**
     * 根据卷码修改为已使用
     * 
     * @param couponSn
     * @param customerId
     * @return
     */
    int updateStatusUse(String couponSn, Long customerId);

    /**
     * 根据编码修改优惠券使用者
     * 
     * @param couponSn
     * @param customerId
     * @param status
     * @return
     */
    int updateCustomer(String couponSn, Long customerId);

    /**
     * 通过编码id，修改指定信息
     * 
     * @param couponId
     * @param beginTime
     * @param endTime
     * @param couponMoney
     * @param fullCut
     * @param couponDes
     * @return
     */
    int updateByCouponId(Long couponId, Date beginTime, Date endTime, Long couponMoney, Long fullCut, String couponDes);

    /**
     * 缓存指定可用和不可用的优惠券列表
     * 
     * @param customerId 用户id
     * @param canUseCouponDetailList 可用优惠券列表
     * @param noUseCouponDetailList 不可用优惠券列表
     */
    void updateBatchToCache(Long customerId, List<CouponDetail> canUseCouponDetailList,
                            List<CouponDetail> noUseCouponDetailList);

    /**
     * 缓存可用的优惠券
     * 
     * @param customerId 用户id
     * @param couponDetail 优惠券
     */
    void updateUseToCache(Long customerId, CouponDetail couponDetail);

    /**
     * 缓存不可用的优惠券
     * 
     * @param customerId 用户id
     * @param couponDetail 优惠券
     */
    void updateUnUseToCache(Long customerId, CouponDetail couponDetail);

    /**
     * 缓存中查询用户指定可用的优惠券总数量
     * 
     * @param customerId
     * @return
     */
    long countUseFromCache(Long customerId);

    /**
     * 缓存中查询用户指定不可用的优惠券总数量
     * 
     * @param customerId
     * @return
     */
    long countUnusedFromCache(Long customerId);

    /**
     * 缓存中查询用户指定可用的优惠券
     * 
     * @param customerId 用户id
     * @param start
     * @param end
     * @return
     */
    List<CouponDetail> queryUseFromCache(Long customerId, long start, long end);

    /**
     * 缓存中查询用户指定不可用的优惠券
     * 
     * @param customerId 用户id
     * @param start
     * @param end
     * @return
     */
    List<CouponDetail> queryUnusedFromCache(Long customerId, long start, long end);

    /**
     * 缓存中是否存在指定可用的优惠券
     * 
     * @param customerId
     * @return
     */
    Boolean existsUseFromCache(Long customerId);

    /**
     * 缓存中是否存在指定不可用的优惠券
     * 
     * @param customerId
     * @return
     */
    Boolean existsUnusedFromCache(Long customerId);
}
