package com.mocentre.tehui.bak.mapper;

import com.mocentre.tehui.backend.model.MallHomeInstance;
import com.mocentre.tehui.backend.param.MallHomeParam;
import com.mocentre.tehui.bak.enums.MallHomeGoodsType;
import com.mocentre.tehui.bak.enums.MallHomeShowLocal;
import com.mocentre.tehui.bak.model.MallHome;
import com.mocentre.tehui.frontend.model.MallHomeFTInstance;

import java.util.ArrayList;
import java.util.List;

/**
 * 掌银客户端参数转换类 Created by yukaiji on 2017/5/17.
 */
public class MallHomeMapper {

    public static MallHome toMallHome(MallHomeParam mallHomeParam) {
        MallHome mallHome = new MallHome();
        mallHome.setId(mallHomeParam.getId());
        mallHome.setLinkUrl(mallHomeParam.getLinkUrl());
        String goodsType = mallHomeParam.getGoodsType();
        if (MallHomeGoodsType.SECKILL.getCodeValue().equals(goodsType)) {
            mallHome.setGoodsType(MallHomeGoodsType.SECKILL.getCodeValue());
        } else if (MallHomeGoodsType.GROUPON.getCodeValue().equals(goodsType)) {
            mallHome.setGoodsType(MallHomeGoodsType.GROUPON.getCodeValue());
        } else if (MallHomeGoodsType.COMMON.getCodeValue().equals(goodsType)) {
            mallHome.setGoodsType(MallHomeGoodsType.COMMON.getCodeValue());
        }
        mallHome.setIsChain(mallHomeParam.getIsChain());
        mallHome.setGoodsId(mallHomeParam.getGoodsId());
        mallHome.setActivityId(mallHomeParam.getActivityId());
        mallHome.setActGoodsId(mallHomeParam.getActGoodsId());
        String showLocal = mallHomeParam.getShowLocal();
        if (MallHomeShowLocal.ACT.getCodeValue().equals(showLocal)) {
            mallHome.setShowLocal(MallHomeShowLocal.ACT.getCodeValue());
        } else if (MallHomeShowLocal.SPECIAL.getCodeValue().equals(showLocal)) {
            mallHome.setShowLocal(MallHomeShowLocal.SPECIAL.getCodeValue());
        }
        mallHome.setShowDes(mallHomeParam.getShowDes());
        mallHome.setShowImg(mallHomeParam.getShowImg());
        mallHome.setShowName(mallHomeParam.getShowName());
        mallHome.setShowPrice(mallHomeParam.getShowPrice());
        mallHome.setOldPrice(mallHomeParam.getOldPrice());
        mallHome.setSorting(mallHomeParam.getSorting());
        mallHome.setTagImg(mallHomeParam.getTagImg());
        return mallHome;
    }

    public static MallHomeFTInstance toMallHomeFTInstance(MallHome mallHome) {
        if (mallHome == null) {
            return null;
        }
        MallHomeFTInstance mallHomeFTIns = new MallHomeFTInstance();
        mallHomeFTIns.setLinkUrl(mallHome.getLinkUrl());
        mallHomeFTIns.setGoodsType(mallHome.getGoodsType());
        mallHomeFTIns.setIsChain(mallHome.getIsChain());
        mallHomeFTIns.setGoodsId(mallHome.getGoodsId());
        mallHomeFTIns.setActGoodsId(mallHome.getActGoodsId());
        mallHomeFTIns.setShowDes(mallHome.getShowDes());
        mallHomeFTIns.setShowImg(mallHome.getShowImg());
        mallHomeFTIns.setShowName(mallHome.getShowName());
        mallHomeFTIns.setShowPrice(mallHome.getShowPrice());
        mallHomeFTIns.setOldPrice(mallHome.getOldPrice());
        mallHomeFTIns.setTagImg(mallHome.getTagImg());
        return mallHomeFTIns;
    }

    public static List<MallHomeFTInstance> toMallHomeFTInstanceList(List<MallHome> mallHomes) {
        List<MallHomeFTInstance> list = new ArrayList<MallHomeFTInstance>();
        if (mallHomes == null || mallHomes.size() < 1) {
            return list;
        }
        for (MallHome mallHome : mallHomes) {
            list.add(toMallHomeFTInstance(mallHome));
        }
        return list;
    }

    public static MallHomeInstance toMallHomeIns(MallHome mallHome) {
        MallHomeInstance mallHomeIns = new MallHomeInstance();
        mallHomeIns.setLinkUrl(mallHome.getLinkUrl());
        mallHomeIns.setId(mallHome.getId());
        mallHomeIns.setGoodsType(mallHome.getGoodsType());
        mallHomeIns.setIsChain(mallHome.getIsChain());
        mallHomeIns.setGoodsId(mallHome.getGoodsId());
        mallHomeIns.setActivityId(mallHome.getActivityId());
        mallHomeIns.setActGoodsId(mallHome.getActGoodsId());
        mallHomeIns.setShowLocal(mallHome.getShowLocal());
        mallHomeIns.setShowDes(mallHome.getShowDes());
        mallHomeIns.setShowImg(mallHome.getShowImg());
        mallHomeIns.setShowName(mallHome.getShowName());
        mallHomeIns.setShowPrice(mallHome.getShowPrice());
        mallHomeIns.setOldPrice(mallHome.getOldPrice());
        mallHomeIns.setSorting(mallHome.getSorting());
        mallHomeIns.setTagImg(mallHome.getTagImg());
        return mallHomeIns;
    }

    public static List<MallHomeInstance> toMallHomeInsList(List<MallHome> mallHomes) {
        List<MallHomeInstance> mallHomeInstances = new ArrayList<MallHomeInstance>();
        if (mallHomes == null || mallHomes.size() < 1) {
            return mallHomeInstances;
        }
        for (MallHome mallHome : mallHomes) {
            mallHomeInstances.add(toMallHomeIns(mallHome));
        }
        return mallHomeInstances;
    }

}
