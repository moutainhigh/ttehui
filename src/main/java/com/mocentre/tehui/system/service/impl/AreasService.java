/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mocentre.tehui.core.service.support.datasource.annotation.DataSource;
import com.mocentre.tehui.system.dao.IAreasDao;
import com.mocentre.tehui.system.enums.AreasType;
import com.mocentre.tehui.system.model.Areas;
import com.mocentre.tehui.system.service.IAreasService;

/**
 * 类AreasService.java的实现描述：区域实现类
 * 
 * @author sz.gong 2016年11月17日 上午11:36:49
 */
@Component
public class AreasService implements IAreasService {

    @Autowired
    private IAreasDao areasDao;

    @Override
    @DataSource(value = "read")
    public List<Areas> getAll() {
        return areasDao.getAll();
    }

    @Override
    @DataSource(value = "read")
    public List<Areas> getAllProvince() {
        return areasDao.getAllProvince();
    }

    @Override
    @DataSource(value = "read")
    public List<Areas> getAllCity() {
        return areasDao.getAllCity();
    }

    @Override
    @DataSource(value = "read")
    public List<Areas> getAllAreas() {
        return areasDao.getAllAreas();
    }

    @Override
    @DataSource(value = "read")
    public List<Areas> get(String p_code) {
        return areasDao.get(p_code);
    }

    @Override
    @DataSource(value = "read")
    public Areas getAreas(String code) {
        return areasDao.getAreas(code);
    }

    @Override
    @DataSource(value = "read")
    public String getName(String code) {
        Areas areas = this.getAreas(code);
        return areas.getName();
    }

    @Override
    @DataSource(value = "read")
    public List<Areas> getProvinceFromCache() {
        List<Areas> proList = new ArrayList<Areas>();
        List<Areas> areasList = areasDao.getAllFromCache();
        for (Areas areas : areasList) {
            if (AreasType.PROVINCE.getCodeValue().equals(areas.getType())) {
                proList.add(areas);
            }
        }
        return proList;
    }

    @Override
    @DataSource(value = "read")
    public List<Areas> getCityFromCache() {
        List<Areas> proList = new ArrayList<Areas>();
        List<Areas> areasList = areasDao.getAllFromCache();
        for (Areas areas : areasList) {
            if (AreasType.CITY.getCodeValue().equals(areas.getType())) {
                proList.add(areas);
            }
        }
        return proList;
    }

    @Override
    @DataSource(value = "read")
    public List<Areas> getAreaFromCache() {
        List<Areas> proList = new ArrayList<Areas>();
        List<Areas> areasList = areasDao.getAllFromCache();
        for (Areas areas : areasList) {
            if (AreasType.AREA.getCodeValue().equals(areas.getType())) {
                proList.add(areas);
            }
        }
        return proList;
    }

    @Override
    @DataSource(value = "read")
    public List<Areas> getAreasFromCache() {
        List<Areas> areasList = areasDao.getAllFromCache();
        return areasList;
    }

    @Override
    @DataSource(value = "read")
    public Areas getAreasFromCache(String code) {
        return areasDao.getFromCache(code);
    }

    @Override
    @DataSource(value = "read")
    public String getNameFromCache(String code) {
        Areas areas = this.getAreasFromCache(code);
        if (areas == null) {
            return null;
        }
        return areas.getName();
    }

    @Override
    @DataSource(value = "read")
    public void getAllAreasToCache() {
        areasDao.getAllToCache();
    }

}
