package com.mocentre.gift.buy.dao.impl;

import com.mocentre.gift.buy.dao.IGiftGiftSheetDao;
import com.mocentre.gift.buy.model.GiftGiftSheet;
import com.mocentre.tehui.core.dao.BaseDao;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *礼品单dao
 * Created by 王雪莹 on 2017/4/10.
 */
@Repository
public class GiftGiftSheetDao extends BaseDao<GiftGiftSheet> implements IGiftGiftSheetDao {

    @Override
    public void save(GiftGiftSheet gift) {
        super.save(gift);
    }

    @Override
    public void delById(Long customerId, List<Long> idList) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("customerId",customerId);
        map.put("idList",idList);
        super.getSqlSession().update("GiftGiftSheet_logic_delete", map);
    }

    @Override
    public void updateNum(Long id, Integer num) {
        Assert.notNull(num);
        GiftGiftSheet giftSheet = new GiftGiftSheet();
        giftSheet.setId(id);
        giftSheet.setNum(num);
        super.updateEntity(giftSheet);
    }

    @Override
    public List<GiftGiftSheet> getByCustomerid(Long customerId) {
        Assert.notNull(customerId);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("customerId", customerId);
        return this.queryList(paramMap);
    }

    @Override
    public List<GiftGiftSheet> getByIdList(List<Long> idList,Long customerId) {
        Assert.notNull(customerId);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("customerId", customerId);
        paramMap.put("idList", idList);
        return super.getSqlSession().selectList("GiftGiftSheet_getList", paramMap);
    }
}
