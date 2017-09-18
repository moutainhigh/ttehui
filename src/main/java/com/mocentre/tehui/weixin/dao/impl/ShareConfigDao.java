package com.mocentre.tehui.weixin.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mocentre.tehui.common.constant.RedisKeyConstant;
import com.mocentre.tehui.core.dao.BaseRedisDao;
import com.mocentre.tehui.weixin.dao.IShareConfigDao;
import com.mocentre.tehui.weixin.model.ShareConfig;

/**
 * 微信分享配置信息 Created by yukaiji on 2017/5/4.
 */
@Repository
public class ShareConfigDao extends BaseRedisDao<ShareConfig, String, ShareConfig> implements IShareConfigDao {

    @Override
    public int editDefault(Long id) {
        return super.update("ShareConfig_updateDefault", id);
    }

    @Override
    public boolean updateToCache(ShareConfig shareConfig) {
        String key = RedisKeyConstant.SHARE_CONFIG;
        return cacheValue(key, shareConfig);
    }

    @Override
    public ShareConfig getFromCache() {
        String key = RedisKeyConstant.SHARE_CONFIG;
        return getCacheValue(key);
    }

    @Override
    public ShareConfig getDefault() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("isDefault", 1);
        return super.queryUniquely(paramMap);
    }

    @Override
    public boolean deleteFromCache() {
        String key = RedisKeyConstant.SHARE_CONFIG;
        return super.removeCache(key);
    }

}
