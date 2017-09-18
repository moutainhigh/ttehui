package com.mocentre.gift.ps.service;

import com.mocentre.common.BaseResult;
import com.mocentre.common.ListJsonResult;
import com.mocentre.gift.ps.model.GiftCustomer;
import com.mocentre.tehui.core.service.support.query.Requirement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 礼品平台 客户Service接口
 * @author liqifan
 * @date 创建时间：2017年4月6日 上午11:24:23
 */
public interface IGiftCustomerService {

	List<GiftCustomer> queryAll(Map<String, Object> paramMap);

	void updateGiftCustomer(GiftCustomer giftCustomer);

	void saveGiftCustomer(GiftCustomer giftCustomer);

	boolean queryExistGiftCustomer(Map<String, Object> paramMap);

	BaseResult delete(List<Long> idList, String requestId);

	GiftCustomer getGiftCustomer(Long id);

	GiftCustomer getGiftCustomerByUserName(String userName);

	void updateGiftCustomerPassWord(Map<String, Object> paramMap);

    ListJsonResult<GiftCustomer> queryGiftCustomerPage(Requirement require);

	List<GiftCustomer> selectCustomer(HashMap<String, Object> map);
}
