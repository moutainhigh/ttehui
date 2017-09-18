package com.mocentre.tehui.weixin.dao;

import com.mocentre.tehui.weixin.model.Config;

import java.util.List;
import java.util.Map;

/**
 * 微信配置信息
 * Created by 王雪莹 on 2017/1/12.
 */
public interface IConfigDao {
    List<Config> queryList(Map<String, Object> paramMap);
}
