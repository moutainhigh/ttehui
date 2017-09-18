package com.mocentre.gift.dma.service;

import com.mocentre.common.BaseResult;
import com.mocentre.common.ListJsonResult;
import com.mocentre.gift.dma.model.GiftDemand;
import com.mocentre.tehui.core.service.support.query.Requirement;

import java.util.List;
import java.util.Map;

public interface IGiftDemandService {

	ListJsonResult<GiftDemand> queryGiftDemandPage(Requirement require);

	List<GiftDemand> queryAll(Map<String, Object> paramMap);

	void changeGiftDemandStatus(Map<String, Object> paramMap);

	BaseResult delete(List<Long> idList, String requestId);

	Long addGiftDemand(GiftDemand giftDemand);

	Long updateGiftDemand(GiftDemand giftDemand);
}
