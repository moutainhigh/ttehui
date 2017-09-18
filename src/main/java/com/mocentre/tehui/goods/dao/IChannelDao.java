package com.mocentre.tehui.goods.dao;

import com.mocentre.tehui.goods.model.Channel;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 频道Dao接口
 * Created by 王雪莹 on 2016/11/4.
 */
public interface IChannelDao {
    List<Channel> getAll();

    Channel get(Serializable id);

    List<Channel> queryList(Map<String, Object> paramMap);

    List<Channel> queryListASC(Map<String, Object> paramMap);

    Long updateEntity(Channel channel);

    Long updateEntity(List<Channel> channel);

    Long saveEntity(Channel channel);

    int saveEntity(List<Channel> channels);
}
