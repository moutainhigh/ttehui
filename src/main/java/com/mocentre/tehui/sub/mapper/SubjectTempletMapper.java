/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.sub.mapper;

import java.util.ArrayList;
import java.util.List;

import com.mocentre.tehui.backend.model.SubjectTempletInstance;
import com.mocentre.tehui.sub.model.SubjectTemplet;

/**
 * 类SubjectTempletMapper.java的实现描述：专题模板转换
 * 
 * @author sz.gong 2016年12月12日 下午6:16:38
 */
public class SubjectTempletMapper {

    public static SubjectTempletInstance toSubjectTempletInstance(SubjectTemplet subTemp) {
        SubjectTempletInstance subTempIns = new SubjectTempletInstance();
        subTempIns.setName(subTemp.getName());
        subTempIns.setCode(subTemp.getCode());
        subTempIns.setId(subTemp.getId());
        subTempIns.setImgHeight(subTemp.getImgHeight());
        subTempIns.setImgWidth(subTemp.getImgWidth());
        subTempIns.setGmtCreated(subTemp.getGmtCreated());
        subTempIns.setGmtModified(subTemp.getGmtModified());
        return subTempIns;
    }
    
    public static List<SubjectTempletInstance> toSubjectTempletInstanceList(List<SubjectTemplet> subTempList) {
    	List<SubjectTempletInstance> subTmpInsList = new ArrayList<SubjectTempletInstance>();
		if (subTempList == null || subTempList.size() < 1){
            return subTmpInsList;
        }
		
		for (SubjectTemplet subTmp : subTempList) {
			subTmpInsList.add(toSubjectTempletInstance(subTmp));
		}
        return subTmpInsList;
    }

}
