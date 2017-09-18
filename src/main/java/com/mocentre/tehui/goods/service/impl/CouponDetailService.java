package com.mocentre.tehui.goods.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.core.service.support.datasource.annotation.DataSource;
import com.mocentre.tehui.core.service.support.paging.PageInfo;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.goods.dao.IGoodsDao;
import com.mocentre.tehui.goods.dao.IGoodsStorageDao;
import com.mocentre.tehui.goods.dao.impl.CouponDetailDao;
import com.mocentre.tehui.goods.enums.CouponRelateType;
import com.mocentre.tehui.goods.enums.CouponStatus;
import com.mocentre.tehui.goods.model.CouponDetail;
import com.mocentre.tehui.goods.model.Goods;
import com.mocentre.tehui.goods.model.GoodsStorage;
import com.mocentre.tehui.goods.service.ICouponDetailService;
import com.mocentre.tehui.ps.dao.ICustomerInfoDao;
import com.mocentre.tehui.ps.model.CustomerInfo;

/**
 * 优惠券接口实现 Created by yukaiji on 2016/11/24.
 */

@Component
public class CouponDetailService implements ICouponDetailService {

    @Autowired
    private CouponDetailDao  couponDetailDao;
    @Autowired
    private IGoodsDao        goodsDao;
    @Autowired
    private IGoodsStorageDao goodsStorageDao;
    @Autowired
    private ICustomerInfoDao customerInfoDao;

    @Override
    @DataSource(value = "read")
    public ListJsonResult<CouponDetail> queryCouponDetailPage(Requirement require) {
        return couponDetailDao.queryDatatablesPage(require);
    }

    @Override
    @DataSource(value = "read")
    public CouponDetail getCouponDetail(Long id) {
        return couponDetailDao.get(id);
    }

    @Override
    @DataSource(value = "read")
    public CouponDetail getCouponDetailBySn(String couponSn) {
        return couponDetailDao.getBySn(couponSn);
    }

    @Override
    @DataSource(value = "write")
    public int addCouponDetail(List<CouponDetail> couponDetailList) {
        return couponDetailDao.saveBatchEntity(couponDetailList);
    }

    @Override
    @DataSource(value = "write")
    public void deleteCouponDetail(String couponSn) {
        couponDetailDao.removeBySn(couponSn);
    }

    @Override
    @DataSource(value = "write")
    public void deleteAllCouponDetail(List<Long> couponIdList) {
        for (Long couponId : couponIdList) {
            couponDetailDao.removeByCid(couponId);
        }
    }

    @Override
    @DataSource(value = "write")
    public int updateCouponDetailCustomer(String couponSn, Long customerId) {
        return couponDetailDao.updateCustomer(couponSn, customerId);
    }

    @Override
    @DataSource(value = "read")
    public PageInfo<CouponDetail> getUnusedCouponDetailPage(Long customerId, Integer start, Integer length) {
        PageInfo<CouponDetail> pages = couponDetailDao.getUnusedPage(customerId, start, length);
        return pages;
    }

    @Override
    @DataSource(value = "read")
    public PageInfo<CouponDetail> getUsedCouponDetailPage(Long customerId, Integer start, Integer length) {
        PageInfo<CouponDetail> pages = couponDetailDao.getUsedPage(customerId, start, length);
        return pages;
    }

    @Override
    @DataSource(value = "read")
    public PageInfo<CouponDetail> getExpriedCouponDetailPage(Long customerId, Integer start, Integer length) {
        PageInfo<CouponDetail> pages = couponDetailDao.getExpiredPage(customerId, start, length);
        return pages;
    }

    @Override
    @DataSource(value = "read")
    public List<CouponDetail> getCouponDetailNotExpiredUnusedList(Long customerId) {
        return couponDetailDao.getNotExpiredUnusedList(customerId);
    }

    @Override
    @DataSource(value = "read")
    public CouponDetail queryUnusedCoupon(Long customerId, String couponSn) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("customerId", customerId);
        params.put("status", CouponStatus.UNUSED.getCodeValue());
        params.put("couponSn", couponSn);
        return couponDetailDao.queryUniquely(params);
    }

    @Override
    @DataSource(value = "read")
    public List<CouponDetail> getCouponDetailList(Long customerId, Long couponId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("customerId", customerId);
        params.put("couponId", couponId);
        return couponDetailDao.queryList(params);
    }

    @Override
    @DataSource(value = "read")
    public CouponDetail getOneUnReceiveCouponDetail(Long couponId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("couponId", couponId);
        return couponDetailDao.getOneByCoupon(couponId);
    }

    @Override
    @DataSource(value = "write")
    public int updateCouponDetailCustomerToCache(CouponDetail couponDetail, Long customerId,
                                                 List<Map<String, Object>> goodsMapList) {
        String couponSn = couponDetail.getCouponSn();
        int count = this.updateCouponDetailCustomer(couponSn, customerId);
        if (count > 0) {
            List<CouponDetail> cdList = new ArrayList<CouponDetail>();
            cdList.add(couponDetail);
            Map<String, List<CouponDetail>> classifyCouponMap = this.classifyCoupon(goodsMapList, cdList);
            List<CouponDetail> canUseCouponDetailList = classifyCouponMap.get("canUse");
            List<CouponDetail> noUseCouponDetailList = classifyCouponMap.get("notUse");
            if (canUseCouponDetailList != null && !canUseCouponDetailList.isEmpty()) {
                couponDetailDao.updateUseToCache(customerId, couponDetail);
            } else if (noUseCouponDetailList != null && !noUseCouponDetailList.isEmpty()) {
                couponDetailDao.updateUnUseToCache(customerId, couponDetail);
            }
        }
        return count;
    }

    @Override
    @DataSource(value = "write")
    public Integer updateCustomerCouponToCache(Long customerId, List<Map<String, Object>> goodsMapList) {
        List<CouponDetail> cdList = this.getCouponDetailNotExpiredUnusedList(customerId);
        Map<String, List<CouponDetail>> classifyCouponMap = this.classifyCoupon(goodsMapList, cdList);
        List<CouponDetail> canUseCouponDetailList = classifyCouponMap.get("canUse");
        List<CouponDetail> noUseCouponDetailList = classifyCouponMap.get("notUse");
        couponDetailDao.updateBatchToCache(customerId, canUseCouponDetailList, noUseCouponDetailList);
        return canUseCouponDetailList.size();
    }

    /**
     * 优惠券分类
     * 
     * @param goodsMapList
     * @param couponDetailList
     * @return 分类后的优惠券集合，指定key->canUse,notUse value->优惠券集合
     */
    private Map<String, List<CouponDetail>> classifyCoupon(List<Map<String, Object>> goodsMapList,
                                                           List<CouponDetail> couponDetailList) {
        List<CouponDetail> canUseCouponDetailList = new ArrayList<CouponDetail>();
        List<CouponDetail> noUseCouponDetailList = new ArrayList<CouponDetail>();
        Map<String, List<CouponDetail>> classifyCouponMap = new HashMap<String, List<CouponDetail>>();
        //商品总价格
        Long allPrice = 0l;
        //店铺id-商品总价格(针对店铺)
        Map<Long, Long> shopMap = new HashMap<Long, Long>();
        //商品id-商品总价格(针对商品)
        Map<Long, Long> goodsMap = new HashMap<Long, Long>();
        //商品分类id-商品总价格(针对分类)
        Map<Long, Long> categoryMap = new HashMap<Long, Long>();
        for (Map<String, Object> gdsMap : goodsMapList) {
            Long gdId = (Long) gdsMap.get("goodsId");
            String gdcode = (String) gdsMap.get("stcode");
            Integer gdNum = (Integer) gdsMap.get("buyNum");
            Goods goods = goodsDao.queryFromCache(gdId);
            GoodsStorage goodsStorage = goodsStorageDao.queryFromCache(gdId, gdcode, 0l);
            if (goods != null && goodsStorage != null) {
                Long shopId = goods.getShopId();
                Long categoryId = goods.getCategoryId();
                Long totalPrice = goodsStorage.getSalePrice() * gdNum;
                goodsMap.put(gdId, totalPrice);
                Long categoryPrice = categoryMap.get(categoryId);
                if (categoryPrice != null) {
                    categoryPrice += totalPrice;
                    categoryMap.put(categoryId, categoryPrice);
                } else {
                    categoryMap.put(categoryId, totalPrice);
                }
                Long shopPrice = shopMap.get(shopId);
                if (shopPrice != null) {
                    shopPrice += totalPrice;
                    shopMap.put(shopId, shopPrice);
                } else {
                    shopMap.put(shopId, totalPrice);
                }
                allPrice += totalPrice;
            }
        }
        //计算每张优惠券是否可用
        for (CouponDetail cd : couponDetailList) {
            String relateType = cd.getRelateType();
            String typeIds = cd.getTypeIds();
            Long fullCut = cd.getFullCut();
            if (relateType.equals(CouponRelateType.NO.getCodeValue())) {
                if (allPrice >= fullCut) {
                    canUseCouponDetailList.add(cd);
                } else {
                    noUseCouponDetailList.add(cd);
                }
            } else if (relateType.equals(CouponRelateType.GOODS.getCodeValue())) {
                boolean exist = true;
                String[] typeIdArr = typeIds.split(",");
                for (int i = 0; i < typeIdArr.length; i++) {
                    String typeId = typeIdArr[i];
                    Long price = goodsMap.get(Long.parseLong(typeId));
                    if (price != null && price >= fullCut) {
                        exist = true;
                        break;
                    } else {
                        exist = false;
                    }
                }
                if (exist) {
                    canUseCouponDetailList.add(cd);
                } else {
                    noUseCouponDetailList.add(cd);
                }
            } else if (relateType.equals(CouponRelateType.CATEGORY.getCodeValue())) {
                boolean exist = true;
                String[] typeIdArr = typeIds.split(",");
                for (int i = 0; i < typeIdArr.length; i++) {
                    String typeId = typeIdArr[i];
                    Long price = categoryMap.get(Long.parseLong(typeId));
                    if (price != null && price >= fullCut) {
                        break;
                    } else {
                        exist = false;
                    }
                }
                if (exist) {
                    canUseCouponDetailList.add(cd);
                } else {
                    noUseCouponDetailList.add(cd);
                }
            } else if (relateType.equals(CouponRelateType.SHOP.getCodeValue())) {
                Long shopId = Long.parseLong(typeIds);
                if (shopMap.containsKey(shopId) && shopMap.get(shopId) >= fullCut) {
                    canUseCouponDetailList.add(cd);
                } else {
                    noUseCouponDetailList.add(cd);
                }
            }
        }
        classifyCouponMap.put("canUse", canUseCouponDetailList);
        classifyCouponMap.put("notUse", noUseCouponDetailList);
        return classifyCouponMap;
    }

    @Override
    @DataSource(value = "read")
    public Long calcTotalPrice(CouponDetail couponDetail, List<Map<String, Object>> goodsMapList) {
        //商品总价格
        Long allPrice = 0l;
        //店铺id-商品总价格(针对店铺)
        Map<Long, Long> shopMap = new HashMap<Long, Long>();
        //商品id-商品总价格(针对商品)
        Map<Long, Long> goodsMap = new HashMap<Long, Long>();
        //商品分类id-商品总价格(针对分类)
        Map<Long, Long> categoryMap = new HashMap<Long, Long>();
        for (Map<String, Object> gdsMap : goodsMapList) {
            Long gdId = (Long) gdsMap.get("goodsId");
            String gdcode = (String) gdsMap.get("stcode");
            Integer gdNum = (Integer) gdsMap.get("buyNum");
            Goods goods = goodsDao.queryFromCache(gdId);
            GoodsStorage goodsStorage = goodsStorageDao.queryFromCache(gdId, gdcode, 0l);
            if (goods != null && goodsStorage != null) {
                Long shopId = goods.getShopId();
                Long categoryId = goods.getCategoryId();
                Long totalPrice = goodsStorage.getSalePrice() * gdNum;
                goodsMap.put(gdId, totalPrice);
                Long categoryPrice = categoryMap.get(categoryId);
                if (categoryPrice != null) {
                    categoryPrice += totalPrice;
                    categoryMap.put(categoryId, categoryPrice);
                } else {
                    categoryMap.put(categoryId, totalPrice);
                }
                Long shopPrice = shopMap.get(shopId);
                if (shopPrice != null) {
                    shopPrice += totalPrice;
                    shopMap.put(shopId, shopPrice);
                } else {
                    shopMap.put(shopId, totalPrice);
                }
                allPrice += totalPrice;
            }
        }
        //计算价格
        if (couponDetail != null) {
            String relateType = couponDetail.getRelateType();
            String typeIds = couponDetail.getTypeIds();
            Long fullCut = couponDetail.getFullCut();
            Long couponMoney = couponDetail.getCouponMoney();
            if (relateType.equals(CouponRelateType.NO.getCodeValue())) {
                if (allPrice >= fullCut) {
                    allPrice = allPrice - couponMoney;
                }
            } else if (relateType.equals(CouponRelateType.GOODS.getCodeValue())) {
                String[] typeIdArr = typeIds.split(",");
                for (int i = 0; i < typeIdArr.length; i++) {
                    String typeId = typeIdArr[i];
                    Long price = goodsMap.get(Long.parseLong(typeId));
                    if (price != null && price >= fullCut) {
                        if (price > couponMoney) {
                            allPrice = allPrice - couponMoney;
                        } else {
                            allPrice = allPrice - price;
                        }
                        break;
                    }
                }
            } else if (relateType.equals(CouponRelateType.CATEGORY.getCodeValue())) {
                String[] typeIdArr = typeIds.split(",");
                for (int i = 0; i < typeIdArr.length; i++) {
                    String typeId = typeIdArr[i];
                    Long price = categoryMap.get(Long.parseLong(typeId));
                    if (price != null && price >= fullCut) {
                        if (price > couponMoney) {
                            allPrice = allPrice - couponMoney;
                        } else {
                            allPrice = allPrice - price;
                        }
                        break;
                    }
                }
            } else if (relateType.equals(CouponRelateType.SHOP.getCodeValue())) {
                Long shopId = Long.parseLong(typeIds);
                Long price = shopMap.get(shopId);
                if (shopMap.containsKey(shopId) && price >= fullCut) {
                    if (price > couponMoney) {
                        allPrice = allPrice - couponMoney;
                    } else {
                        allPrice = allPrice - price;
                    }
                }
            }
        }
        if (allPrice < 0) {
            allPrice = 0l;
        }
        return allPrice;
    }

    @Override
    @DataSource(value = "read")
    public Map<String, Object> queryUseFromCache(Long customerId, long start, long end) {
        Map<String, Object> couponMap = new HashMap<String, Object>();
        List<CouponDetail> cdList = couponDetailDao.queryUseFromCache(customerId, start, end);
        Long count = couponDetailDao.countUseFromCache(customerId);
        couponMap.put("count", count);
        couponMap.put("couponDetailList", cdList);
        return couponMap;
    }

    @Override
    @DataSource(value = "read")
    public Map<String, Object> queryUnusedFromCache(Long customerId, long start, long end) {
        Map<String, Object> couponMap = new HashMap<String, Object>();
        List<CouponDetail> cdList = couponDetailDao.queryUnusedFromCache(customerId, start, end);
        Long count = couponDetailDao.countUnusedFromCache(customerId);
        couponMap.put("count", count);
        couponMap.put("couponDetailList", cdList);
        return couponMap;
    }

    @Override
    @DataSource(value = "write")
    public Long addOneCouponDetail(CouponDetail couponDetail) {
        couponDetailDao.saveEntity(couponDetail);
        return couponDetail.getId();
    }

    @Override
    @DataSource(value = "write")
    public int updateCouponDetailCustomer(String couponSn, String telephone) {
        CustomerInfo cumInfo = customerInfoDao.getByTelephone(telephone);
        if (cumInfo != null) {
            Long uid = cumInfo.getId();
            return couponDetailDao.updateCustomer(couponSn, uid);
        }
        return 0;
    }

}
