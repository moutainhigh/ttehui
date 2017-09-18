package com.mocentre.tehui.act.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.act.model.Activity;
import com.mocentre.tehui.core.service.support.query.Requirement;

/**
 * 活动表数据库操作接口. Created by yukaiji on 2016/1/16.
 */
public interface IActivityDao {

    /**
     * 根据id查询
     * 
     * @param id
     * @return
     */
    Activity get(Serializable id);

    /**
     * 获取所有的活动(分页查询)
     * 
     * @return 所有的活动
     */
    ListJsonResult<Activity> queryDatatablesPage(Requirement require);

    /**
     * 根据id和店铺id获取活动
     * 
     * @param paramMap
     */
    Activity getActivity(Map<String, Object> paramMap);

    /**
     * 根据条件获取活动
     * 
     * @param paramMap
     * @return
     */
    List<Activity> getList(Map<String, Object> paramMap);

    /**
     * 插入活动
     * 
     * @param Activity
     */
    Long saveEntity(Activity activity);

    /**
     * 修改活动
     * 
     * @param Activity
     */
    Long updateEntity(Activity activity);

    /**
     * 根据id删除
     * 
     * @param id
     */
    int logicRemoveById(Serializable id);

    /**
     * 改变展示状态
     * 
     * @param paramMap
     * @return
     */
    int show(Map<String, Object> paramMap);
}
