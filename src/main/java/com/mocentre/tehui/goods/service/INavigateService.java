package com.mocentre.tehui.goods.service;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.goods.model.Navigate;

import java.util.List;

/**
 * 导航栏service Created by 王雪莹 on 2016/12/20.
 */
public interface INavigateService {

    ListJsonResult<Navigate> getNavigatePage(Requirement require);

    /**
     * 查询显示的所有导航栏，按照排序字段顺序
     * 
     * @return
     */
    List<Navigate> getShowNavigateSortedList();

    void saveNavigate(Navigate navigate);

    void updateNavigate(Navigate navigate);

    void delByIdList(List<Long> idList);

    /**
     * 查询所有
     * 
     * @return
     */
    List<Navigate> getAll();

    Navigate getNavigateById(Long id);

    List<Navigate> getByTypeId(Long typeId);

}
