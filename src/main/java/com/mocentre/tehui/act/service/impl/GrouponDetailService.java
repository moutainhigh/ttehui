package com.mocentre.tehui.act.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mocentre.tehui.act.dao.IGrouponDetailDao;
import com.mocentre.tehui.act.mapper.GrouponMapper;
import com.mocentre.tehui.act.model.GrouponDetail;
import com.mocentre.tehui.act.service.IGrouponDetailService;
import com.mocentre.tehui.core.service.support.datasource.annotation.DataSource;
import com.mocentre.tehui.frontend.param.GrouponDetailParam;

/**
 * 参团信息详情接口实现类
 * 
 * @author Created by yukaiji on 2017年02月09日
 */
@Component
public class GrouponDetailService implements IGrouponDetailService {

    @Autowired
    private IGrouponDetailDao GrouponDetailDao;

    @Override
    @DataSource(value = "read")
    public List<GrouponDetail> getByGourponId(Long grouponId) {
        return GrouponDetailDao.getDetailList(grouponId);
    }

    @Override
    @DataSource(value = "write")
    public GrouponDetail participateGrouponDetail(GrouponDetailParam grouponDetailParam) {
        GrouponDetail grouponDetail = new GrouponDetail();
        grouponDetail = GrouponMapper.toGrouponDetail(grouponDetailParam);
        GrouponDetailDao.saveEntity(grouponDetail);
        return grouponDetail;
    }

    @Override
    @DataSource(value = "read")
    public GrouponDetail getByUserId(Long grouponId, Long takeUserId) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("grouponId", grouponId);
        param.put("takeUserId", takeUserId);
        return GrouponDetailDao.getGrouponDetailByParam(param);
    }

}
