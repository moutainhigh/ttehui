package com.mocentre.tehui.goods.provider;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.mocentre.tehui.ChannelManageService;
import com.mocentre.tehui.backend.param.ChannelParam;
import com.mocentre.tehui.goods.mapper.ChannelMapper;
import com.mocentre.tehui.goods.model.Channel;
import com.mocentre.tehui.goods.service.impl.ChannelService;

/**
 * 商品频道provider Created by 王雪莹 on 2016/11/21.
 */
public class ChannelManageServiceImpl implements ChannelManageService {

    @Autowired
    private ChannelService channelService;

    /**
     * 根据店铺id获取所有channel
     * 
     * @param shopId
     * @return
     */
    @Override
    public String getChannelByShopId(Long shopId) {
        List<Channel> allChannel = channelService.getAllChannel(shopId);
        return JSON.toJSONString(allChannel);
    }

    /**
     * 根据id查询频道信息
     * 
     * @param id
     * @return
     */
    @Override
    public String getChannelById(Long id) {
        Channel channel = channelService.getById(id);
        return JSON.toJSONString(channel);
    }

    @Override
    public Long addChannel(ChannelParam channelParm) {
        Channel channel = ChannelMapper.toChannel(channelParm);
        Long i = channelService.addChannel(channel);
        return i;
    }

    /**
     * 根据id删除
     * 
     * @param id
     * @param shopId
     * @return
     */
    @Override
    public Long delChannel(Long id, Long shopId) {
        Long i = channelService.delChannel(id, shopId);
        return i;
    }

    /**
     * 根据idList删除
     * 
     * @param ids
     * @param shopId
     * @return
     */
    @Override
    public void delChannnelList(List<Long> ids, Long shopId) {
        channelService.delChannel(ids);
    }
}
