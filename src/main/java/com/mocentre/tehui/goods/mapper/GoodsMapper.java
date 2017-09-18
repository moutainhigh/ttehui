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
import java.util.Date;
import java.util.List;

import org.springframework.cglib.beans.BeanCopier;

import com.mocentre.tehui.act.model.ActivityGoods;
import com.mocentre.tehui.backend.model.GoodsAdvancedInstance;
import com.mocentre.tehui.backend.model.GoodsInstance;
import com.mocentre.tehui.backend.model.GoodsLaunchInstance;
import com.mocentre.tehui.backend.model.GoodsParamInstance;
import com.mocentre.tehui.backend.model.GoodsShareInstance;
import com.mocentre.tehui.backend.model.GoodsStorageInstance;
import com.mocentre.tehui.backend.param.GoodsBaseInfoParam;
import com.mocentre.tehui.frontend.model.ActGoodsDetailFTInstance;
import com.mocentre.tehui.frontend.model.GoodsDetailFTInstance;
import com.mocentre.tehui.frontend.model.GoodsDetailWithStorageAndPriceFTInstance;
import com.mocentre.tehui.frontend.model.GoodsListPageInfoFTInstance;
import com.mocentre.tehui.frontend.model.GoodsStorageAndPriceFTInstance;
import com.mocentre.tehui.goods.enums.GoodsCheckedType;
import com.mocentre.tehui.goods.enums.GoodsShowType;
import com.mocentre.tehui.goods.model.Goods;
import com.mocentre.tehui.goods.model.GoodsLaunch;
import com.mocentre.tehui.goods.model.GoodsParam;
import com.mocentre.tehui.goods.model.GoodsShare;
import com.mocentre.tehui.goods.model.GoodsStorage;

/**
 * Created by Arvin on 16/12/12.
 */
public class GoodsMapper {

    public static GoodsInstance toInstance(Goods goods) {
        GoodsInstance goodsInstance = new GoodsInstance();
        BeanCopier copier = BeanCopier.create(Goods.class, GoodsInstance.class, false);
        copier.copy(goods, goodsInstance, null);
        goodsInstance.setSellLowPrice(goods.getSellLowPrice() == null ? null : new BigDecimal(goods.getSellLowPrice())
                .divide(new BigDecimal(100)));
        goodsInstance.setOldLowPrice(goods.getOldLowPrice() == null ? null : new BigDecimal(goods.getOldLowPrice())
                .divide(new BigDecimal(100)));
        return goodsInstance;
    }

    public static List<GoodsInstance> toInstanceList(List<Goods> lists) {
        List<GoodsInstance> listResult = new ArrayList<GoodsInstance>();
        if (lists == null || lists.size() < 1) {
            return listResult;
        }
        for (Goods good : lists) {
            GoodsInstance goodsInstance = GoodsMapper.toInstance(good);
            listResult.add(goodsInstance);
        }
        return listResult;
    }

    public static GoodsAdvancedInstance toAdvanceInstance(Goods goods, List<GoodsStorage> storageList,
                                                          List<GoodsParam> paramList, GoodsShare share,
                                                          List<GoodsLaunch> launchList) {
        GoodsAdvancedInstance goodsAdvancedInstance = new GoodsAdvancedInstance();
        if (goods != null) {
            GoodsInstance goodsInstance = GoodsMapper.toInstance(goods);
            goodsAdvancedInstance.setGoods(goodsInstance);
        }
        if (storageList != null) {
            List<GoodsStorageInstance> storageInsList = new ArrayList<GoodsStorageInstance>();
            for (GoodsStorage storage : storageList) {
                GoodsStorageInstance storageIns = GoodsStorageMapper.toGoodsStorageIns(storage);
                storageInsList.add(storageIns);
            }
            goodsAdvancedInstance.setStorageList(storageInsList);
        }
        if (paramList != null) {
            List<GoodsParamInstance> paramInsList = new ArrayList<GoodsParamInstance>();
            for (GoodsParam param : paramList) {
                GoodsParamInstance paramIns = GoodsParamMapper.toInstance(param);
                paramInsList.add(paramIns);
            }
            goodsAdvancedInstance.setParamList(paramInsList);
        }
        if (share != null) {
            GoodsShareInstance shareIns = GoodsShareMapper.toInstance(share);
            goodsAdvancedInstance.setShare(shareIns);
        }
        if (launchList != null) {
            List<GoodsLaunchInstance> launchInsList = new ArrayList<GoodsLaunchInstance>();
            for (GoodsLaunch launch : launchList) {
                GoodsLaunchInstance launchIns = GoodsLaunchMapper.toInstance(launch);
                launchInsList.add(launchIns);
            }
            goodsAdvancedInstance.setLaunchList(launchInsList);
        }
        return goodsAdvancedInstance;
    }

    public static GoodsDetailFTInstance toGoodsDetailFTInstance(Goods goods) {
        GoodsDetailFTInstance gd = new GoodsDetailFTInstance();
        gd.setId(goods.getId());
        gd.setTitle(goods.getTitle());
        gd.setDescribe(goods.getDescribe());
        gd.setSellPrice(goods.getSellPrice());
        gd.setOldPrice(goods.getOldPrice());
        gd.setJdPrice(goods.getJdPrice());
        gd.setTmPrice(goods.getTmPrice());
        gd.setStoreTotal(goods.getStoreTotal());
        gd.setStandard(goods.getStandard());
        gd.setImgBanner(goods.getImgBanner());
        gd.setDetails(goods.getDetails());
        return gd;
    }

    public static GoodsListPageInfoFTInstance toGoodsListPageInfoFTInstance(Goods goods) {
        GoodsListPageInfoFTInstance instance = new GoodsListPageInfoFTInstance();
        instance.setId(goods.getId());
        instance.setShopId(goods.getShopId());
        instance.setDescribe(goods.getDescribe());
        instance.setImg(goods.getImgListPage());
        instance.setOldPrice(goods.getOldPrice());
        instance.setSellPrice(goods.getSellPrice());
        instance.setTitle(goods.getTitle());
        return instance;
    }

    public static List<GoodsListPageInfoFTInstance> toGoodsListPageInfoFTInstanceList(List<Goods> goodsList) {
        List<GoodsListPageInfoFTInstance> resultList = new ArrayList<>();
        for (Goods g : goodsList) {
            resultList.add(toGoodsListPageInfoFTInstance(g));
        }
        return resultList;
    }

    public static Goods toGoods(GoodsBaseInfoParam param) {
        Goods goods = new Goods();
        goods.setId(param.getId());
        goods.setShopId(param.getShopId());
        goods.setTitle(param.getTitle());
        goods.setJdPrice(param.getJdPrice());
        goods.setTmPrice(param.getTmPrice());
        goods.setCategoryId(param.getCategoryId());
        goods.setDescribe(param.getDescribe());
        goods.setFreightMould(param.getFreightMould());
        goods.setId(param.getId());
        goods.setImgCart(param.getImgCart());
        goods.setImgListPage(param.getImgListPage());
        if ("onShelf".equals(param.getStatus())) {
            goods.setIsShow(GoodsShowType.ONSHELF.getCodeValue());
        } else if ("offShelf".equals(param.getStatus())) {
            goods.setIsShow(GoodsShowType.OFFSHELF.getCodeValue());
        } else if ("pass".equals(param.getStatus())) {
            goods.setIsChecked(GoodsCheckedType.CHECKPASS.getCodeValue());
        } else if ("notPassed".equals(param.getStatus())) {
            goods.setIsChecked(GoodsCheckedType.CHECKFAIL.getCodeValue());
        }
        goods.setIsLimitBuy(param.getIsLimitBuy());
        goods.setLimitNums(param.getLimitNums());
        goods.setScore(param.getScore());
        goods.setShopSorting(param.getShopSorting());
        return goods;
    }

    public static ActGoodsDetailFTInstance toActGoodsDetailFTInstance(ActivityGoods activityGoods, Goods goods) {
        ActGoodsDetailFTInstance instance = new ActGoodsDetailFTInstance();
        instance.setDescribe(goods.getDescribe());
        instance.setDetails(goods.getDetails());
        instance.setFreight(goods.getFreightMould());
        instance.setImgBanner(goods.getImgBanner());
        instance.setGoodsId(goods.getId());
        instance.setOldPrice(goods.getSellPrice());
        instance.setStandard(goods.getStandard());
        instance.setStoreTotal(goods.getStoreTotal());
        instance.setJdPrice(goods.getJdPrice());
        instance.setTmPrice(goods.getTmPrice());
        instance.setTagline(activityGoods.getTagline());
        instance.setGrouponNum(activityGoods.getGrouponNum());
        instance.setActivityId(activityGoods.getActivityId());
        instance.setActGoodsId(activityGoods.getId());
        instance.setEndTime(activityGoods.getEndTime());
        instance.setStartTime(activityGoods.getBeginTime());
        instance.setTitle(activityGoods.getGoodsName());
        instance.setSellPrice(activityGoods.getSellPrice());
        return instance;
    }

    public static List<GoodsParamInstance> toGoodsParamInstanceList(List<GoodsParam> paramList) {
        List<GoodsParamInstance> resultList = new ArrayList<>();
        for (GoodsParam gp : paramList) {
            resultList.add(toGoodsParamInstance(gp));
        }
        return resultList;
    }

    private static GoodsParamInstance toGoodsParamInstance(GoodsParam gp) {
        GoodsParamInstance instance = new GoodsParamInstance();
        instance.setGoodsId(gp.getGoodsId());
        instance.setShopId(gp.getShopId());
        instance.setGoodsKey(gp.getGoodsKey());
        instance.setGoodsVal(gp.getGoodsVal());
        instance.setTop(gp.isTop());
        instance.setId(gp.getId());
        return instance;
    }

    public static GoodsStorageAndPriceFTInstance toGoodsStorageAndPriceFTInstance(GoodsStorage goodsStorage) {
        GoodsStorageAndPriceFTInstance instance = new GoodsStorageAndPriceFTInstance();
        instance.setId(goodsStorage.getId());
        instance.setDescribe(goodsStorage.getDescribe());
        instance.setGoodsId(goodsStorage.getGoodsId());
        instance.setStorage(goodsStorage.getStockNum());
        instance.setCode(goodsStorage.getStandardCode());
        if (null != goodsStorage.getSalePrice()) {
            instance.setPrice(new BigDecimal(goodsStorage.getSalePrice()).divide(new BigDecimal(100)));
        } else {
            instance.setPrice(new BigDecimal(0));
        }
        return instance;
    }

    public static GoodsDetailWithStorageAndPriceFTInstance toGoodsDetailWithStorageAndPriceFTInstance(Goods goods,
                                                                                                      List<GoodsStorage> goodsStorageList,
                                                                                                      GoodsShare goodsShare,
                                                                                                      ActivityGoods actGoods) {
        GoodsDetailWithStorageAndPriceFTInstance instance = new GoodsDetailWithStorageAndPriceFTInstance();
        BeanCopier copier = BeanCopier.create(Goods.class, GoodsDetailWithStorageAndPriceFTInstance.class, false);
        copier.copy(goods, instance, null);
        Long storeTotal = 0l;
        List<GoodsStorageAndPriceFTInstance> gspFTInsList = new ArrayList<GoodsStorageAndPriceFTInstance>();
        if (goodsStorageList != null && goodsStorageList.size() > 0) {
            for (GoodsStorage goodsStorage : goodsStorageList) {
                storeTotal += goodsStorage.getStockNum();
                gspFTInsList.add(toGoodsStorageAndPriceFTInstance(goodsStorage));
            }
        }
        instance.setStorageAndPriceList(gspFTInsList);
        if (goodsShare != null) {
            GoodsShareInstance goodsShareIns = GoodsShareMapper.toInstance(goodsShare);
            instance.setGoodsShare(goodsShareIns);
        }
        if (actGoods != null) {
            Date beginTime = actGoods.getBeginTime();
            Date endTime = actGoods.getEndTime();
            Date nowTime = new Date();
            instance.setBeginTime(beginTime);
            instance.setEndTime(endTime);
            if (nowTime.before(beginTime)) {
                instance.setActStatus("notStarted");
            } else if (!nowTime.before(beginTime) && !nowTime.after(endTime)) {
                instance.setActStatus("progressing");
            } else {
                instance.setActStatus("end");
            }
            instance.setActGoodsId(actGoods.getId());
            instance.setSellPrice(actGoods.getSellPrice());
            instance.setOldPrice(actGoods.getOldPrice());
        }
        instance.setStoreTotal(storeTotal);
        return instance;
    }

}
