package com.mocentre.tehui.goods.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.core.service.support.datasource.annotation.DataSource;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.goods.dao.INavigateDao;
import com.mocentre.tehui.goods.model.Navigate;
import com.mocentre.tehui.goods.service.INavigateService;

/**
 * 导航栏 Created by 王雪莹 on 2016/12/20.
 */
@Component
public class NavigateService implements INavigateService {

    @Autowired
    private INavigateDao navigateDao;

    @Override
    @DataSource(value = "read")
    public ListJsonResult<Navigate> getNavigatePage(Requirement require) {
        return navigateDao.queryDatatablesPage(require);
    }

    @Override
    @DataSource(value = "read")
    public List<Navigate> getShowNavigateSortedList() {
        return navigateDao.getShowBySortingList();
    }

    @Override
    @DataSource(value = "write")
    public void saveNavigate(Navigate navigate) {
        navigateDao.saveEntity(navigate);
    }

    @Override
    @DataSource(value = "write")
    public void updateNavigate(Navigate navigate) {
        if (navigate.getId() != null) {
            navigateDao.updateEntity(navigate);
        }
    }

    @Override
    @DataSource(value = "write")
    public void delByIdList(List<Long> idList) {
        if (idList != null) {
            for (Long id : idList) {
                navigateDao.delById(id);
            }
        }
    }

    @Override
    @DataSource(value = "read")
    public List<Navigate> getAll() {
        return navigateDao.getAll();
    }

    @Override
    @DataSource(value = "read")
    public Navigate getNavigateById(Long id) {
        return navigateDao.get(id);
    }

    @Override
    @DataSource(value = "read")
    public List<Navigate> getByTypeId(Long typeId) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("typeId", typeId);
        return navigateDao.queryList(param);
    }

}
