package com.mocentre.tehui.goods.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mocentre.tehui.core.dao.BaseDao;
import com.mocentre.tehui.goods.dao.INavigateDao;
import com.mocentre.tehui.goods.model.Navigate;

/**
 * 导航栏管理 Created by 王雪莹 on 2016/12/20.
 */
@Repository
public class NavigateDao extends BaseDao<Navigate> implements INavigateDao {

    @Override
    public List<Navigate> getShowBySortingList() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("orderColumn", "sorting");
        paramMap.put("isShow", 1);
        paramMap.put("orderBy", "asc");
        return this.queryList(paramMap);
    }

    @Override
    public void saveEntity(List<Navigate> navigateList) {
        for (Navigate navigate : navigateList) {
            super.saveEntity(navigate);
        }
    }

    /**
     * 删除
     * 
     * @param id
     * @return
     */
    @Override
    public int delById(Long id) {
        return super.logicRemoveById(id);
    }

    /**
     * 根据店铺id删除
     * 
     * @param id
     * @return
     */
    @Override
    public int delByShopId(Long id) {
        return super.delete("Navigate_del_ShopId", id);
    }

    /**
     * 根据店铺id批量删除
     * 
     * @param ids
     * @return
     */
    @Override
    public int delByShopId(List<Long> ids) {
        return super.delete("Navigate_del_ShopIds", ids);
    }
}
