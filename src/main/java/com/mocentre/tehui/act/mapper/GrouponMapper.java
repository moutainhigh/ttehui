/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.act.mapper;

import com.mocentre.tehui.act.model.Groupon;
import com.mocentre.tehui.act.model.GrouponDetail;
import com.mocentre.tehui.backend.model.GrouponInstance;
import com.mocentre.tehui.frontend.model.GrouponFTInstance;
import com.mocentre.tehui.frontend.param.GrouponDetailParam;
import com.mocentre.tehui.frontend.param.GrouponParam;

import java.util.ArrayList;
import java.util.List;

/**
 * 拼团信息转换
 * 
 * @author Created by yukaiji on 2017年02月09日
 */
public class GrouponMapper {

    /**
     * 拼团信息转前端
     */
    public static GrouponFTInstance toGrouponFTInstance(Groupon groupon) {
        GrouponFTInstance grouponFTInstance = new GrouponFTInstance();
        grouponFTInstance.setActGoodsId(groupon.getActGoodsId());
        grouponFTInstance.setId(groupon.getId());
        grouponFTInstance.setOpenProfile(groupon.getOpenProfile());
        grouponFTInstance.setOpenTime(groupon.getOpenTime());
        grouponFTInstance.setCloseTime(groupon.getCloseTime());
        grouponFTInstance.setOpenUserId(groupon.getOpenUserId());
        grouponFTInstance.setBeginTime(groupon.getBeginTime());
        grouponFTInstance.setEndTime(groupon.getEndTime());
        grouponFTInstance.setTakeNum(groupon.getTakeNum());
        return grouponFTInstance;
    }

    /**
     * 拼团信息转前端
     */
    public static GrouponInstance toGrouponInstance(Groupon groupon) {
        GrouponInstance grouponInstance = new GrouponInstance();
        grouponInstance.setOpenUserId(groupon.getOpenUserId());
        grouponInstance.setId(groupon.getId());
        grouponInstance.setOpenTime(groupon.getOpenTime());
        grouponInstance.setCloseTime(groupon.getCloseTime());
        grouponInstance.setTakeNum(groupon.getTakeNum());
        grouponInstance.setGrouponNum(groupon.getGrouponNum());
        grouponInstance.setGroupStatus(groupon.getGroupStatus());
        grouponInstance.setIsPay(groupon.getIsPay());
        return grouponInstance;
    }

    /**
     * 拼团信息转前端
     */
    public static List<GrouponFTInstance> toGrouponFTInstanceList(List<Groupon> grouponList) {
        List<GrouponFTInstance> grouponFTInsList = new ArrayList<GrouponFTInstance>();
        if (grouponList == null || grouponList.size() < 1) {
            return grouponFTInsList;
        }
        for (Groupon groupon : grouponList) {
            grouponFTInsList.add(toGrouponFTInstance(groupon));
        }
        return grouponFTInsList;
    }

    /**
     * 参数转拼团信息
     */
    public static Groupon toGroupon(GrouponParam grouponParam) {
        Groupon groupon = new Groupon();
        groupon.setActGoodsId(grouponParam.getActGoodsId());
        groupon.setId(grouponParam.getId());
        groupon.setOpenProfile(grouponParam.getOpenProfile());
        groupon.setOpenUserId(grouponParam.getOpenUserId());
        groupon.setBeginTime(grouponParam.getBeginTime());
        groupon.setEndTime(grouponParam.getEndTime());
        return groupon;
    }

    /**
     * 参数转参团人信息
     */
    public static GrouponDetail toGrouponDetail(GrouponDetailParam grouponDetailParam) {
        GrouponDetail grouponDetail = new GrouponDetail();
        grouponDetail.setActGoodsId(grouponDetailParam.getActGoodsId());
        grouponDetail.setGrouponId(grouponDetailParam.getGrouponId());
        grouponDetail.setIsHead(grouponDetailParam.getIsHead());
        grouponDetail.setTakeProfile(grouponDetailParam.getTakeProfile());
        grouponDetail.setTakeUserId(grouponDetailParam.getTakeUserId());
        return grouponDetail;
    }

    public static List<GrouponInstance> toGrouponInstanceList(List<Groupon> grouponList) {
        List<GrouponInstance> grouponInsList = new ArrayList<GrouponInstance>();
        if (grouponList == null || grouponList.size() < 1) {
            return grouponInsList;
        }
        for (Groupon groupon : grouponList) {
            grouponInsList.add(toGrouponInstance(groupon));
        }
        return grouponInsList;
    }
}
