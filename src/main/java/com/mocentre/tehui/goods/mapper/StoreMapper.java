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

import com.mocentre.tehui.frontend.model.StoreFTInstance;
import com.mocentre.tehui.frontend.param.StoreParam;
import com.mocentre.tehui.goods.model.Store;

/**
 * Created by Arvin on 16/12/12.
 */
public class StoreMapper {

    public static StoreFTInstance toInstance(Store store) {
        StoreFTInstance storeFTInstance = new StoreFTInstance();
        BeanCopier copier = BeanCopier.create(Store.class, StoreFTInstance.class, false);
        copier.copy(store, storeFTInstance, null);
        return storeFTInstance;
    }

    public static List<StoreFTInstance> toInstanceList(List<Store> lists) {
        if (lists.size() < 1) {
            return null;
        }
        List<StoreFTInstance> listResult = new ArrayList<StoreFTInstance>();
        for (Store store : lists) {
            StoreFTInstance storeFTInstance = toInstance(store);
            listResult.add(storeFTInstance);
        }
        return listResult;
    }

    /**
     * 请求参数转换
     * 
     * @param storeParam
     * @return
     */
    public static Store toCouponModel(StoreParam storeParam) {
        Store store = new Store();
        BeanCopier copier = BeanCopier.create(StoreParam.class, Store.class, false);
        copier.copy(storeParam, store, null);
        return store;
    }
}
