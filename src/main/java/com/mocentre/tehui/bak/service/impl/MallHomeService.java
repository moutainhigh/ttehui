package com.mocentre.tehui.bak.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.backend.param.MallHomeParam;
import com.mocentre.tehui.bak.dao.IMallHomeDao;
import com.mocentre.tehui.bak.enums.MallHomeShowLocal;
import com.mocentre.tehui.bak.mapper.MallHomeMapper;
import com.mocentre.tehui.bak.model.MallHome;
import com.mocentre.tehui.bak.service.IMallHomeService;
import com.mocentre.tehui.core.service.support.datasource.annotation.DataSource;
import com.mocentre.tehui.core.service.support.paging.PageInfo;
import com.mocentre.tehui.core.service.support.query.Requirement;

/**
 * 农行掌上银行首页实现类
 * <p>
 * Created by yukaiji on 2017/5/17.
 */

@Component
public class MallHomeService implements IMallHomeService {

    @Autowired
    private IMallHomeDao mallHomeDao;

    @Override
    @DataSource(value = "read")
    public ListJsonResult<MallHome> queryMallHomePage(Requirement require) {
        return mallHomeDao.queryDatatablesPage(require);
    }

    @Override
    @DataSource(value = "read")
    public MallHome getMallHomeById(Long id) {
        return mallHomeDao.get(id);
    }

    @Override
    @DataSource(value = "read")
    public List<MallHome> getMallHomeByGoods(Long goodsId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("goodsId", goodsId);
        return mallHomeDao.getList(paramMap);
    }

    @Override
    @DataSource(value = "read")
    public List<MallHome> getMallHomeByActivity(Long activityId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("activityId", activityId);
        return mallHomeDao.getList(paramMap);
    }

    @Override
    @DataSource(value = "read")
    public List<MallHome> getMallHomeByActGoods(Long actGoodsId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("actGoodsId", actGoodsId);
        return mallHomeDao.getList(paramMap);
    }

    @Override
    @DataSource(value = "write")
    public MallHome addMallHome(MallHomeParam mallHomeParam) {
        MallHome mallHome = MallHomeMapper.toMallHome(mallHomeParam);
        mallHomeDao.saveEntity(mallHome);
        if (mallHome.getId() != null) {
            mallHomeDao.updateToCache(mallHome);
        }
        return mallHome;
    }

    @Override
    @DataSource(value = "write")
    public Long updateMallHome(MallHomeParam mallHomeParam) {
        MallHome mallHome = MallHomeMapper.toMallHome(mallHomeParam);
        MallHome oldMallHome = mallHomeDao.get(mallHome.getId());
        Long count = mallHomeDao.updateEntity(mallHome);
        if (mallHome.getId() != null) {
            mallHomeDao.deleteFromCache(mallHome.getId(), oldMallHome.getShowLocal());
            mallHomeDao.updateToCache(mallHome);
        }
        return count;
    }

    @Override
    @DataSource(value = "write")
    public int removeById(Long id, String showLocal) {
        int count = mallHomeDao.logicRemoveById(id);
        if (count > 0) {
            mallHomeDao.deleteFromCache(id, showLocal);
        }
        return count;
    }

    @Override
    @DataSource(value = "read")
    public List<MallHome> getMallHomePage(Integer start, Integer length) {
        List<MallHome> mallHomeList = new ArrayList<MallHome>();
        String showLocal = MallHomeShowLocal.SPECIAL.getCodeValue();
        mallHomeList = mallHomeDao.queryFromCache(showLocal, start - 1, start * length);
        if (mallHomeList == null || mallHomeList.size() <= 0) {
            PageInfo<MallHome> pageInfo = mallHomeDao.getPage(showLocal, start, length);
            mallHomeList = pageInfo.getRows();
        } else {
            Collections.sort(mallHomeList, new Comparator<MallHome>() {
                @Override
                public int compare(MallHome o1, MallHome o2) {
                    return o1.getSorting().compareTo(o2.getSorting());
                }
            });
        }
        return mallHomeList;
    }

    @Override
    @DataSource(value = "read")
    public List<MallHome> getMallHomeByAct() {
        List<MallHome> mallHomeList = new ArrayList<MallHome>();
        String showLocal = MallHomeShowLocal.ACT.getCodeValue();
        mallHomeList = mallHomeDao.queryFromCache(showLocal, null, null);
        if (mallHomeList == null || mallHomeList.size() <= 0) {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("orderColumn", "sorting");
            paramMap.put("orderBy", "asc");
            paramMap.put("showLocal", showLocal);
            mallHomeList = mallHomeDao.getList(paramMap);
        } else {
            Collections.sort(mallHomeList, new Comparator<MallHome>() {
                @Override
                public int compare(MallHome o1, MallHome o2) {
                    return o1.getSorting().compareTo(o2.getSorting());
                }
            });
        }
        return mallHomeList;
    }

    @Override
    @DataSource(value = "read")
    public List<MallHome> getMallHomeListByLocal(String showLocal) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("orderColumn", "sorting");
        paramMap.put("orderBy", "asc");
        paramMap.put("showLocal", showLocal);
        List<MallHome> mallHomeList = mallHomeDao.getList(paramMap);
        return mallHomeList;
    }

}
