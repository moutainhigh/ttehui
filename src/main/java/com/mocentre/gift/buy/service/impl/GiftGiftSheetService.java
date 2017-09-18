/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.gift.buy.service.impl;


import com.mocentre.gift.buy.dao.IGiftGiftSheetDao;
import com.mocentre.gift.buy.model.GiftGiftSheet;
import com.mocentre.gift.buy.service.IGiftGiftSheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 礼品单service
 * Created by 王雪莹 on 2017/4/11.
 */
@Component
public class GiftGiftSheetService implements IGiftGiftSheetService {
@Autowired
private IGiftGiftSheetDao dao;

    @Override
    public void saveGiftSheet(GiftGiftSheet giftSheet) {
        if(giftSheet.getCustomerId() != null){
            dao.save(giftSheet);
        }
    }

    @Override
    public List<GiftGiftSheet> queryGiftListByCustomer(Long customerId) {
        return dao.getByCustomerid(customerId);
    }

    @Override
    public void updateGiftSheetNum(Long customerId, Long id, Integer num) {
        dao.updateNum(id,num);
    }

    @Override
    public void deleteGiftList(Long customerId, Long id) {
        ArrayList<Long> idList = new ArrayList<>();
        idList.add(id);
        dao.delById(customerId,idList);
    }

    @Override
    public void deleteGiftList(Long customerId, List<Long> idList) {
        dao.delById(customerId,idList);
    }

    @Override
    public List<GiftGiftSheet> getByIdList(List<Long> idList,Long customerId) {
        return dao.getByIdList(idList,customerId);
    }

    @Override
    public List<GiftGiftSheet> queryGiftSheetByParam(Map<String, Object> param) {
        return dao.queryList(param);
    }
}
