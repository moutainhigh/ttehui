package com.mocentre.tehui.weixin.service;

import com.mocentre.tehui.weixin.model.Config;

/**
 * 微信配置信息
 * Created by 王雪莹 on 2017/1/12.
 */
public interface IConfigService {
    Config getByProjectCode(String code);
}
