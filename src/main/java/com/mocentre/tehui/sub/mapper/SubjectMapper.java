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

import com.mocentre.tehui.backend.model.SubjectInstance;
import com.mocentre.tehui.backend.param.SubjectParam;
import com.mocentre.tehui.frontend.model.SubjectFTInstance;
import com.mocentre.tehui.sub.model.Subject;
import com.mocentre.tehui.sub.model.SubjectTemplet;

/**
 * 类SubjectMapper.java的实现描述：专题转换
 * 
 * @author sz.gong 2016年12月12日 下午4:43:07
 */
public class SubjectMapper {

    public static SubjectInstance toSubjectInstance(Subject subject) {
        if (subject == null) {
            return null;
        }
        SubjectInstance subIns = new SubjectInstance();
        subIns.setBeCity(subject.getBeCity());
        subIns.setBeLatitude(subject.getBeLatitude());
        subIns.setBeLongitude(subject.getBeLongitude());
        subIns.setId(subject.getId());
        subIns.setGmtCreated(subject.getGmtCreated());
        subIns.setGmtModified(subject.getGmtModified());
        subIns.setIntro(subject.getIntro());
        subIns.setIsShow(subject.getIsShow());
        subIns.setShopId(subject.getShopId());
        subIns.setSorting(subject.getSorting());
        subIns.setSubTmpCode(subject.getSubTmpCode());
        subIns.setTitle(subject.getTitle());
        subIns.setTopBanner(subject.getTopBanner());
        return subIns;
    }

    public static SubjectInstance toSubjectInstance(Subject subject, SubjectTemplet subTemplate) {
        SubjectInstance subIns = toSubjectInstance(subject);
        if (subIns != null) {
            subIns.setTmpImgWidth(subTemplate.getImgWidth());
            subIns.setTmpImgHeight(subTemplate.getImgHeight());
        }
        return subIns;
    }

    public static List<SubjectInstance> toSubjectInstanceList(List<Subject> subjectList) {
        List<SubjectInstance> subjectInstances = new ArrayList<SubjectInstance>();
        if (subjectList == null || subjectList.size() < 1) {
            return subjectInstances;
        }
        for (Subject instance : subjectList) {
            subjectInstances.add(toSubjectInstance(instance));
        }
        return subjectInstances;
    }

    public static SubjectFTInstance toSubjectFTInstance(Subject subject) {
        SubjectFTInstance subIns = new SubjectFTInstance();
        subIns.setBeCity(subject.getBeCity());
        subIns.setBeLatitude(subject.getBeLatitude());
        subIns.setBeLongitude(subject.getBeLongitude());
        subIns.setId(subject.getId());
        subIns.setGmtCreated(subject.getGmtCreated());
        subIns.setGmtModified(subject.getGmtModified());
        subIns.setIntro(subject.getIntro());
        subIns.setIsShow(subject.getIsShow());
        subIns.setShopId(subject.getShopId());
        subIns.setSorting(subject.getSorting());
        subIns.setSubTmpCode(subject.getSubTmpCode());
        subIns.setTitle(subject.getTitle());
        subIns.setTopBanner(subject.getTopBanner());
        return subIns;
    }

    public static Subject toSubject(SubjectParam subParam) {
        Subject sub = new Subject();
        sub.setBeCity(subParam.getBeCity());
        sub.setBeLatitude(subParam.getBeLatitude());
        sub.setBeLongitude(subParam.getBeLongitude());
        sub.setId(subParam.getId());
        sub.setIntro(subParam.getIntro());
        sub.setIsShow(subParam.getIsShow());
        sub.setShopId(subParam.getShopId());
        sub.setSorting(subParam.getSorting());
        sub.setSubTmpCode(subParam.getSubTmpCode());
        sub.setTitle(subParam.getTitle());
        sub.setTopBanner(subParam.getTopBanner());
        return sub;
    }

    public static List<SubjectFTInstance> toSubjectFTInstanceList(List<Subject> subjectList) {
        List<SubjectFTInstance> subjectFTInstances = new ArrayList<>();
        if (subjectList == null || subjectList.size() < 1) {
            return subjectFTInstances;
        }
        for (Subject instance : subjectList) {
            subjectFTInstances.add(toSubjectFTInstance(instance));
        }
        return subjectFTInstances;
    }

}
