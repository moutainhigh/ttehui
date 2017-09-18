/*
 *
 *  * Copyright 2016 mocentre.com All right reserved. This software is the
 *  * confidential and proprietary information of mocentre.com ("Confidential
 *  * Information"). You shall not disclose such Confidential Information and shall
 *  * use it only in accordance with the terms of the license agreement you entered
 *  * into with mocentre.com .
 *
 */

package com.mocentre.tehui.goods.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cglib.beans.BeanCopier;

import com.mocentre.tehui.backend.model.GoodsLaunchInstance;
import com.mocentre.tehui.goods.model.GoodsLaunch;

/**
 * Created by Arvin on 16/12/12.
 */
public class GoodsLaunchMapper {

    public static GoodsLaunchInstance toInstance(GoodsLaunch goodsLaunch) {
        GoodsLaunchInstance launchIns = new GoodsLaunchInstance();
        BeanCopier copier = BeanCopier.create(GoodsLaunch.class, GoodsLaunchInstance.class, false);
        copier.copy(goodsLaunch, launchIns, null);
        return launchIns;
    }

    public static List<GoodsLaunchInstance> toInstanceList(List<GoodsLaunch> goodsLaunchList) {
        List<GoodsLaunchInstance> launchInsList = new ArrayList<GoodsLaunchInstance>();
        for (GoodsLaunch goodsLaunch : goodsLaunchList) {
            launchInsList.add(toInstance(goodsLaunch));
        }
        return launchInsList;
    }

}
