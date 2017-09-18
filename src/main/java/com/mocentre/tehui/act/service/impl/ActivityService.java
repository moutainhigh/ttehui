package com.mocentre.tehui.act.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.act.dao.IActivityDao;
import com.mocentre.tehui.act.mapper.ActivityMapper;
import com.mocentre.tehui.act.model.Activity;
import com.mocentre.tehui.act.service.IActivityGoodsService;
import com.mocentre.tehui.act.service.IActivityService;
import com.mocentre.tehui.backend.param.ActivityParam;
import com.mocentre.tehui.core.service.support.datasource.annotation.DataSource;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.system.dao.IImageDao;
import com.mocentre.tehui.system.enums.ImageSeat;
import com.mocentre.tehui.system.enums.ImageType;
import com.mocentre.tehui.system.model.Image;

/**
 * 活动接口实现 Created by yukaiji on 2017/1/16.
 */

@Component
public class ActivityService implements IActivityService {

    @Autowired
    private IActivityDao          activityDao;
    @Autowired
    private IActivityGoodsService activityGoodsService;
    @Autowired
    private IImageDao             imageDao;

    @Override
    @DataSource(value = "read")
    public ListJsonResult<Activity> queryActivityPage(Requirement require) {
        return activityDao.queryDatatablesPage(require);
    }

    @Override
    @DataSource(value = "read")
    public Activity getActivityById(Long id) {
        return activityDao.get(id);
    }

    @Override
    @DataSource(value = "read")
    public Activity getActivityById(Long id, Long shopId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", id);
        paramMap.put("shopId", shopId);
        // 获取banner图
        List<Image> imageList = imageDao.queryList(id, ImageSeat.ACTIVITY.getCodeValue());
        Activity activity = activityDao.getActivity(paramMap);
        activity.setImageList(imageList);
        return activity;
    }

    @Override
    @DataSource(value = "read")
    public List<Activity> getShowActivityList() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("isShow", 1);
        return activityDao.getList(paramMap);
    }

    @Override
    @DataSource(value = "read")
    public List<Activity> getActivityByParam(Map<String, Object> paramMap) {
        return activityDao.getList(paramMap);
    }

    @Override
    @DataSource(value = "write")
    public Activity addActivity(ActivityParam activityParam) {
        Activity activity = ActivityMapper.toActivity(activityParam);
        activityDao.saveEntity(activity);
        String[] banners = activityParam.getTopBanner().split(",");
        for (String banner : banners) {
            if (StringUtils.isNotBlank(banner)) {
                Image image = new Image();
                image.setSeat(ImageSeat.ACTIVITY.getCodeValue());
                image.setType(ImageType.BANNER.getCodeValue());
                image.setLocation(banner);
                image.setSeatId(activity.getId());
                imageDao.saveEntity(image);
            }
        }
        return activity;
    }

    @Override
    @DataSource(value = "write")
    public Long updateActivity(ActivityParam activityParam) {
        Activity activity = ActivityMapper.toActivity(activityParam);
        String seat = ImageSeat.ACTIVITY.getCodeValue();
        String type = ImageType.BANNER.getCodeValue();
        Long count = activityDao.updateEntity(activity);
        Long id = activityParam.getId();
        imageDao.delete(type, seat, id);
        String[] banners = activityParam.getTopBanner().split(",");
        for (String banner : banners) {
            if (StringUtils.isNotBlank(banner)) {
                Image image = new Image();
                image.setSeat(ImageSeat.ACTIVITY.getCodeValue());
                image.setType(ImageType.BANNER.getCodeValue());
                image.setLocation(banner);
                image.setSeatId(activity.getId());
                imageDao.saveEntity(image);
            }
        }
        return count;
    }

    @Override
    @DataSource(value = "write")
    public int delActivityById(Long id) {
        String seat = ImageSeat.ACTIVITY.getCodeValue();
        String type = ImageType.BANNER.getCodeValue();
        int num = activityDao.logicRemoveById(id);
        if (num > 0) {
            imageDao.delete(type, seat, id);
            activityGoodsService.deleteActGoodsByActId(id);
        }
        return num;
    }

    @Override
    @DataSource(value = "read")
    public int show(Map<String, Object> paramMap) {
        return activityDao.show(paramMap);
    }

}
