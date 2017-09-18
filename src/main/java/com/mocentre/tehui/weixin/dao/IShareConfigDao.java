package com.mocentre.tehui.weixin.dao;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.weixin.model.ShareConfig;

import java.io.Serializable;

/**
 * 微信分享配置信息 Created by yukaiji on 2017/5/4.
 */
public interface IShareConfigDao {
    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    ShareConfig get(Serializable id);

    /**
     * 获取所有的分享配置(分页查询)
     *
     * @return 所有的分享配置
     */
    ListJsonResult<ShareConfig> queryDatatablesPage(Requirement require);

    /**
     * 插入分享配置
     *
     * @param shareConfig
     */
    Long saveEntity(ShareConfig shareConfig);

    /**
     * 修改分享配置
     *
     * @param shareConfig
     */
    Long updateEntity(ShareConfig shareConfig);

    /**
     * 根据id删除
     *
     * @param id
     */
    int logicRemoveById(Serializable id);

    /**
     * 改变默认
     *
     * @param id
     * @return
     */
    int editDefault(Long id);

    /**
     * 查询默认配置
     * 
     * @return
     */
    ShareConfig getDefault();

    /**
     * 保存到缓存
     * 
     * @param shareConfig
     * @return
     */
    boolean updateToCache(ShareConfig shareConfig);

    /**
     * 缓存中获取
     * 
     * @return
     */
    ShareConfig getFromCache();

    /**
     * 缓存中删除
     *
     * @return
     */
    boolean deleteFromCache();

}
