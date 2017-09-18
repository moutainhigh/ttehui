/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.act.mapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.mocentre.tehui.act.model.ActivityGoods;
import com.mocentre.tehui.backend.model.ActivityGoodsInstance;
import com.mocentre.tehui.backend.param.ActivityGoodsParam;
import com.mocentre.tehui.core.utils.DateUtils;
import com.mocentre.tehui.frontend.model.ActivityGoodsFTInstance;

/**
 * 活动与商品关联信息转换
 * 
 * @author Created by yukaiji on 2016年1月16日
 */
public class ActivityGoodsMapper {

    /**
     * 转前台返回实体类
     */
    public static ActivityGoodsInstance toActivityGoodsInstance(ActivityGoods activityGoods) {
        ActivityGoodsInstance activityIns = new ActivityGoodsInstance();
        activityIns.setActivityId(activityGoods.getActivityId());
        activityIns.setGoodsId(activityGoods.getGoodsId());
        activityIns.setGoodsImg(activityGoods.getGoodsImg());
        activityIns.setGoodsName(activityGoods.getGoodsName());
        activityIns.setId(activityGoods.getId());
        activityIns.setOldPrice(activityGoods.getOldPrice());
        activityIns.setSellPrice(activityGoods.getSellPrice());
        activityIns.setTagline(activityGoods.getTagline());
        activityIns.setGrouponNum(activityGoods.getGrouponNum());
        activityIns.setBeginTime(activityGoods.getBeginTime());
        activityIns.setEndTime(activityGoods.getEndTime());
        activityIns.setActualUrl(activityGoods.getActualUrl());
        activityIns.setShortUrl(activityGoods.getShortUrl());
        activityIns.setGmtCreated(activityGoods.getGmtCreated());
        activityIns.setGmtModified(activityGoods.getGmtModified());
        Long sellLowPrice = activityGoods.getSellLowPrice();
        if (sellLowPrice != null) {
            activityIns.setSellLowPrice(new BigDecimal(sellLowPrice).divide(new BigDecimal(100)));
        }
        Long oldLowPrice = activityGoods.getOldLowPrice();
        if (oldLowPrice != null) {
            activityIns.setOldLowPrice(new BigDecimal(oldLowPrice).divide(new BigDecimal(100)));
        }
        return activityIns;
    }

    public static ActivityGoodsFTInstance toActivityGoodsFTInstance(ActivityGoods activityGoods) {
        ActivityGoodsFTInstance activityInsFT = new ActivityGoodsFTInstance();
        activityInsFT.setActivityId(activityGoods.getActivityId());
        activityInsFT.setGoodsId(activityGoods.getGoodsId());
        activityInsFT.setGoodsImg(activityGoods.getGoodsImg());
        activityInsFT.setGoodsName(activityGoods.getGoodsName());
        activityInsFT.setId(activityGoods.getId());
        activityInsFT.setOldPrice(activityGoods.getOldPrice());
        activityInsFT.setSellPrice(activityGoods.getSellPrice());
        activityInsFT.setTagline(activityGoods.getTagline());
        activityInsFT.setGrouponNum(activityGoods.getGrouponNum());
        activityInsFT.setBeginTime(activityGoods.getBeginTime());
        activityInsFT.setEndTime(activityGoods.getEndTime());
        activityInsFT.setActualUrl(activityGoods.getActualUrl());
        activityInsFT.setShortUrl(activityGoods.getShortUrl());
        activityInsFT.setGmtCreated(activityGoods.getGmtCreated());
        activityInsFT.setGmtModified(activityGoods.getGmtModified());
        Long sellLowPrice = activityGoods.getSellLowPrice();
        if (sellLowPrice != null) {
            activityInsFT.setSellLowPrice(new BigDecimal(sellLowPrice).divide(new BigDecimal(100)));
        }
        Long oldLowPrice = activityGoods.getOldLowPrice();
        if (oldLowPrice != null) {
            activityInsFT.setOldLowPrice(new BigDecimal(oldLowPrice).divide(new BigDecimal(100)));
        }
        return activityInsFT;
    }

    /**
     * 参数转活动与商品信息详情
     */
    public static ActivityGoods toActivityGoods(ActivityGoodsParam activityGoodsParam) {
        ActivityGoods activityGoods = new ActivityGoods();
        activityGoods.setActivityId(activityGoodsParam.getActivityId());
        activityGoods.setGoodsId(activityGoodsParam.getGoodsId());
        activityGoods.setGoodsImg(activityGoodsParam.getGoodsImg());
        activityGoods.setGoodsName(activityGoodsParam.getGoodsName());
        activityGoods.setId(activityGoodsParam.getId());
        activityGoods.setTagline(activityGoodsParam.getTagline());
        activityGoods.setGrouponNum(activityGoodsParam.getGrouponNum());
        if (activityGoodsParam.getBeginTime() != null) {
            activityGoods.setBeginTime(DateUtils.parseTime(activityGoodsParam.getBeginTime()));
        }
        if (activityGoodsParam.getEndTime() != null) {
            activityGoods.setEndTime(DateUtils.parseTime(activityGoodsParam.getEndTime()));
        }
        return activityGoods;
    }

    /**
     * 转前台返回值列表
     */
    public static List<ActivityGoodsInstance> toActivityGoodsInstanceList(List<ActivityGoods> actGoodsList) {
        List<ActivityGoodsInstance> actGoodsInsList = new ArrayList<ActivityGoodsInstance>();
        if (actGoodsList == null || actGoodsList.size() < 1) {
            return actGoodsInsList;
        }
        for (ActivityGoods actGoods : actGoodsList) {
            actGoodsInsList.add(toActivityGoodsInstance(actGoods));
        }
        return actGoodsInsList;
    }

    /**
     * 转前台返回值列表
     */
    public static List<ActivityGoodsFTInstance> toActivityGoodsFTInstanceList(List<ActivityGoods> actGoodsList) {
        List<ActivityGoodsFTInstance> actGoodsFTInsList = new ArrayList<ActivityGoodsFTInstance>();
        if (actGoodsList == null || actGoodsList.size() < 1) {
            return actGoodsFTInsList;
        }
        for (ActivityGoods actGoods : actGoodsList) {
            actGoodsFTInsList.add(toActivityGoodsFTInstance(actGoods));
        }
        return actGoodsFTInsList;
    }

}
