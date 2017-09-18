package com.mocentre.tehui.goods.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.mocentre.common.ListJsonResult;
import com.mocentre.tehui.core.service.support.query.Requirement;
import com.mocentre.tehui.goods.model.Attribute;

public interface IAttributeDao {

    ListJsonResult<Attribute> queryDatatablesPage(Requirement require);

    Attribute get(Serializable id);

    List<Attribute> getAll();

    List<Attribute> queryList(Map<String, Object> paramMap);

    Attribute queryUniquely(Map<String, Object> paramMap);

    Long updateEntity(Attribute attribute);

    Long saveEntity(Attribute attribute);

    int delById(Long id);

    int delById(List<Long> ids);
}
