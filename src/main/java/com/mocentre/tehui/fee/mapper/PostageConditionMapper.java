/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.fee.mapper;

import com.mocentre.tehui.backend.model.SubjectTempletInstance;
import com.mocentre.tehui.sub.model.SubjectTemplet;

/**
 * 类SubjectTempletMapper.java的实现描述：专题模板转换
 * 
 * @author sz.gong 2016年12月12日 下午6:16:38
 */
public class PostageConditionMapper {

    public static SubjectTempletInstance toSubjectTempletInstance(SubjectTemplet subTemp) {
        SubjectTempletInstance subTempIns = new SubjectTempletInstance();
        subTempIns.setCode(subTemp.getCode());
        subTempIns.setId(subTemp.getId());
        subTempIns.setImgHeight(subTemp.getImgHeight());
        subTempIns.setImgWidth(subTemp.getImgWidth());
        return subTempIns;
    }

}
