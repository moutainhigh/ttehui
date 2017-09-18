package com.mocentre.gift.gd.dao.impl;
import com.mocentre.gift.gd.dao.IGiftGoodsDao;
import com.mocentre.gift.gd.model.GiftGoods;
import com.mocentre.tehui.core.dao.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 礼品平台礼品数据库操作接口实现类.
 *
 * Created by yukaiji on 2017/4/6.
 */

@Repository
public class GiftGoodsDao extends BaseDao<GiftGoods>  implements IGiftGoodsDao {

    @Override
    public List<GiftGoods> getGiftGoodsByParam(Map<String, Object> paramMap) {
        return super.getSqlSession().selectList(entityClass.getSimpleName() + "_byParam", paramMap);
    }

    @Override
    public List<GiftGoods> getGiftGoodsListByCategoryId(Map<String, Object> paramMap) {
        return super.getSqlSession().selectList(entityClass.getSimpleName() + "_byCategoryIdList", paramMap);
    }
}
