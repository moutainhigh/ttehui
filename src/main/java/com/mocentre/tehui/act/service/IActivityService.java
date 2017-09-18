package com.mocentre.tehui.act.service;

import java.util.List;
import java.util.Map;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.act.model.Activity;
import com.mocentre.tehui.backend.param.ActivityParam;
import com.mocentre.tehui.core.service.support.query.Requirement;

/**
 * 活动详细信息接口. Created by yukaiji on 2017/1/16.
 */
public interface IActivityService {

    /**
     * 获取所有的活动(分页查询)
     * 
     * @return 所有的活动
     */
    ListJsonResult<Activity> queryActivityPage(Requirement require);

    /**
     * 根据id
     * 
     * @param id
     * @return
     */
    Activity getActivityById(Long id);

    /**
     * 查询展示的活动
     * 
     * @return
     */
    List<Activity> getShowActivityList();

    /**
     * 根据id和店铺id获取活动
     * 
     * @param id, shopId
     */
    Activity getActivityById(Long id, Long shopId);

    /**
     * 根据店铺id获取所有需要展示的活动及详细信息
     * 
     * @param id, shopId
     */
    List<Activity> getActivityByParam(Map<String, Object> paramMap);

    /**
     * 添加一个活动
     * 
     * @param Activity
     * @return id
     */
    Activity addActivity(ActivityParam activityParam);

    /**
     * 修改一个活动
     * 
     * @param Activity
     * @return id
     */
    Long updateActivity(ActivityParam activityParam);

    /**
     * 根据id删除
     * 
     * @param id
     * @return
     */
    int delActivityById(Long id);

    /**
     * 改变展示状态
     * 
     * @param paramMap
     * @return
     */
    int show(Map<String, Object> paramMap);
}
