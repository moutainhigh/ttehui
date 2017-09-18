/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.weixin.mapper;

import com.mocentre.tehui.backend.model.ShareConfigInstance;
import com.mocentre.tehui.backend.param.ShareConfigParam;
import com.mocentre.tehui.weixin.model.ShareConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * 微信分享配置转换
 * 
 * @author Created by yukaiji on 2017年05月04日
 */
public class ShareConfigMapper {

    public static ShareConfig toShareConfig(ShareConfigParam shareConfigParam) {
        ShareConfig shareConfig = new ShareConfig();
        shareConfig.setId(shareConfigParam.getId());
        shareConfig.setDataUrl(shareConfigParam.getDataUrl());
        shareConfig.setDescription(shareConfigParam.getDescription());
        shareConfig.setImgUrl(shareConfigParam.getImgUrl());
        shareConfig.setIsDefault(shareConfigParam.getIsDefault());
        shareConfig.setLinkUrl(shareConfigParam.getLinkUrl());
        shareConfig.setTitle(shareConfigParam.getTitle());
        shareConfig.setType(shareConfigParam.getType());
        return shareConfig;
    }

    public static ShareConfigInstance toShareConfigIns(ShareConfig shareConfig) {
        ShareConfigInstance shareConfigInstance = new ShareConfigInstance();
        shareConfigInstance.setId(shareConfig.getId());
        shareConfigInstance.setDataUrl(shareConfig.getDataUrl());
        shareConfigInstance.setDescription(shareConfig.getDescription());
        shareConfigInstance.setImgUrl(shareConfig.getImgUrl());
        shareConfigInstance.setIsDefault(shareConfig.getIsDefault());
        shareConfigInstance.setLinkUrl(shareConfig.getLinkUrl());
        shareConfigInstance.setTitle(shareConfig.getTitle());
        shareConfigInstance.setType(shareConfig.getType());
        return shareConfigInstance;
    }

    public static List<ShareConfigInstance> toShareConfigInsList(List<ShareConfig> shareConfigs) {
        List<ShareConfigInstance> shareConfigInsList = new ArrayList<ShareConfigInstance>();
        if (shareConfigs == null || shareConfigs.size() < 1){
            return shareConfigInsList;
        }

        for (ShareConfig shareConfig : shareConfigs) {
            shareConfigInsList.add(toShareConfigIns(shareConfig));
        }
        return shareConfigInsList;
    }
}
