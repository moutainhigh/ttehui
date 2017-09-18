package com.mocentre.tehui.goods.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mocentre.tehui.core.dao.BaseDao;
import com.mocentre.tehui.core.service.support.paging.PageInfo;
import com.mocentre.tehui.goods.dao.ICategoryDao;
import com.mocentre.tehui.goods.model.Category;

/**
 * 分类Dao实现 Created by 王雪莹 on 2016/11/4.
 */
@Repository
public class CategoryDao extends BaseDao<Category> implements ICategoryDao {

    /**
     * 根据条件查询并按sorted字段排序
     * 
     * @param paramMap
     * @return
     */
    @Override
    public List<Category> queryListASC(Map<String, Object> paramMap) {
        paramMap.put("orderColumn", "sorted");
        return super.queryList(paramMap);
    }

    /**
     * 批量添加
     * 
     * @param channels
     * @return
     */
    @Override
    public int saveEntity(List<Category> channels) {
        return super.insert("Category_insert_List", channels);
    }

    /**
     * 根据id删除
     * 
     * @param id
     * @return
     */
    @Override
    public int delById(Long id) {
        return super.delete("Category_del_id", id);
    }

    /**
     * 根据id列表删除
     * 
     * @param ids
     * @return
     */
    @Override
    public int delById(List<Long> ids) {
        return super.delete("Category_del_ids", ids);
    }

    @Override
    public PageInfo<Category> getPage(Integer start, Integer length) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("length", length);
        param.put("start", start);
        param.put("orderColumn", "sorted");
        param.put("orderBy", "asc");
        param.put("isShow", "1");
        PageInfo<Category> pageInfo = super.queryPaged(param);
        return pageInfo;
    }

}
