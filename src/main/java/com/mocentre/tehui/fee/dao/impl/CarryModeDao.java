package com.mocentre.tehui.fee.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.mocentre.tehui.core.dao.BaseDao;
import com.mocentre.tehui.fee.dao.ICarryModeDao;
import com.mocentre.tehui.fee.model.CarryMode;

/**
 * 运送方式信息数据库操作实现类 Created by yukaiji on 2016/11/04.
 */
@Repository
public class CarryModeDao extends BaseDao<CarryMode> implements ICarryModeDao {

    @Override
    public List<CarryMode> queryAll(Serializable id) {
        return super.getList(entityClass.getSimpleName() + "_getAll", id);
    }

    @Override
    public Long addCarryMode(List<CarryMode> carryModeList) {
        int count = super.insert(entityClass.getSimpleName() + "_insert", carryModeList);
        return Long.valueOf(count);
    }

    @Override
    public int delByFreightIdList(List<Long> freightIdList) {
        return super.delete(entityClass.getSimpleName() + "_delByCarryModeIdList", freightIdList);
    }

    @Override
    public List<CarryMode> queryByParam(CarryMode carryMode) {
        return super.getList(entityClass.getSimpleName() + "_byParam", carryMode);
    }

}
