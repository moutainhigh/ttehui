package com.mocentre.gift.gd.dao.impl;

import com.mocentre.gift.gd.dao.IGiftCategoryDao;
import com.mocentre.gift.gd.model.GiftCategory;
import com.mocentre.tehui.core.dao.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 礼品平台分类数据库操作接口实现类.
 *
 * Created by yukaiji on 2017/4/6.
 */

@Repository
public class GiftCategoryDao extends BaseDao<GiftCategory>  implements IGiftCategoryDao{

    @Override
    public List<GiftCategory> getGiftCategoryByParam(Map<String, Object> paramMap) {
        return super.getSqlSession().selectList(entityClass.getSimpleName() + "_byParam", paramMap);
    }

}
