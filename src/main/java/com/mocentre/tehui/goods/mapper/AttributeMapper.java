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

import com.mocentre.tehui.backend.model.AttributeInstance;
import com.mocentre.tehui.goods.model.Attribute;
import org.springframework.cglib.beans.BeanCopier;

/**
 * Created by Arvin on 16/12/12.
 */
public class AttributeMapper {

    public static AttributeInstance toInstance(Attribute attribute) {
        AttributeInstance attributeInstance = new AttributeInstance();
        BeanCopier copier = BeanCopier.create(Attribute.class, AttributeInstance.class,false);
        copier.copy(attribute, attributeInstance, null);
        return attributeInstance;
    }

}
