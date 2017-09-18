/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.sub.mapper;

import com.mocentre.tehui.backend.model.SubjectGoodsInstance;
import com.mocentre.tehui.backend.param.SubjectGoodsParam;
import com.mocentre.tehui.frontend.model.SubjectGoodsFTInstance;
import com.mocentre.tehui.sub.model.SubjectGoods;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 类SubjectGoodsMapper.java的实现描述：专题商品转换器
 * 
 * @author sz.gong 2016年12月12日 下午5:49:30
 */
public class SubjectGoodsMapper {

    public static SubjectGoods toSubjectGoods(SubjectGoodsParam sbgParam) {
        SubjectGoods subGoods = new SubjectGoods();
        subGoods.setGoodsId(sbgParam.getGoodsId());
        subGoods.setGoodsImg(sbgParam.getGoodsImg());
        subGoods.setId(sbgParam.getId());
        subGoods.setSubjectId(sbgParam.getSubjectId());
        subGoods.setTagline(sbgParam.getTagline());
        subGoods.setSorting(sbgParam.getSorting());
        return subGoods;
    }

    public static SubjectGoodsInstance toSubjectGoodsInstance(SubjectGoods subjectGoods) {
        SubjectGoodsInstance subGoodsIns = new SubjectGoodsInstance();
        subGoodsIns.setGoodsId(subjectGoods.getGoodsId());
        subGoodsIns.setGoodsImg(subjectGoods.getGoodsImg());
        subGoodsIns.setGoodsName(subjectGoods.getGoodsName());
        subGoodsIns.setId(subjectGoods.getId());
        subGoodsIns.setOldPrice(subjectGoods.getOldPrice());
        subGoodsIns.setSellPrice(subjectGoods.getSellPrice());
        subGoodsIns.setSubjectId(subjectGoods.getSubjectId());
        subGoodsIns.setTagline(subjectGoods.getTagline());
        subGoodsIns.setGmtCreated(subjectGoods.getGmtCreated());
        subGoodsIns.setGmtModified(subjectGoods.getGmtModified());
        subGoodsIns.setSorting(subjectGoods.getSorting());
        Long sellLowPrice = subjectGoods.getSellLowPrice();
        Long oldLowPrice = subjectGoods.getOldLowPrice();
        subGoodsIns.setSellLowPrice(sellLowPrice == null ? new BigDecimal(0) : new BigDecimal(sellLowPrice)
                .divide(new BigDecimal(100)));
        subGoodsIns.setOldLowPrice(oldLowPrice == null ? new BigDecimal(0) : new BigDecimal(oldLowPrice)
                .divide(new BigDecimal(100)));
        return subGoodsIns;
    }

    public static SubjectGoodsFTInstance toSubjectGoodsFTInstance(SubjectGoods subjectGoods) {
        SubjectGoodsFTInstance subGoodsFTIns = new SubjectGoodsFTInstance();
        subGoodsFTIns.setGoodsId(subjectGoods.getGoodsId());
        subGoodsFTIns.setGoodsImg(subjectGoods.getGoodsImg());
        subGoodsFTIns.setGoodsName(subjectGoods.getGoodsName());
        subGoodsFTIns.setId(subjectGoods.getId());
        subGoodsFTIns.setOldPrice(subjectGoods.getOldPrice());
        subGoodsFTIns.setSellPrice(subjectGoods.getSellPrice());
        subGoodsFTIns.setSubjectId(subjectGoods.getSubjectId());
        subGoodsFTIns.setTagline(subjectGoods.getTagline());
        subGoodsFTIns.setGmtCreated(subjectGoods.getGmtCreated());
        subGoodsFTIns.setGmtModified(subjectGoods.getGmtModified());
        subGoodsFTIns.setSorting(subjectGoods.getSorting());
        Long sellLowPrice = subjectGoods.getSellLowPrice();
        Long oldLowPrice = subjectGoods.getOldLowPrice();
        subGoodsFTIns.setSellLowPrice(sellLowPrice == null ? new BigDecimal(0) : new BigDecimal(sellLowPrice)
                .divide(new BigDecimal(100)));
        subGoodsFTIns.setOldLowPrice(oldLowPrice == null ? new BigDecimal(0) : new BigDecimal(oldLowPrice)
                .divide(new BigDecimal(100)));
        return subGoodsFTIns;
    }

    public static List<SubjectGoodsInstance> toSubjectGoodsInstanceList(List<SubjectGoods> subjectGoodsList) {
        List<SubjectGoodsInstance> subGoodsInsList = new ArrayList<SubjectGoodsInstance>();
        if (subjectGoodsList == null || subjectGoodsList.size() < 1) {
            return subGoodsInsList;
        }
        for (SubjectGoods subGoods : subjectGoodsList) {
            subGoodsInsList.add(toSubjectGoodsInstance(subGoods));
        }
        return subGoodsInsList;
    }

    public static List<SubjectGoodsFTInstance> toSubjectGoodsFTInstanceList(List<SubjectGoods> subjectGoodsList) {
        List<SubjectGoodsFTInstance> subGoodsFTInsList = new ArrayList<SubjectGoodsFTInstance>();
        if (subjectGoodsList == null || subjectGoodsList.size() < 1) {
            return subGoodsFTInsList;
        }
        for (SubjectGoods subGoods : subjectGoodsList) {
            subGoodsFTInsList.add(toSubjectGoodsFTInstance(subGoods));
        }
        return subGoodsFTInsList;
    }
}
