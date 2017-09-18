package com.mocentre.tehui.act.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mocentre.tehui.act.dao.IGrouponDetailDao;
import com.mocentre.tehui.act.model.GrouponDetail;
import com.mocentre.tehui.core.dao.BaseDao;

/**
 * 参团表数据库操作接口实现. Created by yukaiji on 2017/02/09.
 */
@Repository
public class GrouponDetailDao extends BaseDao<GrouponDetail> implements IGrouponDetailDao {

    @Override
    public List<GrouponDetail> getDetailList(Long grouponId) {
        return super.getList(entityClass.getSimpleName() + POSTFIX_GET, grouponId);
    }

    @Override
    public GrouponDetail getGrouponDetailByParam(Map<String, Object> param) {
        return super.queryUniquely(entityClass.getSimpleName() + "_getByParam", param);
    }

    @Override
    public int updateGrouponDetailPay(Long grouponId, Long takeUserId) {
        GrouponDetail gDetail = new GrouponDetail();
        gDetail.setTakeUserId(takeUserId);
        gDetail.setGrouponId(grouponId);
        gDetail.setIsPay(1);
        return super.update("GrouponDetail_update", gDetail);
    }

}
