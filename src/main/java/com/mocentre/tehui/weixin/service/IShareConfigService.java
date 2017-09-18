package com.mocentre.tehui.weixin.service;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.backend.param.ShareConfigParam;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.weixin.model.ShareConfig;

/**
 * 微信分享配置信息 Created by 王雪莹 on 2017/5/4.
 */
public interface IShareConfigService {

    /**
     * 获取所有的活动(分页查询)
     *
     * @return 所有的活动
     */
    ListJsonResult<ShareConfig> queryShareConfigPage(Requirement require);

    /**
     * 根据id
     *
     * @param id
     * @return
     */
    ShareConfig getShareConfigById(Long id);

    /**
     * 添加一个分享配置
     *
     * @param shareConfigParam
     * @return id
     */
    ShareConfig addShareConfig(ShareConfigParam shareConfigParam);

    /**
     * 修改一个分享配置
     *
     * @param shareConfigParam
     * @return id
     */
    Long updateShareConfig(ShareConfigParam shareConfigParam);

    /**
     * 修改默认分享配置
     *
     * @param id
     * @return id
     */
    int updateShareConfigDefault(Long id);

    /**
     * 根据id逻辑删除
     *
     * @param id
     * @return
     */
    int delShareConfigById(Long id);

    /**
     * 查询默认分享配置
     * 
     * @return
     */
    ShareConfig getDefaultShareConfig();

}
