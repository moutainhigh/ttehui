package com.mocentre.tehui.td.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mocentre.tehui.core.service.support.datasource.annotation.DataSource;
import com.mocentre.tehui.td.dao.IThirdGoodsAreasDao;
import com.mocentre.tehui.td.model.ThirdGoodsAreas;
import com.mocentre.tehui.td.service.IThirdGoodsAreasService;

/**
 * 三方商品区域service实现类
 * <p>
 * Created by yukaiji on 2017/5/17.
 */

@Component
public class ThirdGoodsAreasService implements IThirdGoodsAreasService {

    @Autowired
    private IThirdGoodsAreasDao thirdGoodsAreasDao;

    @Override
    @DataSource(value = "read")
    public List<ThirdGoodsAreas> getThirdGoodsAreasByGoodsId(Long goodsId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("goodsId", goodsId);
        return thirdGoodsAreasDao.queryList(map);

    }

    @Override
    @DataSource(value = "read")
    public List<ThirdGoodsAreas> getThirdGoodsAreasByBDCityCode(String bdCityCode) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("bdCityCode", bdCityCode);
        return thirdGoodsAreasDao.queryList(map);
    }

}
