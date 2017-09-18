package com.mocentre.tehui.system.provider;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.mocentre.tehui.AreasManageService;
import com.mocentre.tehui.common.utils.LoggerUtil;
import com.mocentre.tehui.system.model.Areas;
import com.mocentre.tehui.system.service.IAreasService;

/**
 * 区域信息接口. Created by yukaiji on 2016/11/22.
 */
public class AreasManageServiceImpl implements AreasManageService {

    @Autowired
    private IAreasService areasService;

    @Override
    public String getAll() {
        List<Areas> areasList = new ArrayList<Areas>();
        try {
            areasList = areasService.getAll();
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("getAll", e);
        }
        return JSON.toJSONString(areasList);
    }

    @Override
    public String getAllProvinceTwoLinkage() {
        List<Areas> provinceList = new ArrayList<Areas>();
        List<Areas> cityList = new ArrayList<Areas>();
        String result = null;
        try {
            provinceList = areasService.getAllProvince();
            cityList = areasService.getAllCity();
            for (Areas provinces : provinceList) {
                List<Areas> childrenList = new ArrayList<Areas>();
                for (Areas citys : cityList) {
                    if (citys.getpCode().equals(provinces.getCode())) {
                        childrenList.add(citys);
                    }
                }
                provinces.setChildrenList(childrenList);
            }
            result = JSON.toJSONString(provinceList);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("getAllProvince", e);
        }
        return result;
    }

    @Override
    public String getAllProvinceThreeLinkage() {
        List<Areas> provinceList = new ArrayList<Areas>();
        List<Areas> cityList = new ArrayList<Areas>();
        List<Areas> areasList = new ArrayList<Areas>();
        String result = null;
        try {
            provinceList = areasService.getAllProvince();
            cityList = areasService.getAllCity();
            areasList = areasService.getAllAreas();
            for (Areas province : provinceList) {
                List<Areas> childrenList = new ArrayList<Areas>();
                for (Areas citys : cityList) {
                    List<Areas> childrenList1 = new ArrayList<Areas>();
                    for (Areas areas : areasList) {
                        if (citys.getCode().equals(areas.getpCode())) {
                            childrenList1.add(areas);
                        }
                    }
                    citys.setChildrenList(childrenList1);
                    if (province.getCode().equals(citys.getpCode())) {
                        childrenList.add(citys);
                    }
                }
                province.setChildrenList(childrenList);
            }
            result = JSON.toJSONString(provinceList);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("getAllProvinceThree", e);
        }
        return result;
    }

    @Override
    public String get(String p_code) {
        List<Areas> areasList = new ArrayList<Areas>();
        try {
            areasList = areasService.get(p_code);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("get", e);
        }
        return JSON.toJSONString(areasList);
    }

    @Override
    public String getAreas(String code) {
        Areas areas = new Areas();
        try {
            areas = areasService.getAreas(code);
        } catch (Exception e) {
            LoggerUtil.tehuiLog.error("getAreas", e);
        }
        return JSON.toJSONString(areas);
    }

}
