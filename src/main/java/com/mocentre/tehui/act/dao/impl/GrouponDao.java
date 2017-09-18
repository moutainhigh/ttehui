package com.mocentre.tehui.act.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mocentre.tehui.act.dao.IGrouponDao;
import com.mocentre.tehui.act.enums.GroupStatus;
import com.mocentre.tehui.act.model.Groupon;
import com.mocentre.tehui.core.dao.BaseDao;

/**
 * 拼团详情数据库操作接口实现. Created by yukaiji on 2017/02/09.
 */
@Repository
public class GrouponDao extends BaseDao<Groupon> implements IGrouponDao {

    @Override
    public List<Groupon> getAllByUnfinished(Map<String, Object> paramMap) {
        return super.getList(entityClass.getSimpleName() + "_byUnfinished", paramMap);
    }

    @Override
    public void updateStatusAndPay(Long id, String groupStatus) {
        Groupon groupon = new Groupon();
        groupon.setId(id);
        groupon.setGroupStatus(groupStatus);
        if (GroupStatus.GROUPED.getCodeValue().equals(groupStatus)) {
            groupon.setGroupTime(new Date());
        }
        groupon.setIsPay(1);
        super.updateEntity(groupon);
    }

    @Override
    public void updateStatus(Long id, String groupStatus) {
        Groupon groupon = new Groupon();
        groupon.setId(id);
        groupon.setGroupStatus(groupStatus);
        if (GroupStatus.GROUPED.getCodeValue().equals(groupStatus)) {
            groupon.setGroupTime(new Date());
        }
        super.updateEntity(groupon);
    }

    @Override
    public void updateStatusAndNum(Long id, String groupStatus, Integer num) {
        Groupon groupon = new Groupon();
        groupon.setId(id);
        groupon.setTakeNum(num);
        groupon.setGroupStatus(groupStatus);
        super.update("Groupon_update_ns", groupon);
    }

    @Override
    public void updateIsDeal(Long id) {
        Groupon groupon = new Groupon();
        groupon.setId(id);
        groupon.setIsDeal(1);
        super.updateEntity(groupon);
    }

    @Override
    public List<Groupon> queryNeedClose() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("isClose", true);
        paramMap.put("isPay", 1);
        paramMap.put("isDeal", 0);
        return super.queryList(paramMap);
    }

}
