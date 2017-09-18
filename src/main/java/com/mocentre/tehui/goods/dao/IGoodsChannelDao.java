package com.mocentre.tehui.goods.dao;

import com.mocentre.tehui.goods.model.GoodsChannel;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 商品频道关联表
 * Created by 王雪莹 on 2016/11/16.
 */
public interface IGoodsChannelDao {
    int saveEntity(List<GoodsChannel> goodsChannels);

    Long saveEntity(GoodsChannel goodsChannel);

    int delByGoodsId(Serializable goodsId);

    int delByGoodsId(List<Long> goodsId);

    int delByChannelId(Serializable channelId);

    int delByChannelId(List<Long> channelIds);

    Long updateEntity(GoodsChannel goodsChannel);

    Long updateEntity(List<GoodsChannel> goodsChannel);

    GoodsChannel get(Serializable id);

    List<GoodsChannel> getAll();

    List<GoodsChannel> queryList(Map<String, Object> paramMap);

    List<Long> getChannelIdListByGoodsId(Serializable goodsId);

    List<Long> getGoodsIdListByChannelId(Serializable channelId);
}
