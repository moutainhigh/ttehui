package com.mocentre.tehui.goods.service;

import com.mocentre.tehui.goods.model.Channel;

import java.util.List;
import java.util.Map;

/**
 * 频道service接口
 * Created by 王雪莹 on 2016/11/5.
 */
public interface IChannelService {
    /**
     * 添加一个新频道（店铺id不能为空）
     */
    Long addChannel(Channel channel);
    /**
     * 删除一个频道
     */
    Long delChannel(Long id, Long shopId);

    /**
     * 批量删除多个频道
     */
    void delChannel(List<Long> ids);
    /**
     * 根据 id 修改频道属性
     */
    Long updateChannel(Channel channel);
    /**
     * 根据 id 修改频道属性
     */
    Long updateChannel(Map<String,Object> paramMap);
    /**
     * 修改频道的排序（List）
     */
    void updateSorted(Map<String,Object> paramMap);
    /**
     *查询某一店铺所有的频道（店铺id）
     */
    List<Channel> getAllChannel(Long shopId);
    /**
     * 查询某一店铺展示的店铺（店铺id）
     */
    List<Channel> getShowChannel(Long shopId);

    /**
     * 根据id查询
     */
    Channel getById(Long id);
}
