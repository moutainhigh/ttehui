package com.mocentre.tehui.weixin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.backend.param.ShareConfigParam;
import com.mocentre.tehui.core.service.support.datasource.annotation.DataSource;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.weixin.dao.IShareConfigDao;
import com.mocentre.tehui.weixin.enums.ShareType;
import com.mocentre.tehui.weixin.mapper.ShareConfigMapper;
import com.mocentre.tehui.weixin.model.ShareConfig;
import com.mocentre.tehui.weixin.service.IShareConfigService;

/**
 * 微信分享配置管理 Created by yukaiji on 2017/5/4.
 */
@Component
public class ShareConfigService implements IShareConfigService {

    @Autowired
    private IShareConfigDao shareConfigDao;

    @Override
    @DataSource(value = "read")
    public ListJsonResult<ShareConfig> queryShareConfigPage(Requirement require) {
        return shareConfigDao.queryDatatablesPage(require);
    }

    @Override
    @DataSource(value = "read")
    public ShareConfig getShareConfigById(Long id) {
        return shareConfigDao.get(id);
    }

    @Override
    @DataSource(value = "write")
    public ShareConfig addShareConfig(ShareConfigParam shareConfigParam) {
        ShareConfig shareConfig = ShareConfigMapper.toShareConfig(shareConfigParam);
        if (shareConfigParam.getIsDefault() == 1) {
            shareConfigDao.editDefault(null);
            shareConfigDao.updateToCache(shareConfig);
        }
        shareConfigDao.saveEntity(shareConfig);
        return shareConfig;
    }

    @Override
    @DataSource(value = "write")
    public Long updateShareConfig(ShareConfigParam shareConfigParam) {
        ShareConfig shareConfig = ShareConfigMapper.toShareConfig(shareConfigParam);
        if (shareConfigParam.getIsDefault() == 1) {
            shareConfigDao.editDefault(null);
            shareConfigDao.updateToCache(shareConfig);
        }
        if (ShareType.LINK.getCodeValue().equals(shareConfigParam.getType())) {
            shareConfig.setDataUrl("");
        }
        return shareConfigDao.updateEntity(shareConfig);
    }

    @Override
    @DataSource(value = "write")
    public int updateShareConfigDefault(Long id) {
        return shareConfigDao.editDefault(id);
    }

    @Override
    @DataSource(value = "write")
    public int delShareConfigById(Long id) {
        ShareConfig shareConfig = shareConfigDao.get(id);
        if (shareConfig.getIsDefault() == 1) {
            shareConfigDao.deleteFromCache();
        }
        return shareConfigDao.logicRemoveById(id);
    }

    @Override
    @DataSource(value = "read")
    public ShareConfig getDefaultShareConfig() {
        ShareConfig shareConfig = shareConfigDao.getFromCache();
        if (shareConfig == null) {
            shareConfig = shareConfigDao.getDefault();
        }
        return shareConfig;
    }

}
