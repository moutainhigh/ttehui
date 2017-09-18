/*
 * Copyright 2016 mocentre.com All right reserved. This software is the
 * confidential and proprietary information of mocentre.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with mocentre.com .
 */
package com.mocentre.tehui.ntc.mapper;


import com.mocentre.tehui.backend.model.NtcMailInstance;
import com.mocentre.tehui.backend.param.NtcMailParam;
import com.mocentre.tehui.ntc.model.NtcMail;

import java.util.ArrayList;
import java.util.List;

/**
 * 通知邮件转换
 *
 * Created by wangxueying on 17/08/08
 */
public class NtcMailMapper {

    public static NtcMailInstance toNtcMailInstance(NtcMail ntcMail) {
        NtcMailInstance instance = new NtcMailInstance();
        instance.setMail(ntcMail.getMail());
        instance.setNoticeType(ntcMail.getNoticeType());
        instance.setId(ntcMail.getId());
        return instance;
    }
    public static NtcMail toNtcMail(NtcMailParam param) {
        NtcMail ntcMail = new NtcMail();
        ntcMail.setMail(param.getMail());
        ntcMail.setNoticeType(param.getNoticeType());
        ntcMail.setId(param.getId());
        return ntcMail;
    }

    public static List<NtcMailInstance> toNtcMailInstanceList(List<NtcMail> ntcMailList) {
        List<NtcMailInstance> instanceList = new ArrayList<NtcMailInstance>();
        if (ntcMailList == null || ntcMailList.size() < 1) {
            return instanceList;
        }
        for (NtcMail instance : ntcMailList) {
            instanceList.add(toNtcMailInstance(instance));
        }
        return instanceList;
    }
}
