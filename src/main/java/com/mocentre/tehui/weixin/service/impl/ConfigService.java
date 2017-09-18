package com.mocentre.tehui.weixin.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mocentre.tehui.core.service.support.datasource.annotation.DataSource;
import com.mocentre.tehui.weixin.dao.IConfigDao;
import com.mocentre.tehui.weixin.model.Config;
import com.mocentre.tehui.weixin.service.IConfigService;

/**
 * 微信管理 Created by 王雪莹 on 2017/1/12.
 */
@Component
public class ConfigService implements IConfigService {

    @Autowired
    private IConfigDao configDao;

    /**
     * 根据项目code获取微信配置
     * 
     * @param code
     * @return
     */
    @Override
    @DataSource(value = "read")
    public Config getByProjectCode(String code) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("code", code);
        List<Config> configList = configDao.queryList(paramMap);
        if (configList != null && configList.size() > 0) {
            return configList.get(0);
        }
        return null;
    }

}
