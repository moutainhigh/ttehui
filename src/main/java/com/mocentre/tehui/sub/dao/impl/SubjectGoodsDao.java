package com.mocentre.tehui.sub.dao.impl;

import org.springframework.stereotype.Repository;

import com.mocentre.tehui.core.dao.BaseDao;
import com.mocentre.tehui.sub.dao.ISubjectGoodsDao;
import com.mocentre.tehui.sub.model.SubjectGoods;

/**
 * 专题商品关联数据库操作接口实现. Created by yukaiji on 2016/12/2.
 */
@Repository
public class SubjectGoodsDao extends BaseDao<SubjectGoods> implements ISubjectGoodsDao {

    @Override
    public int logicRemoveBySubject(Long subjectId) {
        return super.update("SubjectGoods_logicRemoveBySubject", subjectId);
    }

    @Override
    public int updateByGoods(Long goodsId, String goodsName, String sellPrice, String oldPrice, Long sellLowPrice,
                             Long oldLowPrice) {
        SubjectGoods subGoods = new SubjectGoods();
        subGoods.setGoodsId(goodsId);
        subGoods.setGoodsName(goodsName);
        subGoods.setSellPrice(sellPrice);
        subGoods.setOldPrice(oldPrice);
        subGoods.setSellLowPrice(sellLowPrice);
        subGoods.setOldLowPrice(oldLowPrice);
        int count = super.update("SubjectGoods_update_goods", subGoods);
        return count;
    }

}
