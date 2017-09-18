package com.mocentre.gift.gd.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mocentre.common.ListJsonResult;
import com.mocentre.gift.backend.param.GiftCategoryParam;
import com.mocentre.gift.gd.dao.IGiftCategoryDao;
import com.mocentre.gift.gd.mapper.GiftCategoryMapper;
import com.mocentre.gift.gd.model.GiftCategory;
import com.mocentre.gift.gd.service.IGiftCategoryService;
import com.mocentre.tehui.core.service.support.query.Requirement;

/**
 * 礼品平台分类接口实现类. Created by yukaiji on 2017/4/6.
 */

@Component
public class GiftCategoryService implements IGiftCategoryService {

    @Autowired
    private IGiftCategoryDao giftCategoryDao;

    @Override
    public ListJsonResult<GiftCategory> queryGiftCategoryPage(Requirement require) {
        return giftCategoryDao.queryDatatablesPage(require);
    }

    @Override
    public GiftCategory getGiftCategoryById(Long id) {
        return giftCategoryDao.get(id);
    }

    @Override
    public List<GiftCategory> getGiftCategoryByParam(Map<String, Object> param) {
        return giftCategoryDao.getGiftCategoryByParam(param);
    }

    @Override
    public GiftCategory addGiftCategory(GiftCategoryParam giftCategoryParam) {
        GiftCategory giftCategory = GiftCategoryMapper.toGiftCatgory(giftCategoryParam);
        giftCategoryDao.saveEntity(giftCategory);
        return giftCategory;
    }

    @Override
    public Long updateGiftCategory(GiftCategoryParam giftCategoryParam) {
        GiftCategory giftCategory = GiftCategoryMapper.toGiftCatgory(giftCategoryParam);
        return giftCategoryDao.updateEntity(giftCategory);
    }

    @Override
    public int removeById(Long id) {
        return giftCategoryDao.logicRemoveById(id);
    }
}
