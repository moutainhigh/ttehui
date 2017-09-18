package com.mocentre.tehui.goods.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mocentre.tehui.core.service.support.datasource.annotation.DataSource;
import com.mocentre.tehui.goods.dao.IChannelDao;
import com.mocentre.tehui.goods.model.Channel;
import com.mocentre.tehui.goods.service.IChannelService;

/**
 * 商品库存serivice实现 Created by 王雪莹 on 2016/11/5.
 */
@Component
public class ChannelService implements IChannelService {
    @Autowired
    private IChannelDao channelDao;

    /**
     * 添加一个新频道（店铺id不能为空）
     * 
     * @param channel
     */
    @Override
    @DataSource(value = "write")
    public Long addChannel(Channel channel) {
        if (channel.getShopId() == null) {
            // TODO: 2016/11/8
            System.out.println("必需有店铺id");
        } else {
            return channelDao.saveEntity(channel);
        }
        return null;
    }

    /**
     * 根据id和shopId删除
     * 
     * @param id
     */
    @Override
    @DataSource(value = "write")
    public Long delChannel(Long id, Long shopId) {
        if (shopId == null) {
            // TODO: 2016/11/8
            System.out.println("必需有店铺id");
        } else {
            Channel channel = new Channel();
            channel.setIsDeleted(1);
            channel.setId(id);
            channel.setShopId(shopId);
            return channelDao.updateEntity(channel);
        }
        return null;
    }

    /**
     * 根据id列表删除channel
     * 
     * @param ids
     */
    // TODO: 2016/11/8  
    @Override
    @DataSource(value = "write")
    public void delChannel(List<Long> ids) {

    }

    /**
     * 更新频道
     * 
     * @param channel
     */
    @Override
    @DataSource(value = "write")
    public Long updateChannel(Channel channel) {
        if (channel.getShopId() == null) {
            System.out.println("必需有店铺id");
        } else {
            return channelDao.updateEntity(channel);
        }
        return null;
    }

    /**
     * 更新频道
     * 
     * @param paramMap
     */
    @Override
    @DataSource(value = "write")
    public Long updateChannel(Map<String, Object> paramMap) {
        Channel channel = new Channel();
        if (channel.getShopId() == null) {
            // TODO: 2016/11/8
            System.out.println("必需有店铺id");
        } else {
            return channelDao.updateEntity(channel);
        }
        return null;
    }

    /**
     * 更新排序
     * 
     * @param paramMap
     */
    // TODO: 2016/11/8  
    @Override
    public void updateSorted(Map<String, Object> paramMap) {

    }

    /**
     * 获某店铺取所有的channel
     * 
     * @param shopId
     * @return
     */
    @Override
    @DataSource(value = "read")
    public List<Channel> getAllChannel(Long shopId) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("shopId", shopId);
        return channelDao.queryList(paramMap);
    }

    /**
     * 获取莫店铺展示的channel
     * 
     * @param shopId
     * @return
     */
    @Override
    @DataSource(value = "read")
    public List<Channel> getShowChannel(Long shopId) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("shopId", shopId);
        paramMap.put("isShow", "1");
        return channelDao.queryListASC(paramMap);
    }

    /**
     * 获取莫店铺展示的channel
     * 
     * @param id
     * @return
     */
    @Override
    @DataSource(value = "read")
    public Channel getById(Long id) {
        Channel channel = channelDao.get(id);
        return channel;
    }
}
