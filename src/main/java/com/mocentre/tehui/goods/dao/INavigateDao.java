package com.mocentre.tehui.goods.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.goods.model.Navigate;

/**
 * 导航管理 Created by 王雪莹 on 2016/12/20.
 */
public interface INavigateDao {

    ListJsonResult<Navigate> queryDatatablesPage(Requirement require);

    Navigate get(Serializable id);

    List<Navigate> getAll();

    List<Navigate> queryList(Map<String, Object> paramMap);

    /**
     * 按照排序字段顺序排序
     * 
     * @return
     */
    List<Navigate> getShowBySortingList();

    Long saveEntity(Navigate navigate);

    void saveEntity(List<Navigate> navigateList);

    Long updateEntity(Navigate navigate);

    int delById(Long id);

    int delByShopId(Long id);

    int delByShopId(List<Long> ids);
}
