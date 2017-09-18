/*
 *
 *  * Copyright 2016 mocentre.com All right reserved. This software is the
 *  * confidential and proprietary information of mocentre.com ("Confidential
 *  * Information"). You shall not disclose such Confidential Information and shall
 *  * use it only in accordance with the terms of the license agreement you entered
 *  * into with mocentre.com .
 *
 */

package com.mocentre.tehui.goods.mapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.cglib.beans.BeanCopier;

import com.mocentre.tehui.backend.model.CouponDetailInstance;
import com.mocentre.tehui.backend.model.CouponInstance;
import com.mocentre.tehui.backend.param.CouponParam;
import com.mocentre.tehui.frontend.model.CouponDetailFTInstance;
import com.mocentre.tehui.frontend.model.CouponFTInstance;
import com.mocentre.tehui.frontend.model.CouponReceiveFTInstance;
import com.mocentre.tehui.goods.model.Coupon;
import com.mocentre.tehui.goods.model.CouponDetail;

/**
 * Created by Arvin on 16/12/12.
 */
public class CouponMapper {

    public static CouponInstance toInstance(Coupon coupon) {
        CouponInstance couponInstance = new CouponInstance();
        BeanCopier copier = BeanCopier.create(Coupon.class, CouponInstance.class, false);
        copier.copy(coupon, couponInstance, null);
        if (coupon.getCouponMoney() != null) {
            couponInstance.setCouponMoney(new BigDecimal(coupon.getCouponMoney()).divide(new BigDecimal(100)));
        }
        if (coupon.getFullCut() != null) {
            couponInstance.setFullCut(new BigDecimal(coupon.getFullCut()).divide(new BigDecimal(100)));
        }
        return couponInstance;
    }

    public static List<CouponInstance> toInstanceList(List<Coupon> lists) {
        List<CouponInstance> listResult = new ArrayList<CouponInstance>();
        if (lists.isEmpty()) {
            return listResult;
        }
        for (Coupon coupon : lists) {
            CouponInstance categoryInstance = CouponMapper.toInstance(coupon);
            listResult.add(categoryInstance);
        }
        return listResult;
    }

    public static CouponDetailInstance toDetailInstance(CouponDetail couponDetail) {
        CouponDetailInstance couponDetailInstance = new CouponDetailInstance();
        BeanCopier copier = BeanCopier.create(CouponDetail.class, CouponDetailInstance.class, false);
        copier.copy(couponDetail, couponDetailInstance, null);
        if (couponDetail.getCouponMoney() != null) {
            couponDetailInstance.setCouponMoney(new BigDecimal(couponDetail.getCouponMoney())
                    .divide(new BigDecimal(100)));
        }
        if (couponDetail.getFullCut() != null) {
            couponDetailInstance.setFullCut(new BigDecimal(couponDetail.getFullCut()).divide(new BigDecimal(100)));
        }
        return couponDetailInstance;
    }

    public static List<CouponDetailInstance> toDetailInstanceList(List<CouponDetail> lists) {
        if (lists == null) {
            return null;
        }
        List<CouponDetailInstance> listResult = new ArrayList<CouponDetailInstance>();
        if (lists.isEmpty()) {
            return listResult;
        }
        for (CouponDetail coupon : lists) {
            CouponDetailInstance couponDetailInstance = CouponMapper.toDetailInstance(coupon);
            listResult.add(couponDetailInstance);
        }
        return listResult;
    }

    /**
     * 前台优惠券详细信息转换
     * 
     * @param couponDetail
     * @return
     */
    public static CouponDetailFTInstance toDetailFTInstance(CouponDetail couponDetail) {
        CouponDetailFTInstance couponDetailFTInstance = new CouponDetailFTInstance();
        BeanCopier copier = BeanCopier.create(CouponDetail.class, CouponDetailFTInstance.class, false);
        copier.copy(couponDetail, couponDetailFTInstance, null);
        if (couponDetail.getCouponMoney() != null) {
            couponDetailFTInstance.setCouponMoney(new BigDecimal(couponDetail.getCouponMoney()).divide(new BigDecimal(
                    100)));
        }
        if (couponDetail.getFullCut() != null) {
            couponDetailFTInstance.setFullCut(new BigDecimal(couponDetail.getFullCut()).divide(new BigDecimal(100)));
        }
        return couponDetailFTInstance;
    }

    /**
     * 前台优惠券详情列表转换
     * 
     * @param list
     * @return
     */
    public static List<CouponDetailFTInstance> toDetailFTInstanceList(List<CouponDetail> list) {
        List<CouponDetailFTInstance> listResult = new ArrayList<CouponDetailFTInstance>();
        if (list == null || list.isEmpty()) {
            return listResult;
        }
        for (CouponDetail coupon : list) {
            CouponDetailFTInstance couponDetailFTInstance = CouponMapper.toDetailFTInstance(coupon);
            listResult.add(couponDetailFTInstance);
        }
        return listResult;
    }

    /**
     * 前台优惠券信息接口转换
     * 
     * @param coupon
     * @return
     */
    public static CouponFTInstance toCouponFTInstance(Coupon coupon) {
        CouponFTInstance couponFTInstance = new CouponFTInstance();
        BeanCopier copier = BeanCopier.create(Coupon.class, CouponFTInstance.class, false);
        copier.copy(coupon, couponFTInstance, null);
        if (coupon.getCouponMoney() != null) {
            couponFTInstance.setCouponMoney(new BigDecimal(coupon.getCouponMoney()).divide(new BigDecimal(100)));
        }
        if (coupon.getFullCut() != null) {
            couponFTInstance.setFullCut(new BigDecimal(coupon.getFullCut()).divide(new BigDecimal(100)));
        }
        return couponFTInstance;
    }

    public static Coupon toCoupon(CouponParam couponParam) {
        Coupon coupon = new Coupon();
        BeanCopier bc = BeanCopier.create(CouponParam.class, Coupon.class, false);
        bc.copy(couponParam, coupon, null);
        if (couponParam.getCouponMoney() != null) {
            coupon.setCouponMoney(couponParam.getCouponMoney().multiply(new BigDecimal(100)).longValue());
        } else {
            coupon.setCouponMoney(0l);
        }
        if (couponParam.getFullCut() != null) {
            coupon.setFullCut(couponParam.getFullCut().multiply(new BigDecimal(100)).longValue());
        } else {
            coupon.setFullCut(0l);
        }
        if (couponParam.getCouponNum() == null) {
            coupon.setCouponNum(0);
        }
        return coupon;
    }

    public static CouponReceiveFTInstance toCouponReceiveFTInstance(Coupon coupon) {
        CouponReceiveFTInstance cr = new CouponReceiveFTInstance();
        cr.setId(coupon.getId());
        cr.setCouponDes(coupon.getCouponDes());
        cr.setFullCut(new BigDecimal(coupon.getFullCut()).divide(new BigDecimal(100)));
        cr.setCouponMoney(new BigDecimal(coupon.getCouponMoney()).divide(new BigDecimal(100)));
        cr.setBeginTime(coupon.getBeginTime());
        cr.setEndTime(coupon.getEndTime());
        return cr;
    }

}
