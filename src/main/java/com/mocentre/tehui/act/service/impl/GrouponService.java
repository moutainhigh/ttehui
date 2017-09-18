package com.mocentre.tehui.act.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.act.dao.IGrouponDao;
import com.mocentre.tehui.act.model.Groupon;
import com.mocentre.tehui.act.service.IGrouponService;
import com.mocentre.tehui.core.service.support.datasource.annotation.DataSource;
import com.mocentre.tehui.core.service.support.query.Requirement;

/**
 * 拼团信息详情接口实现类
 * 
 * @author Created by yukaiji on 2017年02月09日
 */
@Component
public class GrouponService implements IGrouponService {

    @Autowired
    private IGrouponDao grouponDao;

    @Override
    @DataSource(value = "read")
    public Groupon getGrouponById(Long id) {
        return grouponDao.get(id);
    }

    @Override
    @DataSource(value = "read")
    public List<Groupon> getGrouponByUnfinished(Long actGoodsId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("actGoodsId", actGoodsId);
        paramMap.put("isPay", "1");// 团长已付款
        return grouponDao.getAllByUnfinished(paramMap);
    }

    @Override
    @DataSource(value = "write")
    public Groupon saveGroupon(Groupon groupon) {
        grouponDao.saveEntity(groupon);
        return groupon;
    }

    @Override
    @DataSource(value = "read")
    public List<Groupon> queryGrouponNeedRefund() {
        return grouponDao.queryNeedClose();
    }

    @Override
    @DataSource(value = "read")
    public ListJsonResult<Groupon> queryGrouponPage(Requirement require) {
        return grouponDao.queryDatatablesPage(require);
    }

}
