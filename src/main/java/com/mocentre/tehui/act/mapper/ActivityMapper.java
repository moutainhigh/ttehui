/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.act.mapper;

import java.util.ArrayList;
import java.util.List;

import com.mocentre.tehui.act.enums.ActivityShowLocal;
import com.mocentre.tehui.act.enums.ActivityType;
import com.mocentre.tehui.act.model.Activity;
import com.mocentre.tehui.backend.model.ActivityInstance;
import com.mocentre.tehui.backend.param.ActivityParam;
import com.mocentre.tehui.frontend.model.ActivityFTInstance;
import com.mocentre.tehui.system.mapper.ImageMapper;

/**
 * 活动信息转换
 * 
 * @author Created by yukaiji on 2016年1月16日
 */
public class ActivityMapper {

    /**
     * 转前台返回实体类
     */
    public static ActivityInstance toActivityInstance(Activity activity) {
        ActivityInstance activityIns = new ActivityInstance();
        activityIns.setId(activity.getId());
        activityIns.setShopId(activity.getShopId());
        activityIns.setIntro(activity.getIntro());
        activityIns.setIsShow(activity.getIsShow());
        activityIns.setSorting(activity.getSorting());
        activityIns.setTitle(activity.getTitle());
        activityIns.setType(activity.getType());
        activityIns.setShowLocal(activity.getShowLocal());
        activityIns.setGmtCreated(activity.getGmtCreated());
        activityIns.setGmtModified(activity.getGmtModified());
        activityIns.setImageList(ImageMapper.toImageInstanceList(activity.getImageList()));
        return activityIns;
    }

    /**
     * 转前台返回实体类
     */
    public static ActivityFTInstance toActivityFTInstance(Activity activity) {
        ActivityFTInstance activityIns = new ActivityFTInstance();
        activityIns.setId(activity.getId());
        activityIns.setShopId(activity.getShopId());
        activityIns.setIntro(activity.getIntro());
        activityIns.setIsShow(activity.getIsShow());
        activityIns.setSorting(activity.getSorting());
        activityIns.setTitle(activity.getTitle());
        activityIns.setType(activity.getType());
        activityIns.setGmtCreated(activity.getGmtCreated());
        activityIns.setGmtModified(activity.getGmtModified());
        activityIns.setImageFTList(ImageMapper.toImageFTInstanceList(activity.getImageList()));
        return activityIns;
    }

    /**
     * 参数转活动信息详情
     */
    public static Activity toActivity(ActivityParam activityParam) {
        Activity activity = new Activity();
        Long id = activityParam.getId();
        activity.setId(id);
        activity.setShopId(activityParam.getShopId());
        activity.setIntro(activityParam.getIntro());
        Integer isShow = activityParam.getIsShow();
        if (isShow == 0 || isShow == 1) {
            activity.setIsShow(isShow);
        }
        activity.setSorting(activityParam.getSorting());
        activity.setTitle(activityParam.getTitle());
        String type = activityParam.getType();
        if (id == null) {
            if (ActivityType.GROUPON.getCodeValue().equals(type) || ActivityType.SECKILL.getCodeValue().equals(type)) {
                activity.setType(type);
            }
        }
        String showLocal = activityParam.getShowLocal();
        if (ActivityShowLocal.MALL.getCodeValue().equals(showLocal)
                || ActivityShowLocal.ABC.getCodeValue().equals(showLocal)) {
            activity.setShowLocal(showLocal);
        }
        return activity;
    }

    /**
     * 转前台返回值列表
     */
    public static List<ActivityInstance> toActivityInstanceList(List<Activity> actList) {
        List<ActivityInstance> actInsList = new ArrayList<ActivityInstance>();
        if (actList == null || actList.size() < 1) {
            return actInsList;
        }
        for (Activity activity : actList) {
            actInsList.add(toActivityInstance(activity));
        }
        return actInsList;
    }

    /**
     * 转小程序前台返回值列表
     */
    public static List<ActivityFTInstance> toActivityFTInstanceList(List<Activity> actList) {
        List<ActivityFTInstance> actFTInsList = new ArrayList<ActivityFTInstance>();
        if (actList == null || actList.size() < 1) {
            return actFTInsList;
        }

        for (Activity activity : actList) {
            actFTInsList.add(toActivityFTInstance(activity));
        }
        return actFTInsList;
    }
}
