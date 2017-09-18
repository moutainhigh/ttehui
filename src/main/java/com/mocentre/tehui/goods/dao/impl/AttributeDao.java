package com.mocentre.tehui.goods.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mocentre.tehui.core.dao.BaseDao;
import com.mocentre.tehui.goods.dao.IAttributeDao;
import com.mocentre.tehui.goods.model.Attribute;

/**
 * 商品属性Dao Created by 王雪莹 on 2016/11/4.
 */
@Repository
public class AttributeDao extends BaseDao<Attribute> implements IAttributeDao {

    /**
     * 根据id进行物理删除
     * 
     * @param id
     * @return
     */
    @Override
    public int delById(Long id) {
        return super.delete("GoodsStorage_del_id", id);
    }

    /**
     * 根据id列表进行物理删除(批量)
     * 
     * @param ids
     * @return
     */
    @Override
    public int delById(List<Long> ids) {
        return super.delete("GoodsStorage_del_ids", ids);
    }

}
