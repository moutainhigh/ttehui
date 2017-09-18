package com.mocentre.tehui.td.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.backend.param.ThirdGoodsParam;
import com.mocentre.tehui.core.service.support.datasource.annotation.DataSource;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.system.dao.IAreasDao;
import com.mocentre.tehui.system.model.Areas;
import com.mocentre.tehui.td.dao.IThirdGoodsAreasDao;
import com.mocentre.tehui.td.dao.IThirdGoodsDao;
import com.mocentre.tehui.td.mapper.ThirdGoodsMapper;
import com.mocentre.tehui.td.model.ThirdGoods;
import com.mocentre.tehui.td.model.ThirdGoodsAreas;
import com.mocentre.tehui.td.service.IThirdGoodsService;

/**
 * 三方商品service实现类
 * <p>
 * Created by yukaiji on 2017/5/17.
 */

@Component
public class ThirdGoodsService implements IThirdGoodsService {

    @Autowired
    private IThirdGoodsDao      thirdGoodsDao;
    @Autowired
    private IAreasDao           areasDao;
    @Autowired
    private IThirdGoodsAreasDao thirdGoodsAreasDao;

    @Override
    @DataSource(value = "read")
    public ListJsonResult<ThirdGoods> queryThirdGoodsPage(Requirement require) {
        return thirdGoodsDao.queryDatatablesPage(require);
    }

    @Override
    @DataSource(value = "read")
    public ThirdGoods getThirdGoodsById(Long id) {
        return thirdGoodsDao.get(id);
    }

    @Override
    @DataSource(value = "read")
    public List<ThirdGoods> getThirdGoodsByGoods(Long goodsId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("goodsId", goodsId);
        return thirdGoodsDao.getList(paramMap);
    }

    @Override
    @DataSource(value = "write")
    public ThirdGoods addThirdGoods(ThirdGoodsParam thirdGoodsParam) {
        ThirdGoods thirdGoods = ThirdGoodsMapper.toThirdGoods(thirdGoodsParam);
        thirdGoodsDao.saveEntity(thirdGoods);
        if (thirdGoods.getId() != null) {
            thirdGoodsDao.updateToCache(thirdGoods);
        }
        String areasStr = thirdGoodsParam.getAreaList();
        List<ThirdGoodsAreas> thirdGoodsAreass = new ArrayList<ThirdGoodsAreas>();
        if (!StringUtils.isBlank(areasStr)) {
            String[] areasList = areasStr.split("-");
            for (String areas : areasList) {
                ThirdGoodsAreas thirdGoodsAreas = new ThirdGoodsAreas();
                Areas are = areasDao.getAreas(areas);
                thirdGoodsAreas.setCityCode(are.getCode());
                thirdGoodsAreas.setCityName(are.getName());
                thirdGoodsAreas.setBdCityCode(are.getBdCityCode());
                thirdGoodsAreas.setLatitude(are.getLatitude());
                thirdGoodsAreas.setLongitude(are.getLongitude());
                thirdGoodsAreas.setGoodsId(thirdGoods.getId());
                thirdGoodsAreass.add(thirdGoodsAreas);
            }
            thirdGoodsAreasDao.saveEntity(thirdGoodsAreass);
        }
        return thirdGoods;
    }

    @Override
    @DataSource(value = "write")
    public Long updateThirdGoods(ThirdGoodsParam thirdGoodsParam) {
        ThirdGoods thirdGoods = ThirdGoodsMapper.toThirdGoods(thirdGoodsParam);
        ThirdGoods oldThirdGoods = thirdGoodsDao.get(thirdGoods.getId());
        Long count = thirdGoodsDao.updateEntity(thirdGoods);
        if (thirdGoods.getId() != null) {
            thirdGoodsDao.deleteFromCache(thirdGoods.getId(), oldThirdGoods.getShowLocal());
            thirdGoodsDao.updateToCache(thirdGoods);
        }
        String areasStr = thirdGoodsParam.getAreaList();
        List<ThirdGoodsAreas> thirdGoodsAreasList = new ArrayList<ThirdGoodsAreas>();
        if (!StringUtils.isBlank(areasStr)) {
            String[] areasList = areasStr.split("-");
            for (String areas : areasList) {
                ThirdGoodsAreas thirdGoodsAreas = new ThirdGoodsAreas();
                Areas are = areasDao.getAreas(areas);
                thirdGoodsAreas.setCityCode(are.getCode());
                thirdGoodsAreas.setCityName(are.getName());
                thirdGoodsAreas.setBdCityCode(are.getBdCityCode());
                thirdGoodsAreas.setLatitude(are.getLatitude());
                thirdGoodsAreas.setLongitude(are.getLongitude());
                thirdGoodsAreas.setGoodsId(thirdGoodsParam.getId());
                thirdGoodsAreasList.add(thirdGoodsAreas);
            }
            thirdGoodsAreasDao.removeById(thirdGoodsParam.getId());
            thirdGoodsAreasDao.saveEntity(thirdGoodsAreasList);
        }
        return count;
    }

    @Override
    @DataSource(value = "write")
    public int removeById(Long id, String showLocal) {
        int count = thirdGoodsDao.logicRemoveById(id);
        if (count > 0) {
            thirdGoodsDao.deleteFromCache(id, showLocal);
        }
        thirdGoodsAreasDao.removeById(id);
        return count;
    }

    @Override
    @DataSource(value = "read")
    public List<ThirdGoods> getThirdGoodsList(String showLocal, String bdCityCode) {
        List<ThirdGoods> thirdGoodsList = new ArrayList<ThirdGoods>();
        List<ThirdGoodsAreas> areasList = thirdGoodsAreasDao.queryByBdCityCode(bdCityCode);
        for (ThirdGoodsAreas areas : areasList) {
            ThirdGoods thirdGoods = thirdGoodsDao.queryFromCacheById(showLocal, areas.getGoodsId());
            if (thirdGoods != null) {
                thirdGoodsList.add(thirdGoods);
            }
        }
        if (thirdGoodsList.size() > 1) {
            Collections.sort(thirdGoodsList, new Comparator<ThirdGoods>() {
                @Override
                public int compare(ThirdGoods o1, ThirdGoods o2) {
                    return o1.getSorting().compareTo(o2.getSorting());
                }
            });
        }
        return thirdGoodsList;
    }

}
