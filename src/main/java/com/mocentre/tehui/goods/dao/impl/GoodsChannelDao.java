package com.mocentre.tehui.goods.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.mocentre.tehui.core.dao.BaseDao;
import com.mocentre.tehui.goods.dao.IGoodsChannelDao;
import com.mocentre.tehui.goods.model.GoodsChannel;

/**
 * 商品频道关联Dao实现 Created by 王雪莹 on 2016/11/16.
 */
@Repository
public class GoodsChannelDao extends BaseDao<GoodsChannel> implements IGoodsChannelDao {

    /**
     * 批量添加商品频道关系表
     * 
     * @param goodsChannels
     * @return
     */
    @Override
    public int saveEntity(List<GoodsChannel> goodsChannels) {
        return super.insert("GoodsChannel_insert_List", goodsChannels);
    }

    /**
     * 根据商品id删除该商品关联频道的信息
     * 
     * @param goodsId
     * @return
     */
    @Override
    public int delByGoodsId(Serializable goodsId) {
        return super.delete("GoodsChannel_del_goodsId", goodsId);
    }

    /**
     * 根据商品idList删除该商品关联频道的信息
     * 
     * @param goodsIds
     * @return
     */
    @Override
    public int delByGoodsId(List<Long> goodsIds) {
        return super.delete("GoodsChannel_del_goodsIdList", goodsIds);
    }

    /**
     * 根据频道id删除该频道关联频道的信息
     * 
     * @param channelId
     * @return
     */
    @Override
    public int delByChannelId(Serializable channelId) {
        return super.delete("GoodsChannel_del_channelId", channelId);
    }

    /**
     * 根据频道idList删除该频道关联频道的信息
     * 
     * @param channelIds
     * @return
     */
    @Override
    public int delByChannelId(List<Long> channelIds) {
        return super.delete("GoodsChannel_del_channelIdList", channelIds);
    }

    /**
     * 批量更新数据
     * 
     * @param goodsChannel
     * @return
     */
    @Override
    public Long updateEntity(List<GoodsChannel> goodsChannel) {
        int i = super.update("GoodsChannel_update_List", goodsChannel);
        return new Long(i);
    }

    /**
     * 根据商品id获取频道
     * 
     * @param goodsId
     * @return
     */
    @Override
    public List<Long> getChannelIdListByGoodsId(Serializable goodsId) {
        return super.getList("GoodsChannel_getChannelId_GoodsId", goodsId);
    }

    /**
     * 根据频道id获取商品
     * 
     * @param channelId
     * @return
     */
    @Override
    public List<Long> getGoodsIdListByChannelId(Serializable channelId) {
        return super.getList("GoodsChannel_getGoodsId_ChannelId", channelId);
    }

}
