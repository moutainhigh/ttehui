package com.mocentre.tehui.act.dao;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.act.model.ActivityGoods;
import com.mocentre.tehui.core.service.support.query.Requirement;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 活动商品关联表数据库操作接口. Created by yukaiji on 2017/1/16.
 */
public interface IActivityGoodsDao {

    /**
     * 获取所有的活动商品(分页查询)
     * 
     * @return 所有的活动商品
     */
    ListJsonResult<ActivityGoods> queryDatatablesPage(Requirement require);

    /**
     * 根据条件查询
     * 
     * @param paramMap
     * @return
     */
    List<ActivityGoods> queryList(Map<String, Object> paramMap);

    /**
     * 根据id获取
     * 
     * @return
     */
    ActivityGoods get(Serializable id);

    /**
     * 添加关联信息
     * 
     * @param activityGoods
     * @return id
     */
    Long saveEntity(ActivityGoods activityGoods);

    /**
     * 修改关联信息
     * 
     * @param activityGoods
     * @return 修改数量
     */
    Long updateEntity(ActivityGoods activityGoods);

    /**
     * 根据id删除(逻辑删除)
     * 
     * @param id
     */
    int logicRemove(Long id);

    /**
     * 批量修改商品起止时间
     *
     * @param activityId
     * @param beginTime
     * @param endTime
     * @return 修改数量
     */
    Long updateAllTime(Long activityId, String beginTime, String endTime);

    /**
     * 缓存中查询
     * 
     * @param id
     * @return
     */
    ActivityGoods getFromCache(Long id);

    /**
     * 更新到缓存
     * 
     * @param actGoods
     * @return
     */
    boolean updateToCache(ActivityGoods actGoods);

    /**
     * 缓存中移除
     * 
     * @param id
     * @return
     */
    boolean deleteFromCache(Long id);

}
