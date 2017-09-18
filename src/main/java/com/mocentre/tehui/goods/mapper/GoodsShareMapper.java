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

import org.springframework.cglib.beans.BeanCopier;

import com.mocentre.tehui.backend.model.GoodsShareInstance;
import com.mocentre.tehui.goods.model.GoodsShare;

/**
 * Created by Arvin on 16/12/12.
 */
public class GoodsShareMapper {

    public static GoodsShareInstance toInstance(GoodsShare share) {
        GoodsShareInstance shareIns = new GoodsShareInstance();
        BeanCopier copier = BeanCopier.create(GoodsShare.class, GoodsShareInstance.class, false);
        copier.copy(share, shareIns, null);
        return shareIns;
    }

    public static GoodsShareInstance toGoodsShareInstance(GoodsShare goodsShare) {
        GoodsShareInstance instance = new GoodsShareInstance();
        if (goodsShare != null) {
            BeanCopier copier = BeanCopier.create(GoodsShare.class, GoodsShareInstance.class, false);
            copier.copy(goodsShare, instance, null);
        }
        return instance;
    }

}
