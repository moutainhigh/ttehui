package com.mocentre.tehui.ntc.dao;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.ntc.model.NtcMail;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 通知邮件dao接口
 *
 * Created by wangxueying on 17/08/08
 */
public interface INtcMailDao {

    NtcMail getById(Long id);

    /**
     * 分页查询
     */
    ListJsonResult<NtcMail> queryDatatablesPage(Requirement require);

    List<NtcMail> queryList(Map<String, Object> paramMap);

    Long saveEntity(NtcMail ntcMail);

    Long updateEntity(NtcMail ntcMail);

    int logicRemoveById(Serializable id);
}
