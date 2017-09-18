package com.mocentre.tehui.goods.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.core.service.support.datasource.annotation.DataSource;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.goods.dao.IDiscoverDao;
import com.mocentre.tehui.goods.model.Discover;
import com.mocentre.tehui.goods.service.IDiscoverService;

/**
 * 商城发现页接口实现 Created by yukaiji on 2016/11/17.
 */

@Component
public class DiscoverService implements IDiscoverService {

    @Autowired
    private IDiscoverDao discoverDao;

    @Override
    @DataSource(value = "read")
    public ListJsonResult<Discover> queryDiscoverPage(Requirement require) {
        return discoverDao.queryDatatablesPage(require);
    }

    @Override
    @DataSource(value = "read")
    public List<Discover> getShowDiscoverList() {
        List<Discover> discoverList = new ArrayList<>();
        discoverList = discoverDao.getFromCacheList();
        if (discoverList == null || discoverList.size() == 0) {
            discoverList = discoverDao.getShowList();
        }
        //排序
        Collections.sort(discoverList, new Comparator<Discover>() {
            public int compare(Discover arg0, Discover arg1) {
                return arg0.getSorting().compareTo(arg1.getSorting());
            }
        });
        return discoverList;
    }

    @Override
    @DataSource(value = "read")
    public Discover getDiscoverById(Long id) {
        Discover discover = discoverDao.queryById(id);
        return discover;
    }

    @Override
    @DataSource(value = "read")
    public List<Discover> queryDiscover(Map<String, Object> paramMap) {
        List<Discover> discoverList = discoverDao.queryList(paramMap);
        return discoverList;
    }

    @Override
    @DataSource(value = "write")
    public Long addDiscover(Discover discover) {
        return discoverDao.saveEntity(discover);
    }

    @Override
    @DataSource(value = "write")
    public Long updateDiscover(Discover discover) {
        return discoverDao.updateEntity(discover);
    }

    @Override
    @DataSource(value = "write")
    public int deleteDiscover(List<Long> idList) {
        int result = discoverDao.delByIdList(idList);
        for (int i = 0; i < idList.size(); i++) {
            Long id = idList.get(i);
            discoverDao.deleteFromCache(id);
        }
        return result;
    }

    @Override
    @DataSource(value = "write")
    public Long show(Long id, Integer show) {
        Discover discover = this.getDiscoverById(id);
        if (discover == null) {
            return 0l;
        }
        Discover dis = new Discover();
        dis.setId(id);
        if (show == 1) {
            dis.setIsShow(1);
            discover.setIsShow(1);
            discoverDao.updateToCache(discover);
        } else {
            dis.setIsShow(0);
            discoverDao.deleteFromCache(id);
        }
        return discoverDao.updateEntity(dis);
    }

    @Override
    @DataSource(value = "write")
    public Boolean updateDiscoverToCache(Discover discover) {
        return discoverDao.updateToCache(discover);
    }

}
