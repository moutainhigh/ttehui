package com.mocentre.tehui.system.mapper;

import java.util.ArrayList;
import java.util.List;

import com.mocentre.tehui.frontend.model.AreasFTInstance;
import com.mocentre.tehui.system.model.Areas;

/**
 * 地区转换
 * 
 * @author Created by yukaiji on 2017年2月14日
 */
public class AreasMapper {

    public static AreasFTInstance toAreasFTInstance(Areas areas) {
        AreasFTInstance areasFTIns = new AreasFTInstance();
        areasFTIns.setCode(areas.getCode());
        areasFTIns.setName(areas.getName());
        areasFTIns.setpCode(areas.getpCode());
        areasFTIns.setBdCityCode(areas.getBdCityCode());
        areasFTIns.setLongitude(areas.getLongitude());
        areasFTIns.setLatitude(areas.getLatitude());
        return areasFTIns;
    }

    public static List<AreasFTInstance> toAreasFTInstanceList(List<Areas> areasList) {
        List<AreasFTInstance> areasFTList = new ArrayList<>();
        if (areasList == null || areasList.size() < 1) {
            return areasFTList;
        }
        for (Areas areas : areasList) {
            areasFTList.add(toAreasFTInstance(areas));
        }
        return areasFTList;
    }
}
