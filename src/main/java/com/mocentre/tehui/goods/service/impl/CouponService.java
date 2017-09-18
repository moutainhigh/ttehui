package com.mocentre.tehui.goods.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.common.utils.CommUtil;
import com.mocentre.tehui.core.service.support.datasource.annotation.DataSource;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.goods.dao.ICouponDao;
import com.mocentre.tehui.goods.dao.ICouponDetailDao;
import com.mocentre.tehui.goods.enums.CouponRelateType;
import com.mocentre.tehui.goods.enums.CouponStatus;
import com.mocentre.tehui.goods.model.Coupon;
import com.mocentre.tehui.goods.model.CouponDetail;
import com.mocentre.tehui.goods.service.ICouponService;

/**
 * 优惠券接口实现 Created by yukaiji on 2016/11/24.
 */

@Component
public class CouponService implements ICouponService {

    @Autowired
    private ICouponDao       couponDao;
    @Autowired
    private ICouponDetailDao couponDetailDao;

    @Override
    @DataSource(value = "read")
    public ListJsonResult<Coupon> queryCouponPage(Requirement require) {
        return couponDao.queryDatatablesPage(require);
    }

    @Override
    @DataSource(value = "read")
    public Coupon getCouponById(Long id) {
        return couponDao.get(id);
    }

    @Override
    @DataSource(value = "read")
    public Coupon getCoupon(Long id, Long shopId) {
        return couponDao.queryByIdShop(id, shopId);
    }

    @Override
    @DataSource(value = "read")
    public List<Coupon> getNotExpiredAndNotOuterCouponList() {
        return couponDao.getInnerNotExpiredList();
    }

    @Override
    @DataSource(value = "write")
    public Coupon addCoupon(Coupon coupon) {
        String rType = coupon.getRelateType();
        if (CouponRelateType.NO.getCodeValue().equals(rType)) {
            coupon.setTypeIds(null);
        } else if (CouponRelateType.SHOP.getCodeValue().equals(rType)) {
            coupon.setTypeIds(coupon.getShopId().toString());
        }
        couponDao.saveEntity(coupon);
        if (coupon.getIsLimit() == 1) {
            Integer couponNum = coupon.getCouponNum();
            this.saveCouponToDetail(coupon, couponNum, true);
        }
        return coupon;
    }

    @Override
    @DataSource(value = "write")
    public Long updateCouponAndDetail(Coupon coupon) {
        Long count = 0l;
        Coupon c = this.getCoupon(coupon.getId(), coupon.getShopId());
        if (c != null) {
            Integer couponNum = coupon.getCouponNum();
            if (couponNum != null && couponNum > 0) {
                coupon.setCouponNum(c.getCouponNum() + coupon.getCouponNum());
            } else {
                coupon.setCouponNum(c.getCouponNum());
            }
            count = couponDao.updateEntity(coupon);
            if (count > 0 && c.getIsLimit() == 1) {
                Date beginTime = coupon.getBeginTime();
                Date endTime = coupon.getEndTime();
                Long couponMoney = coupon.getCouponMoney();
                Long fullCut = coupon.getFullCut();
                String couponDes = coupon.getCouponDes();
                c.setBeginTime(beginTime);
                c.setEndTime(endTime);
                c.setCouponMoney(couponMoney);
                c.setFullCut(fullCut);
                couponDetailDao.updateByCouponId(c.getId(), beginTime, endTime, couponMoney, fullCut, couponDes);
                if (couponNum != null && couponNum > 0) {
                    this.saveCouponToDetail(c, couponNum, false);
                }
            }
        }
        return count;
    }

    /**
     * 保存优惠券
     * 
     * @param coupon
     * @param addNum 新增的数量
     * @param isAdd true：新增
     */
    private void saveCouponToDetail(Coupon coupon, Integer addNum, Boolean isAdd) {
        List<CouponDetail> couponDetailList = new ArrayList<CouponDetail>();
        for (int i = 0; i < addNum; i++) {
            CouponDetail detail = new CouponDetail();
            detail.setCouponId(coupon.getId());
            if (coupon.getSnType() == 1) {
                detail.setCouponSn(CommUtil.generateShortUUID());
            } else if (coupon.getSnType() == 2) {
                Long sn = 0l;
                if (isAdd) {
                    sn = coupon.getFirstNum() + i;
                } else {
                    sn = coupon.getCouponNum() + coupon.getFirstNum() + i;
                }
                detail.setCouponSn(sn + "");
            }
            detail.setCouponMoney(coupon.getCouponMoney());
            detail.setCouponDes(coupon.getCouponDes());
            detail.setBeginTime(coupon.getBeginTime());
            detail.setEndTime(coupon.getEndTime());
            detail.setStatus(CouponStatus.UNUSED.getCodeValue());
            detail.setFullCut(coupon.getFullCut());
            detail.setRelateType(coupon.getRelateType());
            detail.setTypeIds(coupon.getTypeIds());
            couponDetailList.add(detail);
            if (i % 5000 == 0) {
                couponDetailDao.saveBatchEntity(couponDetailList);
                couponDetailList.clear();
            } else if (i == addNum - 1) {
                couponDetailDao.saveBatchEntity(couponDetailList);
            }
        }
    }

    @Override
    @DataSource(value = "write")
    public void deleteCoupon(List<Long> idList) {
        for (Long id : idList) {
            couponDao.removeById(id);
        }
    }

}
