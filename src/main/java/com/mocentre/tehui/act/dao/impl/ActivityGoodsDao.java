package com.mocentre.tehui.act.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mocentre.tehui.act.dao.IActivityGoodsDao;
import com.mocentre.tehui.act.model.ActivityGoods;
import com.mocentre.tehui.common.constant.RedisKeyConstant;
import com.mocentre.tehui.core.dao.BaseRedisDao;
import com.mocentre.tehui.core.utils.DateUtils;

/**
 * 活动与商品数据库操作接口实现. Created by yukaiji on 2016/1/16.
 */
@Repository
public class ActivityGoodsDao extends BaseRedisDao<ActivityGoods, String, ActivityGoods> implements IActivityGoodsDao {

    @Override
    public int logicRemove(Long id) {
        int count = super.logicRemoveById(id);
        if (count > 0) {
            this.deleteFromCache(id);
        }
        return count;
    }

    @Override
    public Long updateAllTime(Long activityId, String beginTime, String endTime) {
        ActivityGoods activityGoods = new ActivityGoods();
        activityGoods.setActivityId(activityId);
        activityGoods.setBeginTime(DateUtils.parseTime(beginTime));
        activityGoods.setEndTime(DateUtils.parseTime(endTime));
        Long updateNum = (long) super.update(entityClass.getSimpleName() + "_updateTime", activityGoods);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("activityId", activityId);
        List<ActivityGoods> activityGoodsList = this.queryList(paramMap);
        if (activityGoodsList.size() != updateNum) {
            return -1L;
        }
        for (ActivityGoods actGoods : activityGoodsList) {
            this.updateToCache(actGoods);
        }
        return updateNum;
    }

    @Override
    public ActivityGoods getFromCache(Long id) {
        String key = RedisKeyConstant.ACTGOODS_LIST + ":" + id;
        ActivityGoods actGoods = getCacheValue(key);
        if (actGoods == null) {
            actGoods = this.get(id);
            if (actGoods != null) {
                this.updateToCache(actGoods);
            }
        }
        return actGoods;
    }

    @Override
    public boolean updateToCache(ActivityGoods actGoods) {
        String key = RedisKeyConstant.ACTGOODS_LIST + ":" + actGoods.getId();
        boolean suc = super.cacheValue(key, actGoods);
        return suc;
    }

    @Override
    public boolean deleteFromCache(Long id) {
        String key = RedisKeyConstant.ACTGOODS_LIST + ":" + id;
        boolean suc = removeCacheValue(key);
        return suc;
    }

}
