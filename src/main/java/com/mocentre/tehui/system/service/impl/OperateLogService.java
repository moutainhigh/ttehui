package com.mocentre.tehui.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.core.service.support.datasource.annotation.DataSource;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.system.dao.IOperateLogDao;
import com.mocentre.tehui.system.model.OperateLog;
import com.mocentre.tehui.system.service.IOperateLogServie;

@Service
public class OperateLogService implements IOperateLogServie {

    @Autowired
    private IOperateLogDao operateLogDao;

    @Override
    @DataSource(value = "read")
    public ListJsonResult<OperateLog> queryPage(Requirement require) {
        return operateLogDao.queryDatatablesPage(require);
    }

    @Override
    @DataSource(value = "write")
    public void saveOperateLog(OperateLog log) {
        operateLogDao.saveEntity(log);
    }

}
