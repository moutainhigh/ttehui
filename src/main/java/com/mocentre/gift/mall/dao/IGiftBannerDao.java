package com.mocentre.gift.mall.dao;

import com.mocentre.common.ListJsonResult;
import com.mocentre.gift.mall.model.GiftBanner;
import com.mocentre.tehui.core.service.support.query.Requirement;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 礼品平台 banner图dao
 * 
 * @author liqifan
 * @date 创建时间：2017年4月7日 下午4:17:00
 */
public interface IGiftBannerDao {

    /**
     * 查询banner图
     * 
     * @return 所有的活动
     */
    List<GiftBanner> queryList(Map<String, Object> paramMap);

    /**
     * 根据id查询
     * 
     * @param id
     * @return
     */
    GiftBanner get(Serializable id);

    /**
     * 修改banner图
     * 
     * @param giftBanner
     */
    Long updateEntity(GiftBanner giftBanner);

    /**
     * 插入banner图
     * 
     * @param giftBanner
     */
    Long saveEntity(GiftBanner giftBanner);

    /**
     * 根据id删除
     * 
     * @param id
     */
    int logicRemoveById(Serializable id);

    /**
     * 分页查询
     *
     * @param require
     */
    ListJsonResult<GiftBanner> queryDatatablesPage(Requirement require);
}
