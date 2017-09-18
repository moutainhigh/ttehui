package com.mocentre.tehui.td.mapper;

import com.mocentre.tehui.backend.model.ThirdGoodsAreasInstance;
import com.mocentre.tehui.backend.model.ThirdGoodsInstance;
import com.mocentre.tehui.backend.param.ThirdGoodsParam;
import com.mocentre.tehui.frontend.model.TdGoodsFTInstance;
import com.mocentre.tehui.frontend.model.ThirdGoodsFTInstance;
import com.mocentre.tehui.td.emnus.ThirdGoodsShowLocal;
import com.mocentre.tehui.td.model.ThirdGoods;
import com.mocentre.tehui.td.model.ThirdGoodsAreas;

import java.util.ArrayList;
import java.util.List;

/**
 * 掌银客户端参数转换类 Created by yukaiji on 2017/5/17.
 */
public class ThirdGoodsMapper {

    public static ThirdGoods toThirdGoods(ThirdGoodsParam thirdGoodsParam) {
        ThirdGoods thirdGoods = new ThirdGoods();
        thirdGoods.setId(thirdGoodsParam.getId());
        thirdGoods.setTitle(thirdGoodsParam.getTitle());
        thirdGoods.setLinkUrl(thirdGoodsParam.getLinkUrl());
        thirdGoods.setDescribe(thirdGoodsParam.getDescribe());
        thirdGoods.setShowImg(thirdGoodsParam.getShowImg());
        thirdGoods.setOldPrice(thirdGoodsParam.getOldPrice());
        thirdGoods.setSellPrice(thirdGoodsParam.getSellPrice());
        thirdGoods.setSorting(thirdGoodsParam.getSorting());
        String showLocal = thirdGoodsParam.getShowLocal();
        if (ThirdGoodsShowLocal.ACT.getCodeValue().equals(showLocal)) {
            thirdGoods.setShowLocal(ThirdGoodsShowLocal.ACT.getCodeValue());
        } else if (ThirdGoodsShowLocal.SPECIAL.getCodeValue().equals(showLocal)) {
            thirdGoods.setShowLocal(ThirdGoodsShowLocal.SPECIAL.getCodeValue());
        }
        return thirdGoods;
    }

    public static ThirdGoodsFTInstance toThirdGoodsFTInstance(ThirdGoods thirdGoods) {
        ThirdGoodsFTInstance thirdGoodsFTIns = new ThirdGoodsFTInstance();
        if (thirdGoods == null) {
            return thirdGoodsFTIns;
        }
        thirdGoodsFTIns.setLinkUrl(thirdGoods.getLinkUrl());
        thirdGoodsFTIns.setSellPrice(thirdGoods.getSellPrice());
        thirdGoodsFTIns.setTitle(thirdGoods.getTitle());
        thirdGoodsFTIns.setShowImg(thirdGoods.getShowImg());
        thirdGoodsFTIns.setDescribe(thirdGoods.getDescribe());
        thirdGoodsFTIns.setOldPrice(thirdGoods.getOldPrice());
        thirdGoodsFTIns.setSorting(thirdGoods.getSorting());
        return thirdGoodsFTIns;
    }

    public static List<ThirdGoodsFTInstance> toThirdGoodsFTInstanceList(List<ThirdGoods> thirdGoodss) {
        List<ThirdGoodsFTInstance> list = new ArrayList<ThirdGoodsFTInstance>();
        if (thirdGoodss == null || thirdGoodss.size() < 1) {
            return list;
        }
        for (ThirdGoods thirdGoods : thirdGoodss) {
            list.add(toThirdGoodsFTInstance(thirdGoods));
        }
        return list;
    }

    public static TdGoodsFTInstance toTdGoodsFTInstance(List<ThirdGoods> actGoodsList, List<ThirdGoods> specialGoodsList) {
        TdGoodsFTInstance instance = new TdGoodsFTInstance();
        List<ThirdGoodsFTInstance> actList = new ArrayList<>();
        List<ThirdGoodsFTInstance> specialList = new ArrayList<>();
        if(actGoodsList != null && actGoodsList.size()>0){
            for (ThirdGoods thirdGoods : actGoodsList) {
                actList.add(toThirdGoodsFTInstance(thirdGoods));
            }
        }
        if(specialGoodsList != null && specialGoodsList.size()>0){
            for (ThirdGoods thirdGoods : specialGoodsList) {
                specialList.add(toThirdGoodsFTInstance(thirdGoods));
            }
        }
        instance.setActGoodsList(actList);
        instance.setSpecialGoodsList(specialList);
        return instance;
    }

    public static ThirdGoodsInstance toThirdGoodsIns(ThirdGoods thirdGoods) {
        ThirdGoodsInstance thirdGoodsIns = new ThirdGoodsInstance();
        thirdGoodsIns.setLinkUrl(thirdGoods.getLinkUrl());
        thirdGoodsIns.setId(thirdGoods.getId());
        thirdGoodsIns.setShowLocal(thirdGoods.getShowLocal());
        thirdGoodsIns.setOldPrice(thirdGoods.getOldPrice());
        thirdGoodsIns.setSellPrice(thirdGoods.getSellPrice());
        thirdGoodsIns.setDescribe(thirdGoods.getDescribe());
        thirdGoodsIns.setShowImg(thirdGoods.getShowImg());
        thirdGoodsIns.setTitle(thirdGoods.getTitle());
        thirdGoodsIns.setSorting(thirdGoods.getSorting());
        return thirdGoodsIns;
    }

    public static List<ThirdGoodsInstance> toThirdGoodsInsList(List<ThirdGoods> thirdGoodss) {
        List<ThirdGoodsInstance> thirdGoodsInstances = new ArrayList<ThirdGoodsInstance>();
        if (thirdGoodss == null || thirdGoodss.size() < 1) {
            return thirdGoodsInstances;
        }
        for (ThirdGoods thirdGoods : thirdGoodss) {
            thirdGoodsInstances.add(toThirdGoodsIns(thirdGoods));
        }
        return thirdGoodsInstances;
    }

    public static ThirdGoodsAreasInstance toThirdGoodsAreasIns(ThirdGoodsAreas thirdGoodsAreas) {
        ThirdGoodsAreasInstance thirdGoodsAreasIns = new ThirdGoodsAreasInstance();
        thirdGoodsAreasIns.setCityName(thirdGoodsAreas.getCityName());
        thirdGoodsAreasIns.setCityCode(thirdGoodsAreas.getCityCode());
        thirdGoodsAreasIns.setLatitude(thirdGoodsAreas.getLatitude());
        thirdGoodsAreasIns.setLongitude(thirdGoodsAreas.getLongitude());
        thirdGoodsAreasIns.setGoodsId(thirdGoodsAreas.getGoodsId());
        thirdGoodsAreasIns.setBdCityCode(thirdGoodsAreas.getBdCityCode());
        return thirdGoodsAreasIns;
    }

    public static List<ThirdGoodsAreasInstance> toThirdGoodsAreasInsList(List<ThirdGoodsAreas> thirdGoodsAreasList) {
        List<ThirdGoodsAreasInstance> thirdGoodsAreasInstances = new ArrayList<ThirdGoodsAreasInstance>();
        if (thirdGoodsAreasList == null || thirdGoodsAreasList.size() < 1) {
            return thirdGoodsAreasInstances;
        }
        for (ThirdGoodsAreas thirdGoodsAreas : thirdGoodsAreasList) {
            thirdGoodsAreasInstances.add(toThirdGoodsAreasIns(thirdGoodsAreas));
        }
        return thirdGoodsAreasInstances;
    }
}
