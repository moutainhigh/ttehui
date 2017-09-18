package com.mocentre.tehui.goods.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mocentre.tehui.core.dao.BaseDao;
import com.mocentre.tehui.goods.dao.IChannelDao;
import com.mocentre.tehui.goods.model.Channel;

/**
 * 频道Dao实现 Created by 王雪莹 on 2016/11/4.
 */
@Repository
public class ChannelDao extends BaseDao<Channel> implements IChannelDao {

    /**
     * 根据条件查询并按sorted字段排序
     * 
     * @param paramMap
     * @return
     */
    @Override
    public List<Channel> queryListASC(Map<String, Object> paramMap) {
        return super.getList("Channel_list_asc", paramMap);
    }

    /**
     * 批量更新
     * 
     * @param channel
     * @return
     */
    @Override
    public Long updateEntity(List<Channel> channel) {
        int i = super.update("Channel_update_List", channel);
        return new Long(i);
    }

    /**
     * 批量添加
     * 
     * @param channels
     * @return
     */
    @Override
    public int saveEntity(List<Channel> channels) {
        return super.insert("Channel_insert_List", channels);
    }
}
