package com.mocentre.tehui.act.service;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.act.model.ActivityGoods;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.goods.model.GoodsStorage;

import java.util.List;

/**
 * 活动商品关联接口. Created by yukaiji on 2017/1/16.
 */
public interface IActivityGoodsService {

    /**
     * 获取所有的活动商品(分页查询)
     * 
     * @return 所有的商品
     */
    ListJsonResult<ActivityGoods> queryActivityPage(Requirement require);

    /**
     * 根据id获取
     * 
     * @param id
     */
    ActivityGoods getActivityGoodsById(Long id);

    /**
     * 根据活动Id获取关联信息
     * 
     * @param activityId
     */
    List<ActivityGoods> getActivityGoodsListByActivityId(Long activityId);

    /**
     * 新增活动商品
     * 
     * @param actGoods
     * @param storageList
     * @return
     */
    ActivityGoods insertActivityGoods(ActivityGoods actGoods, List<GoodsStorage> storageList);

    /**
     * 修改活动商品
     * 
     * @param actGoods
     * @param storageList
     * @return
     */
    Long updateActivityGoods(ActivityGoods actGoods, List<GoodsStorage> storageList);

    /**
     * 根据id删除活动商品
     * 
     * @param id
     * @return
     */
    int deleteActGoodsById(Long id);

    /**
     * 根据活动id删除活动商品
     * 
     * @param activityId
     */
    void deleteActGoodsByActId(Long activityId);

    /**
     * 缓存中查询
     * 
     * @param id
     * @return
     */
    ActivityGoods getActivityGoodsFromCache(Long id);

    /**
     * 通过商品id，查询商品
     * 
     * @param goodsId
     * @return
     */
    List<ActivityGoods> queryListByGoods(Long goodsId);

    /**
     * 根据活动id批量更新商品开始结束时间
     *
     * @param activityId
     * @param beginTime
     * @param endTime
     * @return
     */
    Long updateActivityGoodsTime(Long activityId, String beginTime, String endTime);

    /**
     * 是否存在库存令牌
     * 
     * @param goodsId
     * @param goodsSku
     * @param id
     * @return
     */
    Boolean getStorageToken(Long goodsId, String goodsSku, Long id);

    /**
     * 库存中存入一个令牌
     * 
     * @param goodsId
     * @param goodsSku
     * @param id
     * @return
     */
    Boolean putStorageToken(Long goodsId, String goodsSku, Long id);

}
