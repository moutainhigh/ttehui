/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.gift.buy.service;

import com.mocentre.gift.buy.model.GiftGiftSheet;

import java.util.List;
import java.util.Map;

/**
 * 礼品单service接口
 * Created by 王雪莹 on 2017/4/11.
 */
public interface IGiftGiftSheetService {

    /**
     * 添加到礼品单
     *
     * @param giftSheet
     */
    void saveGiftSheet(GiftGiftSheet giftSheet);

    /**
     * 查询用户礼品单
     * 
     * @param customerId 用户id
     * @return
     */
    List<GiftGiftSheet> queryGiftListByCustomer(Long customerId);

    /**
     * 更新指定礼品单商品数量
     * 
     * @param customerId 用户id
     * @param id 购物车id
     * @param num 更新数量
     * @return
     */
    void updateGiftSheetNum(Long customerId, Long id, Integer num);

    /**
     * 删除礼品单商品
     * 
     * @param customerId 用户id
     * @param id 购物车id
     * @return
     */
    void deleteGiftList(Long customerId, Long id);

    /**
     * 根据idList删除
     * @param customerId
     * @param idList
     */
    void deleteGiftList(Long customerId, List<Long> idList);

    /**
     * 根据id列表查询
     * @param idList
     * @return
     */
    List<GiftGiftSheet> getByIdList(List<Long> idList,Long customerId);


    /**
     * 根据条件查询
     * @param param
     * @return
     */
    List<GiftGiftSheet> queryGiftSheetByParam(Map<String, Object> param);
}
