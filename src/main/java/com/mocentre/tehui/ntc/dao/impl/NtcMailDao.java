package com.mocentre.tehui.ntc.dao.impl;

import com.mocentre.tehui.core.dao.BaseDao;
import com.mocentre.tehui.ntc.dao.INtcMailDao;
import com.mocentre.tehui.ntc.model.NtcMail;
import org.springframework.stereotype.Repository;


/**
 * 通知邮件dao
 *
 * Created by wangxueying on 17/08/08
 */
@Repository
public class NtcMailDao extends BaseDao<NtcMail> implements INtcMailDao {

    @Override
    public NtcMail getById(Long id) {
        return super.get(id);
    }
}
