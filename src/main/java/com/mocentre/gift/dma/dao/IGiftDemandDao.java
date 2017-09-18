package com.mocentre.gift.dma.dao;

import com.mocentre.common.ListJsonResult;
import com.mocentre.gift.dma.model.GiftDemand;
import com.mocentre.tehui.core.service.support.query.Requirement;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 礼品平台 极速获取方案dao
 * 
 * @author liqifan
 * @date 创建时间：2017年4月7日 下午2:35:44
 */
public interface IGiftDemandDao {

    List<GiftDemand> queryList(Map<String, Object> paramMap);

    int changeGiftDemandStatus(Map<String, Object> paramMap);

    int logicRemoveById(Serializable id);

    Long saveEntity(GiftDemand giftDemand);

    Long updateEntity(GiftDemand giftDemand);

    ListJsonResult<GiftDemand> queryDatatablesPage(Requirement require);
}
