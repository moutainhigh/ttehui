package com.mocentre.tehui.fee.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mocentre.tehui.core.dao.BaseDao;
import com.mocentre.tehui.fee.dao.IFreightMouldDao;
import com.mocentre.tehui.fee.model.FreightMould;

/**
 * 邮费模板信息数据库操作实现类 Created by yukaiji on 2016/11/04.
 */
@Repository
public class FreightMouldDao extends BaseDao<FreightMould> implements IFreightMouldDao {

    @Override
    public FreightMould getFreightMould(Map<String, Object> paramMap) {
        return super.queryUniquely(entityClass.getSimpleName() + "_getFreightMould", paramMap);
    }

    @Override
    public int delByIdList(List<Long> idList) {
        return super.delete(entityClass.getSimpleName() + "_delByIdList", idList);
    }

}
