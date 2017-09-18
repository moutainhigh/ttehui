package com.mocentre.tehui.goods.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mocentre.tehui.common.constant.RedisKeyConstant;
import com.mocentre.tehui.core.dao.BaseRedisDao;
import com.mocentre.tehui.core.service.support.paging.PageInfo;
import com.mocentre.tehui.goods.dao.ICouponDetailDao;
import com.mocentre.tehui.goods.enums.CouponStatus;
import com.mocentre.tehui.goods.model.CouponDetail;

/**
 * 优惠券详情Dao接口实现 Created by yukaiji on 2016/11/24.
 */
@Repository
public class CouponDetailDao extends BaseRedisDao<CouponDetail, String, CouponDetail> implements ICouponDetailDao {

    @Override
    public PageInfo<CouponDetail> getUsedPage(Long customerId, Integer start, Integer length) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("customerId", customerId);
        paramMap.put("status", CouponStatus.USED.getCodeValue());
        paramMap.put("notExpired", true);
        paramMap.put("orderColumn", "end_time");
        paramMap.put("orderBy", "asc");
        paramMap.put("start", start);
        paramMap.put("length", length);
        return super.queryPaged(paramMap);
    }

    @Override
    public PageInfo<CouponDetail> getUnusedPage(Long customerId, Integer start, Integer length) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("customerId", customerId);
        paramMap.put("status", CouponStatus.UNUSED.getCodeValue());
        paramMap.put("notExpired", true);
        paramMap.put("orderColumn", "end_time");
        paramMap.put("orderBy", "asc");
        paramMap.put("start", start);
        paramMap.put("length", length);
        return super.queryPaged(paramMap);
    }

    @Override
    public PageInfo<CouponDetail> getExpiredPage(Long customerId, Integer start, Integer length) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("customerId", customerId);
        paramMap.put("expired", true);
        paramMap.put("orderColumn", "end_time");
        paramMap.put("orderBy", "asc");
        paramMap.put("start", start);
        paramMap.put("length", length);
        return super.queryPaged(paramMap);
    }

    @Override
    public CouponDetail getOneByCoupon(Long couponId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("couponId", couponId);
        return super.queryUniquely("CouponDetail_one", paramMap);
    }

    @Override
    public int saveBatchEntity(List<CouponDetail> couponList) {
        return super.insert("CouponDetail_insert_batch", couponList);
    }

    @Override
    public int removeByCid(Long couponId) {
        CouponDetail o = new CouponDetail();
        o.setCouponId(couponId);
        return super.delete("CouponDetail_delete_bycid", o);
    }

    @Override
    public int removeBySn(String couponSn) {
        CouponDetail o = new CouponDetail();
        o.setCouponSn(couponSn);
        return super.delete("CouponDetail_delete_bycsn", o);
    }

    @Override
    public int updateStatusUse(String couponSn, Long customerId) {
        CouponDetail o = new CouponDetail();
        o.setCouponSn(couponSn);
        o.setStatus(CouponStatus.USED.getCodeValue());
        o.setUseTime(new Date());
        o.setCustomerId(customerId);
        return super.update("CouponDetail_update_use_bysn", o);
    }

    @Override
    public int updateCustomer(String couponSn, Long customerId) {
        CouponDetail o = new CouponDetail();
        o.setCouponSn(couponSn);
        o.setCustomerId(customerId);
        o.setStatus(CouponStatus.UNUSED.getCodeValue());
        o.setReceiveTime(new Date());
        return super.update("CouponDetail_update_cum_bysn", o);
    }

    @Override
    public int updateByCouponId(Long couponId, Date beginTime, Date endTime, Long couponMoney, Long fullCut,
                                String couponDes) {
        CouponDetail o = new CouponDetail();
        o.setCouponId(couponId);
        o.setBeginTime(beginTime);
        o.setEndTime(endTime);
        o.setCouponMoney(couponMoney);
        o.setFullCut(fullCut);
        o.setCouponDes(couponDes);
        return super.update("CouponDetail_update_detail_bycid", o);
    }

    @Override
    public List<CouponDetail> getNotExpiredUnusedList(Long customerId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("customerId", customerId);
        params.put("status", CouponStatus.UNUSED.getCodeValue());
        params.put("notExpired", true);
        return super.queryList(params);
    }

    @Override
    public CouponDetail getBySn(String couponSn) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("couponSn", couponSn);
        return super.queryUniquely(paramMap);
    }

    @Override
    public void updateBatchToCache(Long customerId, List<CouponDetail> canUseCouponDetailList,
                                   List<CouponDetail> noUseCouponDetailList) {
        String useKey = RedisKeyConstant.COUPON_USE_LIST + ":" + customerId;
        String nouseKey = RedisKeyConstant.COUPON_NOUSE_LIST + ":" + customerId;
        Long time = (long) (30 * 60);
        removeCacheList(useKey);
        removeCacheList(nouseKey);
        if (canUseCouponDetailList != null && !canUseCouponDetailList.isEmpty()) {
            cacheListR(useKey, canUseCouponDetailList, time);
        }
        if (noUseCouponDetailList != null && !noUseCouponDetailList.isEmpty()) {
            cacheListR(nouseKey, noUseCouponDetailList, time);
        }
    }

    @Override
    public void updateUseToCache(Long customerId, CouponDetail couponDetail) {
        String useKey = RedisKeyConstant.COUPON_USE_LIST + ":" + customerId;
        Long time = (long) (30 * 60);
        boolean exist = super.containsListKey(useKey);
        if (!exist) {
            super.cacheListR(useKey, couponDetail, time);
        } else {
            super.cacheListR(useKey, couponDetail);
        }
    }

    @Override
    public void updateUnUseToCache(Long customerId, CouponDetail couponDetail) {
        String nouseKey = RedisKeyConstant.COUPON_NOUSE_LIST + ":" + customerId;
        Long time = (long) (30 * 60);
        boolean exist = super.containsListKey(nouseKey);
        if (!exist) {
            super.cacheListR(nouseKey, couponDetail, time);
        } else {
            super.cacheListR(nouseKey, couponDetail);
        }
    }

    @Override
    public long countUseFromCache(Long customerId) {
        String useKey = RedisKeyConstant.COUPON_USE_LIST + ":" + customerId;
        return getCacheListSize(useKey);
    }

    @Override
    public long countUnusedFromCache(Long customerId) {
        String useKey = RedisKeyConstant.COUPON_NOUSE_LIST + ":" + customerId;
        return getCacheListSize(useKey);
    }

    @Override
    public List<CouponDetail> queryUseFromCache(Long customerId, long start, long end) {
        String useKey = RedisKeyConstant.COUPON_USE_LIST + ":" + customerId;
        return getCacheList(useKey, start, end);
    }

    @Override
    public List<CouponDetail> queryUnusedFromCache(Long customerId, long start, long end) {
        String useKey = RedisKeyConstant.COUPON_NOUSE_LIST + ":" + customerId;
        return getCacheList(useKey, start, end);
    }

    @Override
    public Boolean existsUseFromCache(Long customerId) {
        String useKey = RedisKeyConstant.COUPON_USE_LIST + ":" + customerId;
        return containsListKey(useKey);
    }

    @Override
    public Boolean existsUnusedFromCache(Long customerId) {
        String useKey = RedisKeyConstant.COUPON_NOUSE_LIST + ":" + customerId;
        return containsListKey(useKey);
    }

}
