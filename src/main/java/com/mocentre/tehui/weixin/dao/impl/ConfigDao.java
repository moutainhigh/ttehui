package com.mocentre.tehui.weixin.dao.impl;

import com.mocentre.tehui.core.dao.BaseDao;
import com.mocentre.tehui.weixin.dao.IConfigDao;
import com.mocentre.tehui.weixin.model.Config;
import org.springframework.stereotype.Repository;

/**
 * 微信配置信息
 * Created by 王雪莹 on 2017/1/12.
 */
@Repository
public class ConfigDao extends BaseDao<Config> implements IConfigDao {
}
