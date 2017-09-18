package com.mocentre.gift.mall.service;

import com.mocentre.common.BaseResult;
import com.mocentre.common.ListJsonResult;
import com.mocentre.gift.backend.param.GiftBannerParam;
import com.mocentre.gift.mall.model.GiftBanner;
import com.mocentre.tehui.core.service.support.query.Requirement;

import java.util.List;
import java.util.Map;

/**
 * 礼品平台 banner图service
 * @author liqifan
 * @date 创建时间：2017年4月7日 下午4:14:57
 */
public interface IGiftBannerService {

	List<GiftBanner> queryAll(Map<String, Object> paramMap);

	GiftBanner getGiftBanner(Long id);

	void updateGiftBanner(GiftBannerParam giftBanner);

	void saveGiftBanner(GiftBannerParam giftBanner);

	BaseResult delete(List<Long> idList, String requestId);

	ListJsonResult<GiftBanner> queryGiftBannerPage(Requirement require);
}
