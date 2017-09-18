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

import com.mocentre.tehui.backend.model.DiscoverInstance;
import com.mocentre.tehui.backend.param.DiscoverParam;
import com.mocentre.tehui.frontend.model.DiscoverFTInstance;
import com.mocentre.tehui.goods.model.Discover;

/**
 * Created by Arvin on 16/12/12.
 */
public class DiscoverMapper {

    public static DiscoverInstance toInstance(Discover discover) {
        DiscoverInstance discoverInstance = new DiscoverInstance();
        BeanCopier copier = BeanCopier.create(Discover.class, DiscoverInstance.class, false);
        copier.copy(discover, discoverInstance, null);
        return discoverInstance;
    }

    public static DiscoverFTInstance toDiscoverFTInstance(Discover discover) {
        DiscoverFTInstance discoverFTInstance = new DiscoverFTInstance();
        BeanCopier copier = BeanCopier.create(Discover.class, DiscoverFTInstance.class, false);
        copier.copy(discover, discoverFTInstance, null);
        return discoverFTInstance;
    }

    public static List<DiscoverInstance> toInstanceList(List<Discover> lists) {
        if (lists == null || lists.size() < 1) {
            return null;
        }
        List<DiscoverInstance> listResult = new ArrayList<DiscoverInstance>();
        for (Discover discover : lists) {
            DiscoverInstance discoverInstance = DiscoverMapper.toInstance(discover);
            listResult.add(discoverInstance);
        }
        return listResult;
    }

    public static List<DiscoverFTInstance> toDiscoverFTInstanceList(List<Discover> lists) {
        List<DiscoverFTInstance> listResult = new ArrayList<DiscoverFTInstance>();
        if (lists.size() < 1) {
            return listResult;
        }
        for (Discover discover : lists) {
            DiscoverFTInstance discoverInstance = DiscoverMapper.toDiscoverFTInstance(discover);
            listResult.add(discoverInstance);
        }
        return listResult;
    }

    public static Discover toDiscover(DiscoverParam discoverParam) {
        Discover discover = new Discover();
        Integer isGoods = discoverParam.getIsGoods();
        discover.setShopId(discoverParam.getShopId());
        discover.setActName(discoverParam.getActName());
        discover.setIsGoods(isGoods);
        discover.setShowImg(discoverParam.getShowImg());
        if (isGoods == 1) {
            discover.setGoodsId(Long.valueOf(discoverParam.getGoodsId()));
            discover.setGoodsChannel(discoverParam.getGoodsChannel());
        } else {
            discover.setUrl(discoverParam.getUrl());
        }
        discover.setIsShow(discoverParam.getIsShow());
        discover.setSorting(discoverParam.getSorting());
        return discover;
    }
}
