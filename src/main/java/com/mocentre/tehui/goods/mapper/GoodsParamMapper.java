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

import com.mocentre.tehui.backend.model.GoodsParamInstance;
import com.mocentre.tehui.goods.model.GoodsParam;

/**
 * Created by Arvin on 16/12/12.
 */
public class GoodsParamMapper {

    public static GoodsParamInstance toInstance(GoodsParam param) {
        GoodsParamInstance paramIns = new GoodsParamInstance();
        BeanCopier copier = BeanCopier.create(GoodsParam.class, GoodsParamInstance.class, false);
        copier.copy(param, paramIns, null);
        return paramIns;
    }

}
